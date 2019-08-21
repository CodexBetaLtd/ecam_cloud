package com.neolith.focus.service.asset.impl;

import com.neolith.focus.dao.admin.CountryDao;
import com.neolith.focus.dao.asset.AssetCustomerDao;
import com.neolith.focus.dao.asset.AssetDao;
import com.neolith.focus.dao.biz.BusinessDao;
import com.neolith.focus.dao.biz.BusinessVirtualDao;
import com.neolith.focus.dto.asset.AssetCustomerDTO;
import com.neolith.focus.dto.asset.AssetDTO;
import com.neolith.focus.dto.asset.ContactDTO;
import com.neolith.focus.dto.asset.CustomerDTO;
import com.neolith.focus.mappers.asset.AssetCustomerMapper;
import com.neolith.focus.mappers.biz.CustomerMapper;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.asset.AssetBusiness;
import com.neolith.focus.model.asset.AssetCustomer;
import com.neolith.focus.model.asset.Contact;
import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.model.biz.business.BusinessContact;
import com.neolith.focus.model.biz.business.BusinessVirtual;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.asset.CustomerResult;
import com.neolith.focus.service.asset.api.CustomerService;
import com.neolith.focus.util.AuthenticationUtil;
import com.neolith.focus.util.search.asset.AssetCustomerSearchPropertyMapper;
import com.neolith.focus.util.search.biz.CustomerSearchPropertyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private AssetCustomerDao assetCustomerDao;

	@Autowired
	private BusinessDao customerDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private CountryDao countryDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private BusinessVirtualDao businessVirtualDao;

	@Override
	public DataTablesOutput<AssetCustomerDTO> findByAsset(FocusDataTablesInput input, Integer assetId) throws Exception {

		Specification<AssetCustomer> specification = (root, query, cb) -> cb.equal(root.get("asset").get("id"), assetId);
		AssetCustomerSearchPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<AssetCustomer> domainOut = assetCustomerDao.findAll(input, specification);
		DataTablesOutput<AssetCustomerDTO> out = AssetCustomerMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public CustomerDTO findById(Integer id) {
		Business customer = customerDao.findOne(id);
		try {
			return CustomerMapper.getInstance().domainToDto(customer);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public CustomerResult delete(Integer id) {
		CustomerResult result = new CustomerResult(null, null);
		try {
			customerDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Customer Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Customer Already Assigned. Please Remove from Assigned items and try again.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Override
	public CustomerResult save(CustomerDTO dto) throws Exception {
		CustomerResult result = createCustomerResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Customer Already updated. Please Reload Customer.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(CustomerResult result) throws Exception {
		CustomerMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setCustomerData(result);
		Business customer = customerDao.save(result.getDomainEntity());
		setAssetsToCustomer(customer.getId(), result.getDtoEntity());
		result.updateDtoIdAndVersion();
	}

	private void setCustomerData(CustomerResult result) throws Exception {
		setBusinessVirtual(result);
		setContactsToCustomer(result);
		setCountry(result);
	}

	private void setBusinessVirtual(CustomerResult result) {
		if (result.getDtoEntity().getBusinessId() != null) {
			//			Business businessOwner = AuthenticationUtil.getLoginUserBusiness();
			Business businessOwner = businessDao.findOne(result.getDtoEntity().getBusinessId());
			BusinessVirtual businessVirtual = businessVirtualDao.findByBusinessOwner(businessOwner.getId());
			if (businessVirtual == null) {
				businessVirtual = new BusinessVirtual();
				businessVirtual.setVirtualBusinesses(new HashSet<>());
				businessVirtual.setBusiness(businessOwner);
			}
			result.getDomainEntity().setBusinessVirtual(businessVirtual);
		}
	}
	private void setCountry(CustomerResult result) {
		if ( (result.getDtoEntity().getCountryId() != null) && (result.getDtoEntity().getCountryId() > 0) ) {
			result.getDomainEntity().setCountry(countryDao.findById(result.getDtoEntity().getCountryId()));
		}
	}

	private CustomerResult createCustomerResult(CustomerDTO dto) {
		CustomerResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new CustomerResult(customerDao.findOne(dto.getId()), dto);
		} else {
			result = new CustomerResult(new Business(), dto);
		}

		return result;
	}

	private String getMessageByAction(CustomerDTO dto) {
		if (dto.getId() == null) {
			return "Customer Added Successfully.";
		} else {
			return "Customer Updated Successfully.";
		}
	}

	private void setAssetsToCustomer(Integer id, CustomerDTO dto) {
		Business customer = customerDao.findOne(id);

		Set<Asset> currentAssets = new HashSet<>();

		if ((dto.getAssets() != null) && (dto.getAssets().size() > 0)) {

			Asset asset;
			Set<Asset> prevAssets = customer.getAssets();

			for (AssetDTO assetDTO : dto.getAssets()) {

				if ((prevAssets != null) && (prevAssets.size() > 0)) {
					Optional<Asset> optionalAsset = prevAssets.stream().filter((x) -> x.getId().equals(assetDTO.getId())).findAny();

					if (optionalAsset.isPresent()) {
						asset = optionalAsset.get();
					} else {
						asset = createAsset(customer, assetDTO);
					}
				} else {
					asset = createAsset(customer, assetDTO);
				}

				currentAssets.add(asset);
			}
		}
		customer.setCurrentAssets(currentAssets);
		customerDao.save(customer);
	}

	private Asset createAsset(Business customer, AssetDTO assetDTO) {
		Asset asset = assetDao.findOne(assetDTO.getId());
		asset.setBusiness(customer);
		createCustomerAsset(customer, asset);

		return asset;
	}

	private void createCustomerAsset(Business customer, Asset asset) {

		Set<AssetBusiness> assetCustomers = asset.getAssetBusinesses();

		AssetBusiness assetCustomer =  new AssetBusiness();
		assetCustomer.setAsset(asset);
		assetCustomer.setBusiness(customer);;
		assetCustomer.setIsDeleted(false);

		assetCustomers.add(assetCustomer);
	}

	private void setContactsToCustomer(CustomerResult result) throws Exception {
		Set<BusinessContact> customerContacts = new HashSet<>();
		if ((result.getDtoEntity().getContacts() != null) && (result.getDtoEntity().getContacts().size() > 0)) {

			Set<BusinessContact> currentCustomerContacts = result.getDomainEntity().getContacts();
			BusinessContact customerContact;

			for (ContactDTO contactDTO : result.getDtoEntity().getContacts()) {
				customerContact = getCustomerContact(result.getDomainEntity(), currentCustomerContacts, contactDTO);
				setContactData(contactDTO, customerContact.getContact());
				customerContacts.add(customerContact);
			}
		}

		result.getDomainEntity().setContacts(customerContacts);
	}

	private BusinessContact getCustomerContact(Business customer, Set<BusinessContact> currentCustomerContacts, ContactDTO contactDTO) {
		BusinessContact customerContact;
		if ((currentCustomerContacts != null) && (currentCustomerContacts.size() > 0)) {
			Optional<BusinessContact> optionalCustomerContact = currentCustomerContacts.stream().filter((x) -> x.getContact().getId().equals(contactDTO.getId())).findAny();
			if (optionalCustomerContact.isPresent()) {
				customerContact = optionalCustomerContact.get();
			} else {
				customerContact = createCustomerContact(customer);
			}
		} else {
			customerContact = createCustomerContact(customer);
		}

		return customerContact;
	}

	private BusinessContact createCustomerContact(Business customer) {
		BusinessContact customerContact = new BusinessContact();
		customerContact.setContact(new Contact());
		customerContact.setBusiness(customer);
		customerContact.setIsDeleted(false);

		return customerContact;
	}

	private void setContactData(ContactDTO contactDTO, Contact contact) {
		contact.setName(contactDTO.getName());
		contact.setDesignation(contactDTO.getDesignation());
		contact.setTelephone(contactDTO.getTelephone());
		contact.setEmail(contactDTO.getEmail());
		contact.setIsDeleted(false);
	}

	@Override
    public DataTablesOutput<CustomerDTO> findAll(FocusDataTablesInput input) throws Exception {
        DataTablesOutput<Business> domainOut;
        CustomerSearchPropertyMapper.getInstance().generateDataTableInput(input);
        if (AuthenticationUtil.isAuthUserAdminLevel()) {
            Specification<Business> specification = (root, query, cb) ->
                    cb.and(
                            cb.equal(root.get("roleCustomer"), Boolean.TRUE),
                            cb.isNotNull(root.get("roleCustomer"))
                    );
            domainOut = customerDao.findAll(input, specification);
        } else {
            Specification<Business> specification = (root, query, cb) ->
                    cb.and(
                            cb.equal(root.get("roleCustomer"), Boolean.TRUE),
                            cb.isNotNull(root.get("roleCustomer")),
                            cb.equal(root.get("virtualBusiness"), Boolean.TRUE)
                    );
            domainOut = customerDao.findAll(input, specification);
        }
        DataTablesOutput<CustomerDTO> out = CustomerMapper.getInstance().domainToDTODataTablesOutput(domainOut);

        return out;
    }

    @Override
    public DataTablesOutput<CustomerDTO> findAllByBusiness(FocusDataTablesInput dataTablesInput, Integer id) throws Exception {
        DataTablesOutput<CustomerDTO> out;
        Specification<Business> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("virtualBusiness"), Boolean.TRUE));
            predicates.add(cb.equal(root.get("roleCustomer"), Boolean.TRUE));
            predicates.add(cb.equal(root.get("businessVirtual").get("business").get("id"), id));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        DataTablesOutput<Business> domainOut = customerDao.findAll(dataTablesInput, specification);
        out = CustomerMapper.getInstance().domainToDTODataTablesOutput(domainOut);
        return out;
    }

}
