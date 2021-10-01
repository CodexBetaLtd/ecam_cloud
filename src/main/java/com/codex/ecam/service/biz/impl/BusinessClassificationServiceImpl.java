package com.codex.ecam.service.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.biz.BusinessClassificationDao;
import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;
import com.codex.ecam.mappers.admin.BusinessClassificationMapper;
import com.codex.ecam.model.biz.business.BusinessClassification;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AccountResult;
import com.codex.ecam.result.admin.BusinessClassificationResult;
import com.codex.ecam.service.biz.api.BusinessClassificationService;

@Service
public class BusinessClassificationServiceImpl implements BusinessClassificationService {

	@Autowired
	private BusinessClassificationDao businessClassificationDao;

	@Override
	public List<BusinessClassificationDTO> findAll() {
		final Iterable<BusinessClassification> domainList = businessClassificationDao.findAll();
		try {
			return BusinessClassificationMapper.getInstance().domainToDTOList(domainList);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public DataTablesOutput<BusinessClassificationDTO> findAllDataTable(FocusDataTablesInput input) throws Exception {
		final DataTablesOutput<BusinessClassification> domainOut = businessClassificationDao.findAll(input);
		DataTablesOutput<BusinessClassificationDTO> out = null;
		try {
			out =BusinessClassificationMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return out;
	}

	@Override
	public BusinessClassificationDTO findById(Integer id) throws Exception {
		final BusinessClassification domain = businessClassificationDao.findById(id);
		if (domain != null) {
			return BusinessClassificationMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public BusinessClassificationResult delete(Integer id) {
		final BusinessClassificationResult result = new BusinessClassificationResult(null, null);
		try {
			businessClassificationDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Business Classification Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Business classification Already Assigned. Please Remove from Assigned Business and try again.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BusinessClassificationResult deleteMultiple(Integer[] ids) throws Exception {
		final BusinessClassificationResult result = new BusinessClassificationResult(null, null);
		try {
			for (final Integer id : ids) {
				businessClassificationDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Business Classification(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Business Classification(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public BusinessClassificationResult save(BusinessClassificationDTO dto) throws Exception {
		final BusinessClassificationResult result = createBusinessClassificationResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Account Already updated. Please Reload Account.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(BusinessClassificationResult result) throws Exception {
		BusinessClassificationMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		businessClassificationDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private BusinessClassificationResult createBusinessClassificationResult(BusinessClassificationDTO dto) {
		BusinessClassificationResult result;
		if (dto.getId() != null && dto.getId() > 0) {
			result = new BusinessClassificationResult(businessClassificationDao.findOne(dto.getId()), dto);
		} else {
			result = new BusinessClassificationResult(new BusinessClassification(), dto);
		}

		return result;
	}

	private String getMessageByAction(BusinessClassificationDTO dto) {
		if (dto.getId() == null) {
			return "BusinessClassification Added Successfully.";
		} else {
			return "BusinessClassification Updated Successfully.";
		}
	}


	@Override
	public void saveAll(List<BusinessClassificationDTO> allDummyData) {
		for (final BusinessClassificationDTO dto : allDummyData) {
			try {
				save(dto);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAll() {
		businessClassificationDao.deleteAll();
	}
}
