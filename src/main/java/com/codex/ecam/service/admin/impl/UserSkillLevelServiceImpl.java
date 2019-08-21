package com.codex.ecam.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.UserSkillLevelDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dto.admin.UserSkillLevelDTO;
import com.codex.ecam.mappers.admin.UserSkillLevelMapper;
import com.codex.ecam.model.admin.UserSkillLevel;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.UserSkillLevelResult;
import com.codex.ecam.service.admin.api.UserSkillLevelService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.admin.UserSkillLevelSearchPropertyMapper;

@Service
public class UserSkillLevelServiceImpl implements UserSkillLevelService {

	@Autowired
	UserSkillLevelDao userSkillLevelDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public DataTablesOutput<UserSkillLevelDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<UserSkillLevel> domainOut;
		
		UserSkillLevelSearchPropertyMapper.getInstance().generateDataTableInput(input);

		if(AuthenticationUtil.isAuthUserAdminLevel()){
			domainOut = userSkillLevelDao.findAll(input);
		} else {
			Specification<UserSkillLevel> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = userSkillLevelDao.findAll(input, specification);
		}

		DataTablesOutput<UserSkillLevelDTO> out = UserSkillLevelMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public UserSkillLevelDTO findById(Integer id) throws Exception {
		UserSkillLevel  domain = findEntityById(id);
		if (domain != null) {
			return UserSkillLevelMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	private UserSkillLevel findEntityById(Integer id) {
		return userSkillLevelDao.findById(id);
	}

	@Override
	public UserSkillLevelResult delete(Integer id) {
		UserSkillLevelResult result = new UserSkillLevelResult(null, null);
		try {
			userSkillLevelDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("User Skill Level Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("User Skill Level Already Assigned. Please Remove from Assigned User Skill Level and try again.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Override
	public UserSkillLevelResult save(UserSkillLevelDTO dto) throws Exception {
		UserSkillLevelResult result = createPriorityResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("User Skill Level Already updated. Please Reload User Skill Level.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(UserSkillLevelResult result) throws Exception {
		UserSkillLevelMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBusiness(result);
		userSkillLevelDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private UserSkillLevelResult createPriorityResult(UserSkillLevelDTO dto) {
		UserSkillLevelResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new UserSkillLevelResult(userSkillLevelDao.findOne(dto.getId()), dto);
		} else {
			result = new UserSkillLevelResult(new UserSkillLevel(), dto);
		}

		return result;
	}

	private String getMessageByAction(UserSkillLevelDTO dto) {
		if (dto.getId() == null) {
			return "User Skill Level Added Successfully.";
		} else {
			return "User Skill Level Updated Successfully.";
		}
	}

	private void setBusiness(UserSkillLevelResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	@Override
	public List<UserSkillLevelDTO> findAll() {
		Iterable<UserSkillLevel> domainList = userSkillLevelDao.findAll();
		try {
			return UserSkillLevelMapper.getInstance().domainToDTOList(domainList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
