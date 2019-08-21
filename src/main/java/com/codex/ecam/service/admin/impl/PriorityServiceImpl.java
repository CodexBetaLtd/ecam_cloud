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

import com.codex.ecam.dao.admin.PriorityDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dto.admin.PriorityDTO;
import com.codex.ecam.mappers.admin.PriorityMapper;
import com.codex.ecam.model.maintenance.Priority;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.PriorityResult;
import com.codex.ecam.service.admin.api.PriorityService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.admin.PrioritySearchPropertyMapper;

@Service
public class PriorityServiceImpl implements PriorityService {

	@Autowired
	private PriorityDao priorityDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public DataTablesOutput<PriorityDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<Priority> domainOut;

		PrioritySearchPropertyMapper.getInstance().generateDataTableInput(input);

		if(AuthenticationUtil.isAuthUserAdminLevel()){
			domainOut = priorityDao.findAll(input);
		} else {
			Specification<Priority> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = priorityDao.findAll(input, specification);
		}

		DataTablesOutput<PriorityDTO> out = PriorityMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public PriorityDTO findById(Integer id) throws Exception {
		Priority domain = findEntityById(id);
		if (domain != null) {
			return PriorityMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public PriorityResult delete(Integer id) {
		PriorityResult result = new PriorityResult(null, null);
		try {
			priorityDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Priority Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Priority Already Assigned. Please Remove from Assigned Priority and try again.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Override
	public PriorityResult save(PriorityDTO dto) throws Exception {
		PriorityResult result = createPriorityResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Priority Already updated. Please Reload Priority.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(PriorityResult result) throws Exception {
		PriorityMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBusiness(result);
		priorityDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private PriorityResult createPriorityResult(PriorityDTO dto) {
		PriorityResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new PriorityResult(priorityDao.findOne(dto.getId()), dto);
		} else {
			result = new PriorityResult(new Priority(), dto);
		}

		return result;
	}

	private String getMessageByAction(PriorityDTO dto) {
		if (dto.getId() == null) {
			return "Priority Added Successfully.";
		} else {
			return "Priority Updated Successfully.";
		}
	}

	private void setBusiness(PriorityResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	@Override
	public void saveAll(List<PriorityDTO> allDummyData) {
		for (PriorityDTO dto : allDummyData) {
			try {
				save(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAll() {
		priorityDao.deleteAll();
	}

	@Override
	public List<PriorityDTO> findAll() {
		try {
			List<Priority> priorities;			
			if(AuthenticationUtil.isAuthUserAdminLevel()){
				priorities = (List<Priority>) priorityDao.findAll();
			} else {
				Specification<Priority> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
				priorities = (List<Priority>) priorityDao.findAll(specification);
			}			
			return PriorityMapper.getInstance().domainToDTOList(priorities);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<PriorityDTO> findAllByBusiness(Integer id) {
		try {
			List<PriorityDTO> dtoOut = new ArrayList<>();
			Specification<Priority> specification = (root, query, cb) -> cb.equal(root.get("business").get("id"), id);
			
			if (specification != null) {				
				List<Priority> priorities = (List<Priority>) priorityDao.findAll(specification); 
				dtoOut = PriorityMapper.getInstance().domainToDTOList(priorities);
			}
			return dtoOut;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Priority findEntityById(Integer id) {
		return priorityDao.findById(id);
	}

}
