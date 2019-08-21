package com.neolith.focus.service.maintenance.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.dao.admin.UserDao;
import com.neolith.focus.dao.asset.AssetDao;
import com.neolith.focus.dao.biz.BusinessDao;
import com.neolith.focus.dao.maintenance.ProjectDao;
import com.neolith.focus.dto.admin.UserDTO;
import com.neolith.focus.dto.maintenance.ProjectDTO;
import com.neolith.focus.exception.admin.UserException;
import com.neolith.focus.exception.setting.ProjectException;
import com.neolith.focus.exception.setting.business.BusinessException;
import com.neolith.focus.mappers.maintenance.ProjectMapper;
import com.neolith.focus.model.maintenance.project.Project;
import com.neolith.focus.model.maintenance.project.ProjectUser;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.maintenance.ProjectResult;
import com.neolith.focus.service.maintenance.api.ProjectService;
import com.neolith.focus.util.AuthenticationUtil;
import com.neolith.focus.util.search.project.ProjectSearchPropertyMapper;

@Service
public class ProjectServiceImpl implements ProjectService {


	private final static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private UserDao userDao;

	private ProjectDTO findDTOById(Integer id) throws ProjectException {
		try {
			return ProjectMapper.getInstance().domainToDto(findEntityById(id));
		} catch (Exception e) {
			throw new ProjectException("ERROR! Project mapper not worked!");
		}
	}

	private Project findEntityById(Integer id) throws ProjectException {
		try {
			return projectDao.findOne(id);
		} catch (Exception e) {
			throw new ProjectException("ERROR! Project entity FETCH not completed.!");
		}
	}

	@Override
	public ProjectResult newProject() {
		ProjectResult result = new ProjectResult(null, new ProjectDTO());
		try {
			newProject(result);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Project create operation completed.");
		} catch (ProjectException e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Project NOT created.");
		}
		return result;
	}

	private void newProject(ProjectResult result) throws ProjectException {
		result.getDtoEntity().setIsDeleted(Boolean.FALSE);
	}

	@Override
	public ProjectResult findById(Integer id) {
		ProjectResult result = new ProjectResult(null, new ProjectDTO());
		try {
			result.setDtoEntity(findDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Project fetch operation completed.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Project fetch operation failed.");
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Override
	public ProjectResult save(ProjectDTO dto) {
		ProjectResult result = new ProjectResult(new Project(), dto);
		try {
			saveOrUpdate(result);
			result.addToMessageList("SUCCESS! Project SAVE operation completed.");
			result.setResultStatusSuccess();
		} catch (ProjectException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Project SAVE operation not completed");
			logger.error(ex.getMessage());
		} catch (BusinessException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Project SAVE operation not completed, Business Error");
			logger.error(ex.getMessage());
		} catch (UserException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Project SAVE operation not completed, User Error");
			logger.error(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public ProjectResult update(ProjectDTO dto) {
		ProjectResult result = new ProjectResult(null, dto);
		try {
			Project project = projectDao.findOne(dto.getId());
			result.setDomainEntity(project);
			saveOrUpdate(result);
			result.addToMessageList("Project Updated Successfully.");
		} catch (ProjectException e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Project UPDATE operation not completed");
			logger.error(e.getMessage());
		} catch (BusinessException e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Project UPDATE operation not completed, Business Error");
			logger.error(e.getMessage());
		} catch (UserException e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Project UPDATE operation not completed, User Error");
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
			logger.error(e.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(ProjectResult result) throws ProjectException, BusinessException, UserException, Exception {
		ProjectMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setDomainData(result);
		projectDao.save(result.getDomainEntity());
		result.setDtoEntity(findDTOById(result.getDomainEntity().getId()));
	}

	private void setDomainData(ProjectResult result) throws ProjectException, BusinessException, UserException {
		setBusiness(result);
		setSite(result);
		setProjectUser(result);
	}

	private void setBusiness(ProjectResult result) throws BusinessException {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setProjectUser(ProjectResult result) throws ProjectException, UserException {
		Set<ProjectUser> projectUsers = new HashSet<>();
		for (UserDTO userDTO : result.getDtoEntity().getUsers()) {
			ProjectUser projectUser = new ProjectUser();
			if (userDTO.getId() != null) {
				projectUser = result.getDomainEntity().getProjectUsers().stream() .filter((x) -> x.getUser().getId().equals(userDTO.getId())).findAny().orElseGet(ProjectUser::new);
			} else {
				projectUser = new ProjectUser();
			}
			projectUser.setUser(userDao.findOne(userDTO.getId()));
			projectUser.setProject(result.getDomainEntity());
			projectUser.setIsDeleted(false);
			projectUsers.add(projectUser);
		}
		result.getDomainEntity().setProjectUsers(projectUsers);
	}

	private void setSite(ProjectResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getSiteId() != null)) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		}
	}

	@Override
	public ProjectResult delete(Integer id) {
		ProjectResult result = new ProjectResult(null, null);
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Project delete operation completed.");
		} catch (DataIntegrityViolationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Project Already Used. Cannot delete.");
			logger.error(ex.getMessage());
		} catch (ProjectException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Project delete operation not completed");
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ProjectException.class)
	private void deleteEntityById(Integer id) throws ProjectException {
		projectDao.delete(id);
	}

	@Override
	public DataTablesOutput<ProjectDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<Project> domainOut;
		ProjectSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = projectDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<Project> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = projectDao.findAll(input, specification);
		} else {
			Specification<Project> specification = (root, query, cb) -> cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
			domainOut = projectDao.findAll(input, specification);
		}
		DataTablesOutput<ProjectDTO> out = ProjectMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<ProjectDTO> findProjectByBusiness(FocusDataTablesInput input, Integer id) {
		try {
			if (id == null) {
				logger.error("ID Cannot be null");
				return new DataTablesOutput<>();
			}
			DataTablesOutput<Project> domainOut = projectDao.findAll(input, specFindByBusiness(id));
			return ProjectMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new DataTablesOutput<>();
		}
	}

	private Specification<Project> specFindByBusiness(Integer id) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("business").get("id"), id));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

}
