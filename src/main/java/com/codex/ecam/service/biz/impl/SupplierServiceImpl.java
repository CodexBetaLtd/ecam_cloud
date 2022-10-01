package com.codex.ecam.service.biz.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.CountryDao;
import com.codex.ecam.dao.admin.CurrencyDao;
import com.codex.ecam.dao.biz.BusinessClassificationDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.biz.BusinessVirtualDao;
import com.codex.ecam.dao.biz.SupplierDao;
import com.codex.ecam.dto.biz.supplier.SupplierDTO;
import com.codex.ecam.exception.admin.CurrencyException;
import com.codex.ecam.exception.admin.SupplierException;
import com.codex.ecam.exception.setting.business.BusinessClassificationException;
import com.codex.ecam.mappers.admin.SupplierMapper;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.biz.business.BusinessVirtual;
import com.codex.ecam.model.biz.supplier.Supplier;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.biz.SupplierResult;
import com.codex.ecam.service.biz.api.SupplierService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.biz.SupplierSearchPropertyMapper;


@Service
public class SupplierServiceImpl implements SupplierService {

	private final static Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private BusinessVirtualDao businessVirtualDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private CountryDao countryDao;

	@Autowired
	private BusinessClassificationDao businessClassificationDao;

	private SupplierDTO getDTOById(Integer id) throws SupplierException {
		try {
			return SupplierMapper.getInstance().domainToDto(getEntityById(id));
		} catch (final Exception e) {
			throw new SupplierException("ERROR! Supplier mapper not worked!");
		}
	}

	private Supplier getEntityById(Integer id) throws SupplierException {
		try {
			return supplierDao.findOne(id);
		} catch (final Exception e) {
			throw new SupplierException("ERROR! Supplier FETCH not completed.!");
		}
	}

	@Override
	public SupplierResult newSupplier() {
		final SupplierResult result = new SupplierResult(null, new SupplierDTO());
		try {
			result.setDtoEntity(getNewSupplier());
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! NEW Supplier Created.");
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setResultStatusError();
			result.addToErrorList("ERROR! NEW Supplier NOT Created.");
		}
		return result;
	}

	private SupplierDTO getNewSupplier() {
		final SupplierDTO supplierDTO = new SupplierDTO();
		supplierDTO.setActive(Boolean.TRUE);
		return supplierDTO;
	}

	@Override
	public SupplierResult findById(Integer id) {
		final SupplierResult result = new SupplierResult(null, new SupplierDTO());
		try {
			result.setDtoEntity(getDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Supplier found.");
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setResultStatusError();
			result.addToErrorList("ERROR! Supplier NOT found.");
		}
		return result;
	}


	@Override
	public SupplierResult save(SupplierDTO dto) {
		final SupplierResult result = new SupplierResult(new Supplier(), dto);
		try {
			saveOrUpdate(result);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Supplier save operation completed.");
		} catch (final Exception e) {
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
		final SupplierResult result = new SupplierResult(null, dto);
		try {
			result.setDomainEntity(getEntityById(dto.getId()));
			saveOrUpdate(result);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Supplier save operation completed.");
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			logger.error(e.getMessage());
			result.addToErrorList("Supplier Already updated. Please Reload Supplier.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList("FAILED! Supplier update operation NOT completed");
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(SupplierResult result) throws Exception {
		SupplierMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setSupplierData(result);
		supplierDao.save(result.getDomainEntity());
		result.setDomainEntity(getEntityById(result.getDomainEntity().getId()));
		setBusinessVirtual(result);
		supplierDao.save(result.getDomainEntity());
		result.setDtoEntity(getDTOById(result.getDomainEntity().getId()));
	}

	private void setSupplierData(SupplierResult result) throws Exception {
		setRoleSupplier(result);
		setVirtualBusiness(result);
		setCurrency(result);
		setBusinessClassification(result);
		setCountry(result);
		setBusiness(result);
	}

	private void setCountry(SupplierResult result) {
		if (result.getDtoEntity().getCountryId() != null && result.getDtoEntity().getCountryId() > 0) {
			result.getDomainEntity().setCountry(countryDao.findOne(result.getDtoEntity().getCountryId()));
		}
	}

	private void setRoleSupplier(SupplierResult result) {
		if (result.getDomainEntity().getId() == null) {
			//result.getDomainEntity().setRoleSupplier(Boolean.TRUE);
		}
	}

	private void setVirtualBusiness(SupplierResult result) {
		if (result.getDomainEntity().getId() == null) {
			//	result.getDomainEntity().setVirtualBusiness(Boolean.TRUE);
		}
	}

	private void setBusiness(SupplierResult result) {
		if (result.getDtoEntity().getBusinessId() != null && result.getDtoEntity().getBusinessId() > 0) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}
	private void setBusinessVirtual(SupplierResult result) {
		if (result.getDtoEntity().getVirtualBusinessOwnerId() != null) {
			//			Business businessOwner = AuthenticationUtil.getLoginUserBusiness();
			final Business businessOwner = businessDao.findOne(result.getDtoEntity().getVirtualBusinessOwnerId());
			BusinessVirtual businessVirtual = businessVirtualDao.findByBusinessOwner(businessOwner.getId());
			if (businessVirtual == null) {
				businessVirtual = new BusinessVirtual();
				businessVirtual.setVirtualBusinesses(new HashSet<>());
				businessVirtual.setBusiness(businessOwner);
			}
			//result.getDomainEntity().setBusinessVirtual(businessVirtual);
		}
	}

	private void setBusinessClassification(SupplierResult result) throws Exception {
		if (result.getDtoEntity().getBusinessClassificationId() != null && result.getDtoEntity().getBusinessClassificationId() > 0) {
			result.getDomainEntity().setBusinessClassification(businessClassificationDao.findOne(result.getDtoEntity().getBusinessClassificationId()));
		}
	}

	private void setCurrency(SupplierResult result) throws Exception {
		if (result.getDtoEntity().getCurrencyId() != null && result.getDtoEntity().getCurrencyId() > 0) {
			result.getDomainEntity().setCurrency(currencyDao.findById(result.getDtoEntity().getCurrencyId()));
		}
	}

	@Override
	public SupplierResult delete(Integer id) {
		final SupplierResult result = new SupplierResult(null, new SupplierDTO());
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Supplier delete operation completed.");
		} catch (final SupplierException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			logger.error(ex.getMessage());
			result.addToErrorList("FAILED! Supplier delete operation NOT completed");
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public SupplierResult deleteMultiple(Integer[] ids) throws Exception {
		final SupplierResult result = new SupplierResult(null, null);
		try {
			for (final Integer id : ids) {
				deleteEntityById(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Supplier(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Supplier(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void deleteEntityById(Integer id) throws SupplierException {
		supplierDao.delete(id);
	}

	@Override
	public DataTablesOutput<SupplierDTO> findAllByLevel(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<Supplier> domainOut = null;
		SupplierSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = supplierDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			domainOut = supplierDao.findAll(input, getSystemUserBusinessSpecification(AuthenticationUtil.getLoginUserBusiness().getId()));
		} else {
			domainOut = supplierDao.findAll(input, getGeneralUserBusinessSpecification(AuthenticationUtil.getLoginSite().getSite().getId()));
		}
		final DataTablesOutput<SupplierDTO> out = SupplierMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public List<SupplierDTO> findAllVirtualSupplierList() {
		try {
			final List<Supplier> list = supplierDao.findAll(specAdminLevelVirtualSupplier());
			return SupplierMapper.getInstance().domainToDTOList(list);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Specification<Supplier> getSystemUserBusinessSpecification(Integer businessId) {
		final Specification<Supplier> specification = (root, query, cb) -> cb.equal(root.get("business").get("id"), businessId);
		return specification;
	}

	private Specification<Supplier> getGeneralUserBusinessSpecification(Integer id) {
		final Specification<Supplier> specification = (root, query, cb) -> {
			final Join<Business, Asset> joinAssetBusiness = root.joinList("assets");
			return cb.equal(joinAssetBusiness.get("id"), id);
		};
		return specification;
	}

	@Override
	public DataTablesOutput<SupplierDTO> findAllVirtualSupplier(FocusDataTablesInput input) throws Exception {
		try {
			SupplierSearchPropertyMapper.getInstance().generateDataTableInput(input);
			DataTablesOutput<Supplier> domainOut = null;
			if (AuthenticationUtil.isAuthUserAdminLevel()) {
				domainOut = supplierDao.findAll(input,specAdminLevelVirtualSupplier());
			} else {
				domainOut =  supplierDao.findAll(input, specSystmeLevelVirtualSupplier());
			}
			return SupplierMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}

	@Override
	public List<SupplierDTO> findAllOriginalSupplierList() {
		try {
			final List<Supplier> list = supplierDao.findAll(specOriginalSupplier());
			return SupplierMapper.getInstance().domainToDTOList(list);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Specification<Supplier> specSystmeLevelVirtualSupplier() {
		final Specification<Supplier> spec = (root, query, cb) -> {
			final Join<Business, BusinessVirtual> joinvirtualBusiness = root.join("businessVirtual");
			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("virtualBusiness"), Boolean.TRUE));
			predicates.add(cb.equal(root.get("roleSupplier"), Boolean.TRUE));
			predicates.add(cb.equal(joinvirtualBusiness.get("business"), AuthenticationUtil.getLoginUserBusiness()));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return spec;
	}

	private Specification<Supplier> specAdminLevelVirtualSupplier() {
		final Specification<Supplier> spec = (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("virtualBusiness"), Boolean.TRUE));
			predicates.add(cb.equal(root.get("roleSupplier"), Boolean.TRUE));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return spec;
	}

	@Override
	public DataTablesOutput<SupplierDTO> findAllOriginalSupplier(FocusDataTablesInput input) throws Exception {
		try {
			final DataTablesOutput<Supplier> domainOut = supplierDao.findAll(input, specOriginalSupplier());
			return SupplierMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}

	private Specification<Supplier> specOriginalSupplier() {
		final Specification<Supplier> spec = (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("virtualBusiness"), Boolean.FALSE));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return spec;
	}

	@Override
	public List<SupplierDTO> findAllSupplierByUserLevel() {
		List<SupplierDTO> supplierDTOs = null;
		try {
			Iterable<Supplier> suppliers = null;
			if (AuthenticationUtil.isAuthUserAdminLevel()) {
				suppliers = supplierDao.findAll();
			} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
				suppliers = supplierDao.findAll( getSystemUserBusinessSpecification(AuthenticationUtil.getLoginUserBusiness().getId()));
			} else {
				suppliers = supplierDao.findAll(getGeneralUserBusinessSpecification(AuthenticationUtil.getLoginSite().getSite().getId()));
			}
			supplierDTOs = SupplierMapper.getInstance().domainToDTOList(suppliers);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return supplierDTOs;
	}

}
