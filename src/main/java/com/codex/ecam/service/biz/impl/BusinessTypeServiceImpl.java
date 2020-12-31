package com.codex.ecam.service.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.biz.BusinessTypeDao;
import com.codex.ecam.dto.biz.business.BussinessTypeDTO;
import com.codex.ecam.mappers.admin.BussinesTypeMapper;
import com.codex.ecam.model.biz.business.BusinessTypeDefinition;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.BusinessTypeResult;
import com.codex.ecam.service.biz.api.BusinessTypeService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.biz.BusinessTypeSearchPropertyMapper;

@Service
public class BusinessTypeServiceImpl implements BusinessTypeService {

	@Autowired
	private BusinessTypeDao businessTypeDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public DataTablesOutput<BussinessTypeDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<BusinessTypeDefinition> domainOut;

		BusinessTypeSearchPropertyMapper.getInstance().generateDataTableInput(input);

		if(AuthenticationUtil.isAuthUserAdminLevel()){
			domainOut = businessTypeDao.findAll(input);
		} else {
			Specification<BusinessTypeDefinition> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = businessTypeDao.findAll(input, specification);
		}

		DataTablesOutput<BussinessTypeDTO> out = BussinesTypeMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public BussinessTypeDTO findById(Integer id) throws Exception {
		BusinessTypeDefinition domain = businessTypeDao.findById(id);
		if (domain != null) {
			return BussinesTypeMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public BusinessTypeResult delete(Integer id) {

		BusinessTypeResult result = new BusinessTypeResult(null, null);

		try {
			businessTypeDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Business Type Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Business Type Already Assigned. Please Remove from Assigned Business Type and try again.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public BusinessTypeResult save(BussinessTypeDTO dto) throws Exception {

		BusinessTypeResult result = createBusinessTypeResult(dto);

		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Business Type Already updated. Please Reload Business Type.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(BusinessTypeResult result) throws Exception {
		BussinesTypeMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBusiness(result);
		businessTypeDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private BusinessTypeResult createBusinessTypeResult(BussinessTypeDTO dto) {
		BusinessTypeResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new BusinessTypeResult(businessTypeDao.findOne(dto.getId()), dto);
		} else {
			result = new BusinessTypeResult(new BusinessTypeDefinition(), dto);
		}

		return result;
	}

	private String getMessageByAction(BussinessTypeDTO dto) {
		if (dto.getId() == null) {
			return "Business Type Added Successfully.";
		} else {
			return "Business Type Updated Successfully.";
		}
	}

	private void setBusiness(BusinessTypeResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	@Override
	public List<BussinessTypeDTO> findAll() {
		Iterable<BusinessTypeDefinition> domainList = businessTypeDao.findAll();
		try {
			return BussinesTypeMapper.getInstance().domainToDTOList(domainList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void saveAll(List<BussinessTypeDTO> allDummyData) {
		for (BussinessTypeDTO dto : allDummyData) {
			try {
				save(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAll() {
		businessTypeDao.deleteAll();
	}

}
