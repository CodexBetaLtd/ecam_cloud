package com.codex.ecam.service.asset.impl;

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

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dao.asset.AssetCategoryDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.maintenance.TaskDao;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.dto.asset.SparePartDTO;
import com.codex.ecam.dto.inventory.mrn.MRNItemDTO;
import com.codex.ecam.dto.maintenance.task.TaskDTO;
import com.codex.ecam.mappers.asset.AssetCategoryMapper;
import com.codex.ecam.mappers.maintenance.TaskMapper;
import com.codex.ecam.model.asset.AssetBusiness;
import com.codex.ecam.model.asset.AssetCategory;
import com.codex.ecam.model.asset.AssetCategoryTask;
import com.codex.ecam.model.asset.SparePart;
import com.codex.ecam.model.inventory.mrn.MRNItem;
import com.codex.ecam.model.maintenance.task.Task;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AssetCategoryResult;
import com.codex.ecam.result.admin.AssetCategoryResult;
import com.codex.ecam.service.asset.api.AssetCategoryService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.asset.AssetCategoryPropertyMapper;

@Service
public class AssetCategoryServiceImpl implements AssetCategoryService {

	@Autowired
	private AssetCategoryDao assetCategoryDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private TaskDao taskDao;


	@Override
	public DataTablesOutput<AssetCategoryDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<AssetCategoryDTO> out = null;
		DataTablesOutput<AssetCategory> domainOut;
		AssetCategoryPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = assetCategoryDao.findAll(input);
		} else {
			final Specification<AssetCategory> specification = (root, query, cb) -> cb.equal(root.get("business"),
					AuthenticationUtil.getLoginUserBusiness());
			domainOut = assetCategoryDao.findAll(input, specification);
		}

		try {
			out = AssetCategoryMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public AssetCategoryDTO findById(Integer id) throws Exception {
		final AssetCategory domain = assetCategoryDao.findById(id);
		if (domain != null) {
			return AssetCategoryMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetCategoryResult delete(Integer id) {
		final AssetCategoryResult result = new AssetCategoryResult(null, null);
		try {
			assetCategoryDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("AssetCategory Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset category Already Assigned. Please Remove from Assigned items and try again.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetCategoryResult deleteMultiple(Integer[] ids) throws Exception {
		final AssetCategoryResult result = new AssetCategoryResult(null, null);
		try {
			for (final Integer id : ids) {
				assetCategoryDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("AssetCategory(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("AssetCategory(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetCategoryResult update(AssetCategoryDTO dto) {
		final AssetCategoryResult result = new AssetCategoryResult(null, dto);
		try {
			final AssetCategory domain = assetCategoryDao.findById(dto.getId());
			result.setDomainEntity(domain);
			;
			saveOrUpdate(result);
			result.addToMessageList("Asset Category Updated Successfully.");
		} catch (final Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetCategoryResult save(AssetCategoryDTO dto) {
		final AssetCategoryResult result = new AssetCategoryResult(new AssetCategory(), dto);
		saveOrUpdate(result);
		result.addToMessageList("AssetCategory Added Successfully.");
		return result;
	}

	private void saveOrUpdate(AssetCategoryResult result) {
		try {
			AssetCategoryMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
			setAssetCategoryData(result);
			assetCategoryDao.save(result.getDomainEntity());
			result.updateDtoIdAndVersion();
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("AssetCategory Already updated. Please Reload Asset Category.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
	}

	private void setAssetCategoryData(AssetCategoryResult result) {
		setParent(result.getDtoEntity(), result.getDomainEntity());
		setBusiness(result.getDtoEntity(), result.getDomainEntity());
		setTask(result.getDtoEntity(), result.getDomainEntity());
	}

	private void setTask(AssetCategoryDTO dto, AssetCategory domain) {
		final Set<AssetCategoryTask> assetCategoryTasks = new HashSet<>();
		final List<TaskDTO> taskDTOs = dto.getTasks();
		if (dto.getTasks() != null && dto.getTasks().size() > 0) {
			final Set<AssetCategoryTask> currentassetCategoryTasks = domain.getTasks();

			for (final TaskDTO taskDTO : taskDTOs) {
				AssetCategoryTask assetCategoryTask=new AssetCategoryTask();

				if (currentassetCategoryTasks != null && currentassetCategoryTasks.size() > 0) {
					final AssetCategoryTask optional = currentassetCategoryTasks.stream().filter((x) -> x.getId().equals(taskDTO.getAssetCatgoryTaskId())).findAny().orElseGet(AssetCategoryTask :: new);
					assetCategoryTask = optional;
				} else {
					assetCategoryTask = new AssetCategoryTask();
				}

				assetCategoryTask.setAssetCategory(domain);
				assetCategoryTask.setTask(createTask(taskDTO));
				assetCategoryTask.setIsDeleted(false);
				assetCategoryTasks.add(assetCategoryTask);
			}
			domain.setTasks(assetCategoryTasks);
		}
	}

	private Task createTask(TaskDTO taskDTO) {
		Task task;
		if(taskDTO.getId()!=null){
			task= taskDao.findOne(taskDTO.getId());
		}else{
			task=new Task();
		}

		try {
			TaskMapper.getInstance().dtoToDomain(taskDTO, task);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		taskDao.save(task);
		return task;
	}

	private void setParent(AssetCategoryDTO dto, AssetCategory domain) {
		if (dto.getParentId() != null && dto.getParentId() > 0) {
			domain.setParentAssetCategory(assetCategoryDao.findOne(dto.getParentId()));
		}
	}

	private void setBusiness(AssetCategoryDTO dto, AssetCategory domain) {
		if (dto.getBusinessId() != null && dto.getBusinessId() > 0) {
			domain.setBusiness(businessDao.findOne(dto.getBusinessId()));
		}
	}

	@Override
	public List<AssetCategoryDTO> findAllBySystemCode(Integer systemCode) {
		return null;
	}

	@Override
	public List<AssetCategoryDTO> findByAssetCategoyType(AssetCategoryType type) {
		List<AssetCategory> list = new ArrayList<AssetCategory>();

		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			list = assetCategoryDao.findByAssetCategoyType(type);
		} else {
			list = assetCategoryDao.findByAssetCategoyTypeByBusiness(type,AuthenticationUtil.getLoginUserBusiness().getId());
		}
		try {
			return AssetCategoryMapper.getInstance().domainToDTOList(list);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer findParentIdSystemCode(Integer parentId) throws Exception {
		return assetCategoryDao.findParentIdSystemCode(parentId);
	}

	@Override
	public List<AssetCategoryDTO> findAll() {
		return null;
	}

	@Override
	public void deleteAll() {
		assetCategoryDao.deleteAll();
	}

	@Override
	public void saveAll(List<AssetCategoryDTO> allData) {

		for (final AssetCategoryDTO dto : allData) {
			try {
				save(dto);
			} catch (final Exception e) {

				e.printStackTrace();
			}
		}

	}

	@Override
	public DataTablesOutput<AssetCategoryDTO> findByAssetCategoyType(FocusDataTablesInput input, AssetCategoryType type)
			throws Exception {
		Specification<AssetCategory> specification = null;

		AssetCategoryPropertyMapper.getInstance().generateDataTableInput(input);

		DataTablesOutput<AssetCategoryDTO> out = null;
		DataTablesOutput<AssetCategory> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> cb.equal(root.get("assetCategoryType"), type);
		} else {
			specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()),
					cb.equal(root.get("assetCategoryType"), type));

		}
		domainOut = assetCategoryDao.findAll(input, specification);
		try {
			out = AssetCategoryMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return out;
	}

	@Override
	public DataTablesOutput<AssetCategoryDTO> findByAssetCategoyTypeById(FocusDataTablesInput input, Integer id)
			throws Exception {
		final Specification<AssetCategory> specification = (root, query, cb) -> cb.and(
				cb.equal(root.get("parentAssetCategory").get("id"), id),
				cb.equal(root.get("assetCategoryType"), AssetCategoryType.WAREHOUSE));

		AssetCategoryPropertyMapper.getInstance().generateDataTableInput(input);

		final DataTablesOutput<AssetCategory> domainOut = assetCategoryDao.findAll(input, specification);
		DataTablesOutput<AssetCategoryDTO> out = null;
		try {
			out = AssetCategoryMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return out;
	}
}
