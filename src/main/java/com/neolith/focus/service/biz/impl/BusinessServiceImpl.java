package com.neolith.focus.service.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.dao.admin.CountryDao;
import com.neolith.focus.dao.admin.CurrencyDao;
import com.neolith.focus.dao.biz.BusinessClassificationDao;
import com.neolith.focus.dao.biz.BusinessDao;
import com.neolith.focus.dto.biz.business.BusinessDTO;
import com.neolith.focus.mappers.admin.BusinessMapper;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.BusinessResult;
import com.neolith.focus.service.biz.api.BusinessService;
import com.neolith.focus.util.AuthenticationUtil;

@Service
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private BusinessClassificationDao businessClassificationDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private CountryDao countryDao;

	@Override
	public BusinessDTO findById(Integer id) throws Exception {
		Business domain = findEntityById(id);
		if (domain != null) {
			BusinessDTO businessDTO = BusinessMapper.getInstance().domainToDto(domain);
			return businessDTO;
		}
		return null;
	}

	@Override
	public BusinessResult delete(Integer id) {
		BusinessResult result = new BusinessResult(null, null);
		try {
			businessDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Business Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Business Already Assigned. Please Remove from Assigned Business and try again.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public BusinessResult save(BusinessDTO dto) throws Exception {
		BusinessResult result = createBusinessResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Business Already updated. Please Reload Business.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(BusinessResult result) throws Exception {
		BusinessMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBusinessData(result);
		businessDao.save(result.getDomainEntity());
		result.getDtoEntity().setId(result.getDomainEntity().getId());
	}

	private BusinessResult createBusinessResult(BusinessDTO dto) {
		BusinessResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new BusinessResult(businessDao.findOne(dto.getId()), dto);
		} else {
			result = new BusinessResult(new Business(), dto);
		}

		return result;
	}

	private String getMessageByAction(BusinessDTO dto) {
		if (dto.getId() == null) {
			return "Business Added Successfully.";
		} else {
			return "Business Updated Successfully.";
		}
	}

	private void setBusinessData(BusinessResult result) {
		setCurrency(result.getDtoEntity(), result.getDomainEntity());
		setBusisnessClassification(result.getDtoEntity(), result.getDomainEntity());
		setCountry(result.getDtoEntity(), result.getDomainEntity());
		setRoleCustomer(result.getDtoEntity(), result.getDomainEntity());
		setRoleSupplier(result.getDtoEntity(), result.getDomainEntity());
	}

	private void setRoleSupplier(BusinessDTO dto, Business domain) {
		if (dto.getRoleSupplier() != null) {
			domain.setRoleSupplier(dto.getRoleSupplier());
		}
	}

	private void setRoleCustomer(BusinessDTO dto, Business domain) {
		if (dto.getRoleCustomer() != null) {
			domain.setRoleCustomer(dto.getRoleCustomer());
		}
	}

	private void setCountry(BusinessDTO dto, Business domain) {
		if ((dto.getCountryId() != null) && (dto.getCountryId() > 0)) {
			domain.setCountry(countryDao.findOne(dto.getCountryId()));
		}
	}

	private void setBusisnessClassification(BusinessDTO dto, Business domain) {
		if ((dto.getBusinessClassficationId() != null) && (dto.getBusinessClassficationId() > 0)) {
			domain.setBusinessClassification(businessClassificationDao.findOne(dto.getBusinessClassficationId()));
		}
	}

	private void setCurrency(BusinessDTO dto, Business domain) {
		if ((dto.getCurrencyId() != null) && (dto.getCurrencyId() > 0)) {
			domain.setCurrency(currencyDao.findById(dto.getCurrencyId()));
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveAll(List<BusinessDTO> entities) throws Exception {
		for (BusinessDTO dto : entities) {
			save(dto);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteAll() {
		businessDao.deleteAll();
	}


	@Override
	public List<BusinessDTO> findAll() throws Exception {
		return BusinessMapper.getInstance().domainToDTOList(businessDao.findAll());
	}


	@Override
	public List<BusinessDTO> findAllActualBusinessByLevel() {
		List<BusinessDTO> list = null;
		try {
			if (AuthenticationUtil.isAuthUserAdminLevel()) {
				list = BusinessMapper.getInstance().domainToDTOList(businessDao.findOriginalBiz());
			} else {
				list = new ArrayList<BusinessDTO>();
				list.add(BusinessMapper.getInstance().domainToDto(businessDao.findOne(AuthenticationUtil.getLoginUserBusiness().getId())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public List<BusinessDTO> findAllActualBusiness() {
		try {
			List<Business> businesses = businessDao.findOriginalBiz();
			return BusinessMapper.getInstance().domainToDTOList(businesses);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Business findEntityById(Integer id) {
		return businessDao.findById(id);
	}

	@Override
	public DataTablesOutput<BusinessDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<Business> domainOut = businessDao.findAll(input);
		DataTablesOutput<BusinessDTO> out = BusinessMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<BusinessDTO> findAllByLevel(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<Business> domainOut = null;

		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = businessDao.findAll(input,getAdminUserBusinessSpecification());
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			domainOut = businessDao.findAll(input, getSystemUserBusinessSpecification(AuthenticationUtil.getLoginUserBusiness().getId()));
		} else {
			domainOut = businessDao.findAll(input, getGeneralUserBusinessSpecification(AuthenticationUtil.getLoginSite().getSite().getId()));
		}
		DataTablesOutput<BusinessDTO> out = BusinessMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	private Specification<Business> getAdminUserBusinessSpecification() {
		Specification<Business> specification = (root, query, cb) -> 
			cb.and(cb.isNull(root.get("virtualBusiness")));
		return specification;
	}
	private Specification<Business> getSystemUserBusinessSpecification(Integer businessId) {
		Specification<Business> specification = (root, query, cb) ->
		cb.and(cb.equal(root.get("id"), businessId),cb.isNull(root.get("virtualBusiness")));
		return specification;
	}

	private Specification<Business> getGeneralUserBusinessSpecification(Integer id) {
		Specification<Business> specification = (root, query, cb) -> {
			Join<Business, Asset> joinAssetBusiness = root.joinList("assets");
			return cb.and(cb.equal(joinAssetBusiness.get("id"), id),cb.notEqual(root.get("virtualBusiness"), Boolean.TRUE));
		};
		return specification;
	}

	@Override
	public DataTablesOutput<BusinessDTO> findActualBusinesses(FocusDataTablesInput input) throws Exception {
		try {
			DataTablesOutput<Business> domainOut = businessDao.findAll(input, findAllActualSpecification());
			return BusinessMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}


	private Specification<Business> findAllActualSpecification() {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (AuthenticationUtil.isAuthUserAdminLevel()) {
				predicates.add(cb.or(cb.isNull(root.get("virtualBusiness")), cb.equal(root.get("virtualBusiness"), Boolean.FALSE)));
			} else {
				predicates.add(cb.and(
						cb.or(
								cb.isNull(root.get("virtualBusiness")),
								cb.equal(root.get("virtualBusiness"), Boolean.FALSE)),
						cb.equal(root, AuthenticationUtil.getLoginUserBusiness())

						));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

}
