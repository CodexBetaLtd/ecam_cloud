package com.codex.ecam.service.admin.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.ChargeDepartmentDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dto.admin.ChargeDepartmentDTO;
import com.codex.ecam.mappers.admin.ChargeDepartmentMapper;
import com.codex.ecam.model.maintenance.ChargeDepartment;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.ChargeDepartmentResult;
import com.codex.ecam.service.admin.api.ChargeDepartmentService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.admin.ChargeDepartmentSearchPropertyMapper;

@Service
public class ChargeDepartmentServiceImpl implements ChargeDepartmentService {

	@Autowired
	private ChargeDepartmentDao chargeDepartmentDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public DataTablesOutput<ChargeDepartmentDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<ChargeDepartment> domainOut;

		ChargeDepartmentSearchPropertyMapper.getInstance().generateDataTableInput(input);

		if(AuthenticationUtil.isAuthUserAdminLevel()){
			domainOut = chargeDepartmentDao.findAll(input);
		} else {
			Specification<ChargeDepartment> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = chargeDepartmentDao.findAll(input, specification);
		}

		DataTablesOutput<ChargeDepartmentDTO> out = ChargeDepartmentMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public ChargeDepartmentDTO findById(Integer id) throws Exception {
		ChargeDepartment domain = findEntityById(id);
		if (domain != null) {
			return ChargeDepartmentMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public ChargeDepartmentResult delete(Integer id) {
		ChargeDepartmentResult result = new ChargeDepartmentResult(null, null);
		try {
			chargeDepartmentDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Charge Department Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Charge Department Already Assigned. Please Remove from Assigned Charge Department and try again.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public ChargeDepartmentResult save(ChargeDepartmentDTO dto) throws Exception {
		ChargeDepartmentResult result = createAccountResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Charge Department Already updated. Please Reload Charge Department.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(ChargeDepartmentResult result) throws Exception {
		ChargeDepartmentMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBusiness(result);
		chargeDepartmentDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private ChargeDepartmentResult createAccountResult(ChargeDepartmentDTO dto) {
		ChargeDepartmentResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new ChargeDepartmentResult(chargeDepartmentDao.findOne(dto.getId()), dto);
		} else {
			result = new ChargeDepartmentResult(new ChargeDepartment(), dto);
		}

		return result;
	}

	private String getMessageByAction(ChargeDepartmentDTO dto) {
		if (dto.getId() == null) {
			return "Charge Department Added Successfully.";
		} else {
			return "Charge Department Updated Successfully.";
		}
	}

	private void setBusiness(ChargeDepartmentResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}
	
	@Override
	public List<ChargeDepartmentDTO> findAll() {
		try {
			Iterable<ChargeDepartment> domainList; 
			if(AuthenticationUtil.isAuthUserAdminLevel()){
				domainList = chargeDepartmentDao.findAll();
			} else {
				Specification<ChargeDepartment> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
				domainList = chargeDepartmentDao.findAll(specification);
			} 
			return ChargeDepartmentMapper.getInstance().domainToDTOList(domainList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ChargeDepartmentDTO> findAllByBusiness(Integer id) {
		try {
			List<ChargeDepartmentDTO> dtoOut = new ArrayList<>();
			Specification<ChargeDepartment> specification = (root, query, cb) -> cb.equal(root.get("business"), id);
			
			if( specification != null){  
				List<ChargeDepartment> domainList = chargeDepartmentDao.findAll(specification);
				dtoOut = ChargeDepartmentMapper.getInstance().domainToDTOList(domainList);
			} 
			return dtoOut;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void saveAll(List<ChargeDepartmentDTO> list) {
		for (ChargeDepartmentDTO dto : list) {
			try {
				save(dto);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}

	@Override
	public void deleteAll() {
		chargeDepartmentDao.deleteAll();

	}

	@Override
	public ChargeDepartment findEntityById(Integer id) {
		return chargeDepartmentDao.findById(id);
	}

}
