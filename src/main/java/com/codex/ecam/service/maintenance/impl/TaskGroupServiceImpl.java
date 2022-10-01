package com.codex.ecam.service.maintenance.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.codex.ecam.dao.asset.AssetCategoryTaskDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.maintenance.TaskDao;
import com.codex.ecam.dao.maintenance.TaskGroupDao;
import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.dto.maintenance.task.TaskDTO;
import com.codex.ecam.dto.maintenance.task.TaskGroupDTO;
import com.codex.ecam.exception.maintenance.TaskGroupException;
import com.codex.ecam.mappers.admin.UserMapper;
import com.codex.ecam.mappers.maintenance.TaskGroupMapper;
import com.codex.ecam.mappers.maintenance.TaskMapper;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.admin.UserSite;
import com.codex.ecam.model.admin.UserSiteGroup;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.asset.AssetCategoryTask;
import com.codex.ecam.model.maintenance.task.Task;
import com.codex.ecam.model.maintenance.task.TaskGroup;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.maintenance.TaskGroupResult;
import com.codex.ecam.service.maintenance.api.TaskGroupService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.taskgroup.TaskGroupSearchPropertyMapper;

@Service
public class TaskGroupServiceImpl implements TaskGroupService {

	private final static Logger logger = LoggerFactory.getLogger(TaskGroupServiceImpl.class);

	@Autowired
	private TaskGroupDao taskGroupDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private AssetCategoryTaskDao assetCategoryTaskDao;

	private TaskGroupDTO findDTOById(Integer id) throws TaskGroupException {
		try {
			return TaskGroupMapper.getInstance().domainToDto(findEntityById(id));
		} catch (final Exception e) {
			throw new TaskGroupException("ERROR! Task Group mapper not worked!");
		}
	}

	private TaskGroup findEntityById(Integer id) throws TaskGroupException {
		try {
			return taskGroupDao.findOne(id);
		} catch (final Exception e) {
			throw new TaskGroupException("ERROR! Task Group entity FETCH not completed.!");
		}
	}

	@Override
	public TaskGroupResult newTaskGroup() {
		final TaskGroupResult result = new TaskGroupResult(null, new TaskGroupDTO());
		try {
			newTaskGroup(result);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Task Group create operation completed.");
		} catch (final TaskGroupException e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Task Group NOT created.");
		}
		return result;
	}

	private void newTaskGroup(TaskGroupResult result) throws TaskGroupException {
		result.getDtoEntity().setActive(Boolean.TRUE);
		result.getDtoEntity().setTasks(new ArrayList<>());
	}

	@Override
	public TaskGroupResult findById(Integer id) throws TaskGroupException {
		final TaskGroupResult result = new TaskGroupResult(null, null);
		try {
			result.setDtoEntity(findDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Task Group fetch operation completed.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Task Group fetch operation failed.");
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Override
	public TaskGroupResult delete(Integer id) {
		final TaskGroupResult result = new TaskGroupResult(null, null);
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Task group operation deleted operation completed.");
		} catch (final DataIntegrityViolationException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Task group Already Used. Cannot delete.");
			logger.error(ex.getMessage());
		} catch (final TaskGroupException ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("FAILED! Task group delete operation not completed");
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Override
	public TaskGroupResult deleteMultiple(Integer[] ids) throws Exception {
		final TaskGroupResult result = new TaskGroupResult(null, null);
		try {
			for (final Integer id : ids) {
				deleteEntityById(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Task Group(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Task Group(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = TaskGroupException.class)
	private void deleteEntityById(Integer id) throws TaskGroupException {
		taskGroupDao.delete(id);
	}

	@Override
	public TaskGroupResult save(TaskGroupDTO dto) {
		final TaskGroupResult result = createTaskGroupResult(dto);
		try {
			saveOrUpdate(result);
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Task Group Already updated. Please Reload Task Group.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	private TaskGroupResult createTaskGroupResult(TaskGroupDTO dto) {
		TaskGroupResult result;
		if (dto.getId() != null && dto.getId() > 0) {
			result = new TaskGroupResult(taskGroupDao.findOne(dto.getId()), dto);
		} else {
			result = new TaskGroupResult(new TaskGroup(), dto);
		}

		return result;
	}

	private String getMessageByAction(TaskGroupResult result) {
		if (result.getDtoEntity().getId() == null) {
			return "Task Group Added Successfully.";
		} else {
			return "Task Group Updated Successfully.";
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(TaskGroupResult result) throws Exception {
		TaskGroupMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setData(result);
		taskGroupDao.save(result.getDomainEntity());
		result.addToMessageList(getMessageByAction(result));
		result.setDtoEntity(findDTOById(result.getDomainEntity().getId()));
	}

	private void setData(TaskGroupResult result) throws Exception {
		setTask(result);
		setBusiness(result);
	}

	private void setBusiness(TaskGroupResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getBusinessId() != null) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setTask(TaskGroupResult result) throws Exception {
		final Set<Task> tasks = new HashSet<>();
		for (final TaskDTO taskDTO : result.getDtoEntity().getTasks()) {
			Task task;
			if (taskDTO.getId() != null) {
				task = result.getDomainEntity().getTasks().stream().filter((x) -> x.getId().equals(taskDTO.getId()))
						.findAny().orElseGet(Task::new);
			} else {
				task = new Task();
			}
			TaskMapper.getInstance().dtoToDomain(taskDTO, task);
			task.setTaskGroup(result.getDomainEntity());

			tasks.add(task);
		}

		result.getDomainEntity().setTasks(tasks);
	}

	@Override
	public DataTablesOutput<TaskGroupDTO> findAll(FocusDataTablesInput input) throws Exception {
		try {
			DataTablesOutput<TaskGroup> domainOut = new DataTablesOutput<>();
			TaskGroupSearchPropertyMapper.getInstance().generateDataTableInput(input);
			if (AuthenticationUtil.isAuthUserAdminLevel()) {
				domainOut = taskGroupDao.findAll(input);
			} else {
				domainOut = taskGroupDao.findAll(input, findAllSpecification());
			}
			return TaskGroupMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}

	@Override
	public DataTablesOutput<TaskGroupDTO> findTaskGroupByBusiness(FocusDataTablesInput input, Integer bizId)
			throws Exception {
		try {
			DataTablesOutput<TaskGroup> domainOut = new DataTablesOutput<>();
			TaskGroupSearchPropertyMapper.getInstance().generateDataTableInput(input);
			final Specification<TaskGroup> specification = (root, query, cb) -> {
				return cb.equal(root.get("business").get("id"), bizId);
			};
			domainOut = taskGroupDao.findAll(input, specification);
			return TaskGroupMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}

	private Specification<TaskGroup> findAllSpecification() {
		return (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("business").get("id"), AuthenticationUtil.getLoginUserBusiness().getId()));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

	@Override
	public List<TaskGroupDTO> findAll() throws Exception {
		final List<TaskGroup> domainOut = (List<TaskGroup>) taskGroupDao.findAll();
		final List<TaskGroupDTO> out = TaskGroupMapper.getInstance().domainToDTOList(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<TaskDTO> findAllTasksByTaskGroup(FocusDataTablesInput input, Integer id) throws Exception {
		final Specification<Task> specification = (root, query, cb) -> cb.equal(root.get("taskGroup").get("id"), id);
		final DataTablesOutput<Task> domainOut = taskDao.findAll(input, specification);
		final DataTablesOutput<TaskDTO> out = TaskMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public List<TaskDTO> findAllTasksByTaskGroup(Integer id) throws Exception {
		final List<Task> domainOut = taskDao.findTaskByTaskGroup(id);
		final List<TaskDTO> out = TaskMapper.getInstance().domainToDTOList(domainOut);
		return out;
	}

	public List<TaskDTO> findAllTasksByAssetCategory(Integer assetId) throws Exception {
		final Asset asset = assetDao.findOne(assetId);
		final Specification<AssetCategoryTask> specification = (root, query, cb) -> {
			return cb.equal(root.get("assetCategory").get("id"), asset.getAssetCategory().getId());
		};

		final List<AssetCategoryTask> assetCategoryTasks = assetCategoryTaskDao.findAll(specification);
		final List<Task> domainOut = new ArrayList<>();
		for (final AssetCategoryTask categoryTask : assetCategoryTasks) {
			domainOut.add(categoryTask.getTask());
		}

		final List<TaskDTO> out = TaskMapper.getInstance().domainToDTOList(domainOut);
		return out;
	}

	@Override
	public TaskDTO findTasksById(Integer id) throws Exception {
		return TaskMapper.getInstance().domainToDto(taskDao.findOne(id));
	}
}
