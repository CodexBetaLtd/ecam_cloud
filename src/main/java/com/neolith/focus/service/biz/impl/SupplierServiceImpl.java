package com.neolith.focus.service.biz.impl;

import com.neolith.focus.dao.admin.CountryDao;
import com.neolith.focus.dao.admin.CurrencyDao;
import com.neolith.focus.dao.biz.BusinessClassificationDao;
import com.neolith.focus.dao.biz.BusinessDao;
import com.neolith.focus.dao.biz.BusinessVirtualDao;
import com.neolith.focus.dao.biz.SupplierDao;
import com.neolith.focus.dto.biz.supplier.SupplierDTO;
import com.neolith.focus.exception.admin.CurrencyException;
import com.neolith.focus.exception.admin.SupplierException;
import com.neolith.focus.exception.setting.business.BusinessClassificationException;
import com.neolith.focus.mappers.admin.SupplierMapper;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.model.biz.business.BusinessVirtual;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.biz.SupplierResult;
import com.neolith.focus.service.biz.api.SupplierService;
import com.neolith.focus.util.AuthenticationUtil;
import com.neolith.focus.util.search.biz.SupplierSearchPropertyMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class SupplierServiceImpl implements SupplierService {

	private final static Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private BusinessVirtualDao businessVirtualDao;

	@Autowired
	private BusinessClassificationDao businessClassificationDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private CountryDao countryDao;

	private SupplierDTO getDTOById(Integer id) throws SupplierException {
		try {
			return SupplierMapper.getInstance().domainToDto(getEntityById(id));
		} catch (Exception e) {
			throw new SupplierException("ERROR! Supplier mapper not worked!");
		}
	}

	private Business getEntityById(Integer id) throws SupplierException {
		try {
			return supplierDao.findOne(id);
		} catch (Exception e) {
			throw new SupplierException("ERROR! Supplier FETCH not completed.!");
		}
	}

	@Override
	public SupplierResult newSupplier() {
		SupplierResult result = new SupplierResult(null, new SupplierDTO());
		try {
			result.setDtoEntity(getNewSupplier());
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! NEW Supplier Created.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setResultStatusError();
			result.addToErrorList("ERROR! NEW Supplier NOT Created.");
		}
		return result;
	}

	private SupplierDTO getNewSupplier() {
		SupplierDTO supplierDTO = new SupplierDTO();
		supplierDTO.setActive(Boolean.TRUE);
		return supplierDTO;
	}

	@Override
	public SupplierResult findById(Integer id) {
		SupplierResult result = new SupplierResult(null, new SupplierDTO());
		try {
			result.setDtoEntity(getDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Supplier found.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setResultStatusError();
			result.addToErrorList("ERROR! Supplier NOT found.");
		}
		return result;
	}


	@Override
	public SupplierResult save(SupplierDTO dto) {
		SupplierResult result = new SupplierResult(new Business(), dto);
		try {
			saveOrUpdate(result);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Supplier save operation completed.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
			result.addToErrorList("FAILED! Supplier save operation NOT completed");
		}
		return result;
	}

	/*
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveNewVirtualBiz(SupplierResult result) throws SupplierException,BusinessClassificationException,CurrencyException{
		if(!AuthenticationUtil.isAuthUserAdminLevel()){
			Business businessOwner = AuthenticationUtil.getLoginUserBusiness();

			BusinessVirtual businessVirtual = businessVirtualDao.findByBusinessOwner(businessOwner.getId());
			if(businessVirtual== null){
				businessVirtual = new BusinessVirtual();
			}
			businessVirtual.setBusiness(businessOwner);
			SupplierMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
			setSupplierData(result);
			businessVirtual.getVirtualBusinesses().add(result.getDomainEntity());
			businessVirtualDao.save(businessVirtual);
			result.setDtoEntity(getDTOById(result.getDomainEntity().getId()));
		}else {

		}
	}
	 */


	@Override
	public SupplierResult update(SupplierDTO dto) {
		SupplierResult result = new SupplierResult(null, dto);
		try {
			result.setDomainEntity(getEntityById(dto.getId()));
			saveOrUpdate(result);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Supplier save operation completed.");
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			logger.error(e.getMessage());
			result.addToErrorList("Supplier Already updated. Please Reload Supplier.");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList("FAILED! Supplier update operation NOT completed");
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(SupplierResult result) throws SupplierException, BusinessClassificationException, CurrencyException {
		SupplierMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setSupplierData(result);
		supplierDao.save(result.getDomainEntity());
		result.setDomainEntity(getEntityById(result.getDomainEntity().getId()));
		setBusinessVirtual(result);
		supplierDao.save(result.getDomainEntity());
		result.setDtoEntity(getDTOById(result.getDomainEntity().getId()));
	}

	private void setSupplierData(SupplierResult result) throws BusinessClassificationException, CurrencyException {
		setRoleSupplier(result);
		setVirtualBusiness(result);
		setCurrency(result);
		setBusinessClassification(result);
		setCountry(result);
	}

	private void setCountry(SupplierResult result) {
		if ((result.getDtoEntity().getCountryId() != null) && (result.getDtoEntity().getCountryId() > 0)) {
			result.getDomainEntity().setCountry(countryDao.findOne(result.getDtoEntity().getCountryId()));
		}
	}

	private void setRoleSupplier(SupplierResult result) {
		if (result.getDomainEntity().getId() == null) {
			result.getDomainEntity().setRoleSupplier(Boolean.TRUE);
		}
	}

	private void setVirtualBusiness(SupplierResult result) {
		if (result.getDomainEntity().getId() == null) {
			result.getDomainEntity().setVirtualBusiness(Boolean.TRUE);
		}
	}

	private void setBusinessVirtual(SupplierResult result) {
		if (result.getDtoEntity().getVirtualBusinessOwnerId() != null) {
			//			Business businessOwner = AuthenticationUtil.getLoginUserBusiness();
			Business businessOwner = businessDao.findOne(result.getDtoEntity().getVirtualBusinessOwnerId());
			BusinessVirtual businessVirtual = businessVirtualDao.findByBusinessOwner(businessOwner.getId());
			if (businessVirtual == null) {
				businessVirtual = new BusinessVirtual();
				businessVirtual.setVirtualBusinesses(new HashSet<>());
				businessVirtual.setBusiness(businessOwner);
			}
			result.getDomainEntity().setBusinessVirtual(businessVirtual);
		}
	}

	private void setBusinessClassification(SupplierResult result) throws BusinessClassificationException {
		if ((result.getDtoEntity().getBusinessClassificationId() != null) && (result.getDtoEntity().getBusinessClassificationId() > 0)) {
			result.getDomainEntity().setBusinessClassification(businessClassificationDao.findOne(result.getDtoEntity().getBusinessClassificationId()));
		}
	}

	private void setCurrency(SupplierResult result) throws CurrencyException {
		if ((result.getDtoEntity().getCurrencyId() != null) && (result.getDtoEntity().getCurrencyId() > 0)) {
			result.getDomainEntity().setCurrency(currencyDao.findById(result.getDtoEntity().getCurrencyId()));
		}
	}

	@Override
	public SupplierResult delete(Integer id) {
		SupplierResult result = new SupplierResult(null, new SupplierDTO());
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Supplier delete operation completed.");
		} catch (SupplierException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			logger.error(ex.getMessage());
			result.addToErrorList("FAILED! Supplier delete operation NOT completed");
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void deleteEntityById(Integer id) throws SupplierException {
		supplierDao.delete(id);
	}

	@Override
	public DataTablesOutput<SupplierDTO> findAllByLevel(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<Business> domainOut = null;
		SupplierSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = supplierDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			domainOut = supplierDao.findAll(input, getSystemUserBusinessSpecification(AuthenticationUtil.getLoginUserBusiness().getId()));
		} else {
			domainOut = supplierDao.findAll(input, getGeneralUserBusinessSpecification(AuthenticationUtil.getLoginSite().getSite().getId()));
		}
		DataTablesOutput<SupplierDTO> out = SupplierMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public List<SupplierDTO> findAllVirtualSupplierList() {
		try {
			List<Business> list = supplierDao.findAll(specAdminLevelVirtualSupplier());
			return SupplierMapper.getInstance().domainToDTOList(list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Specification<Business> getSystemUserBusinessSpecification(Integer businessId) {
		Specification<Business> specification = (root, query, cb) -> cb.equal(root.get("id"), businessId);
		return specification;
	}

	private Specification<Business> getGeneralUserBusinessSpecification(Integer id) {
		Specification<Business> specification = (root, query, cb) -> {
			Join<Business, Asset> joinAssetBusiness = root.joinList("assets");
			return cb.equal(joinAssetBusiness.get("id"), id);
		};
		return specification;
	}

	@Override
	public DataTablesOutput<SupplierDTO> findAllVirtualSupplier(FocusDataTablesInput input) throws Exception {
		try {
			SupplierSearchPropertyMapper.getInstance().generateDataTableInput(input);
			DataTablesOutput<Business> domainOut = null;
			if (AuthenticationUtil.isAuthUserAdminLevel()) {
				domainOut = supplierDao.findAll(input,specAdminLevelVirtualSupplier());
			} else {
				domainOut =  supplierDao.findAll(input, specSystmeLevelVirtualSupplier());
			}
			return SupplierMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}

	@Override
	public List<SupplierDTO> findAllOriginalSupplierList() {
		try {
			List<Business> list = supplierDao.findAll(specOriginalSupplier());
			return SupplierMapper.getInstance().domainToDTOList(list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Specification<Business> specSystmeLevelVirtualSupplier() {
		Specification<Business> spec = (root, query, cb) -> {
			Join<Business, BusinessVirtual> joinvirtualBusiness = root.join("businessVirtual");
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("virtualBusiness"), Boolean.TRUE));
			predicates.add(cb.equal(root.get("roleSupplier"), Boolean.TRUE));
			predicates.add(cb.equal(joinvirtualBusiness.get("business"), AuthenticationUtil.getLoginUserBusiness()));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return spec;
	}
	
	private Specification<Business> specAdminLevelVirtualSupplier() {
		Specification<Business> spec = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("virtualBusiness"), Boolean.TRUE));
			predicates.add(cb.equal(root.get("roleSupplier"), Boolean.TRUE));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return spec;
	}

	@Override
	public DataTablesOutput<SupplierDTO> findAllOriginalSupplier(FocusDataTablesInput input) throws Exception {
		try {
			DataTablesOutput<Business> domainOut = supplierDao.findAll(input, specOriginalSupplier());
			return SupplierMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}

	private Specification<Business> specOriginalSupplier() {
		Specification<Business> spec = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("virtualBusiness"), Boolean.FALSE));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return spec;
	}

}
