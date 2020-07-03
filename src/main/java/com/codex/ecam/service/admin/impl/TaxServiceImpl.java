package com.codex.ecam.service.admin.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.AccountDao;
import com.codex.ecam.dao.admin.TaxDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dto.admin.AccountDTO;
import com.codex.ecam.dto.admin.cmmssetting.tax.TaxDTO;
import com.codex.ecam.dto.admin.cmmssetting.tax.TaxValueDTO;
import com.codex.ecam.dto.inventory.mrn.MRNItemDTO;
import com.codex.ecam.mappers.admin.AccountMapper;
import com.codex.ecam.mappers.admin.TaxMapper;
import com.codex.ecam.model.admin.Tax;
import com.codex.ecam.model.admin.TaxValue;
import com.codex.ecam.model.inventory.mrn.MRNItem;
import com.codex.ecam.model.maintenance.Account;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AccountResult;
import com.codex.ecam.result.admin.TaxResult;
import com.codex.ecam.service.admin.api.AccountService;
import com.codex.ecam.service.admin.api.TaxService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.admin.AccountSearchPropertyMapper;

@Service
public class TaxServiceImpl implements TaxService {

	@Autowired
	private TaxDao taxDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public DataTablesOutput<TaxDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<Tax> domainOut;

		AccountSearchPropertyMapper.getInstance().generateDataTableInput(input);

		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = taxDao.findAll(input);
		} else {
			Specification<Tax> specification = (root, query, cb) -> cb.equal(root.get("business"),
					AuthenticationUtil.getLoginUserBusiness());
			domainOut = taxDao.findAll(input, specification);
		}

		DataTablesOutput<TaxDTO> out = TaxMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public TaxDTO findById(Integer id) throws Exception {
		Tax domain = findEntityById(id);
		if (domain != null) {
			return TaxMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public Tax findEntityById(Integer id) throws Exception {
		return taxDao.findOne(id);
	}

	@Override
	public TaxResult delete(Integer id) {
		TaxResult result = new TaxResult(null, null);
		try {
			taxDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Tax Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Tax Already Assigned. Please Remove from Assigned Tax and try again.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public TaxResult save(TaxDTO dto) throws Exception {
		TaxResult result = createAccountResult(dto);
		try {
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Tax Already updated. Please Reload Tax.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(TaxResult result) throws Exception {
		TaxMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBusiness(result);
		setTaxValues(result);
		taxDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private TaxResult createAccountResult(TaxDTO dto) {
		TaxResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new TaxResult(taxDao.findOne(dto.getId()), dto);
		} else {
			result = new TaxResult(new Tax(), dto);
		}

		return result;
	}

	private String getMessageByAction(TaxDTO dto) {
		if (dto.getId() == null) {
			return "Tax Added Successfully.";
		} else {
			return "Tax Updated Successfully.";
		}
	}

	private void setTaxValues(TaxResult result) {
		Set<TaxValue> taxValues = new HashSet<>();
		List<TaxValueDTO> taxValueDTOs = result.getDtoEntity().getTaxValueDTOs();

		if (taxValueDTOs != null && taxValueDTOs.size() > 0) {

			Set<TaxValue> currentTaxValues = result.getDomainEntity().getTaxValues();

			for (TaxValueDTO taxValueDTO : taxValueDTOs) {

				TaxValue taxValue = new TaxValue();

				if ((currentTaxValues != null) && (currentTaxValues.size() > 0)) {
					TaxValue optional = currentTaxValues.stream().filter((x) -> x.getId().equals(taxValueDTO.getId()))
							.findAny().orElseGet(TaxValue::new);
					taxValue = optional;
				} else {
					taxValue = new TaxValue();
				}

				createTaxValue(result, taxValueDTO, taxValue);
				taxValues.add(taxValue);
			}
		}
		result.getDomainEntity().setTaxValues(taxValues);
	}

	private void createTaxValue(TaxResult result, TaxValueDTO taxValueDTO, TaxValue taxValue) {
		taxValue.setFromDate(taxValueDTO.getStartDate());
		taxValue.setToDate(taxValueDTO.getEndDate());
		taxValue.setId(taxValueDTO.getId());
		taxValue.setVersion(taxValueDTO.getVersion());
		taxValue.setIsCurrentRate(taxValue.getIsCurrentRate());
		taxValue.setValue(taxValue.getValue());
		taxValue.setIsDeleted(Boolean.FALSE);
		taxValue.setTax(result.getDomainEntity());
	}

	private void setBusiness(TaxResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	@Override
	public List<TaxDTO> findAll() {
		try {
			Iterable<Tax> domainList;
			if (AuthenticationUtil.isAuthUserAdminLevel()) {
				domainList = taxDao.findAll();
			} else {
				Specification<Tax> specification = (root, query, cb) -> cb.equal(root.get("business"),
						AuthenticationUtil.getLoginUserBusiness());
				domainList = taxDao.findAll(specification);
			}
			return TaxMapper.getInstance().domainToDTOList(domainList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TaxDTO> findAllByBusiness(Integer id) {
		try {
			List<TaxDTO> dtoOut = new ArrayList<>();
			Specification<Tax> specification = (root, query, cb) -> cb.equal(root.get("business").get("id"), id);

			if (specification != null) {
				List<Tax> domainList = taxDao.findAll(specification);
				dtoOut = TaxMapper.getInstance().domainToDTOList(domainList);
			}
			return dtoOut;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
