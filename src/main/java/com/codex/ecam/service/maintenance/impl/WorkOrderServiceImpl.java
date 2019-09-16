package com.codex.ecam.service.maintenance.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.dao.admin.*;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.asset.AssetMeterReadingDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.inventory.StockDao;
import com.codex.ecam.dao.maintenance.MiscellaneousExpenseTypeDao;
import com.codex.ecam.dao.maintenance.ProjectDao;
import com.codex.ecam.dao.maintenance.TaskGroupDao;
import com.codex.ecam.dao.maintenance.WorkOrderDao;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingValueDTO;
import com.codex.ecam.dto.maintenance.workOrder.*;
import com.codex.ecam.mappers.asset.AssetMeterReadingValueMapper;
import com.codex.ecam.mappers.maintenance.workorder.WorkOrderFileMapper;
import com.codex.ecam.mappers.maintenance.workorder.WorkOrderMapper;
import com.codex.ecam.mappers.maintenance.workorder.WorkOrderNoteMapper;
import com.codex.ecam.mappers.maintenance.workorder.WorkOrderNotificationMapper;
import com.codex.ecam.mappers.maintenance.workorder.WorkOrderPartMapper;
import com.codex.ecam.model.asset.AssetMeterReading;
import com.codex.ecam.model.asset.AssetMeterReadingValue;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.model.maintenance.miscellaneous.WorkOrderMiscellaneousExpense;
import com.codex.ecam.model.maintenance.workorder.*;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.RestResult;
import com.codex.ecam.result.maintenance.WorkOrderResult;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.maintenance.api.EmailAndNotificationSender;
import com.codex.ecam.service.maintenance.api.WorkOrderService;
import com.codex.ecam.service.maintenance.impl.notification.customstate.WorkOrderState;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.DateUtil;
import com.codex.ecam.util.FileDownloadUtil;
import com.codex.ecam.util.FileUploadUtil;
import com.codex.ecam.util.search.workorder.WorkOrderSearchPropertyMapper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

	private final static Logger logger = LoggerFactory.getLogger(WorkOrderServiceImpl.class);
	
	@Autowired
	Environment environment;
	
	@Autowired
	private WorkOrderDao workOrderDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BusinessDao businessDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private ChargeDepartmentDao chargeDepartmentDao;
	
	@Autowired
	private MaintenanceTypeDao maintenanceTypeDao;
	
	@Autowired
	private PriorityDao priorityDao;
	
	@Autowired
	private AssetDao assetDao;
	
	@Autowired
	private MiscellaneousExpenseTypeDao miscellaneousExpenseTypeDao;
	
	@Autowired
	private EmailAndNotificationSender emailAndNotificationSender;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private StockDao stockDao;
	
	@Autowired
	private AssetMeterReadingDao assetMeterReadingDao;
	
	@Autowired
	private TaskGroupDao taskGroupDao;
	
	@Autowired
	private AssetService assetService;

	@Override
	public WorkOrderDTO findById(Integer id) throws Exception {
		WorkOrder domain = workOrderDao.findById(id);
		if (domain != null) {
			return WorkOrderMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public WorkOrderResult delete(Integer id) {
		WorkOrderResult result = new WorkOrderResult(null, null);
		try {
			workOrderDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Work Order Deleted Successfully.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public WorkOrderResult save(WorkOrderDTO dto) {

		WorkOrderResult result = createWorkOrderResult(dto);
		try {
			saveOrUpdate(result);
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Work Order Already updated. Please Reload Work Order.");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	private WorkOrderResult createWorkOrderResult(WorkOrderDTO dto) {
		WorkOrderResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new WorkOrderResult(workOrderDao.findOne(dto.getId()), dto);
		} else {
			result = new WorkOrderResult(new WorkOrder(), dto);
		}

		return result;
	}

	private String getMessageByAction(WorkOrderResult result) {
		if (result.getDtoEntity().getId() == null) {
			return "Work Order Added Successfully.";
		} else {
			return "Work Order Updated Successfully.";
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(WorkOrderResult result) throws Exception {
		WorkOrderMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setWorkOrderData(result);
		workOrderDao.save(result.getDomainEntity());
		stateChange(result.getDtoEntity());
		result.addToMessageList(getMessageByAction(result));
		result.updateDtoIdAndVersion();
	}

	private void setWorkOrderData(WorkOrderResult result) throws Exception {
		setAssetToWorkOrder(result);
		setWorkOrderNotification(result);
		setWorkOrderMisCost(result);
		setBusiness(result);
		setSite(result);
		setAccount(result);
		setChargeDepartment(result);
		setCompletedBy(result);
		setRequestedBy(result);
		setMaintenanceType(result);
		setPriority(result);
		setProject(result);
		setWorkOrderTask(result);
		setWorkOrderMeterReadings(result);
		setWorkOrderFiles(result);
		setWoNotes(result);
		setWoStatus(result);
	}

	private void setAssetToWorkOrder(WorkOrderResult result) throws Exception {
		Set<WorkOrderAsset> woAssets = new HashSet<>();
		if ((result.getDtoEntity().getAssets() != null) && (result.getDtoEntity().getAssets().size() > 0)) {
			Set<WorkOrderAsset> currentAssets = result.getDomainEntity().getWorkOrderAssets();
			WorkOrderAsset woAsset;
			for (AssetDTO asset : result.getDtoEntity().getAssets()) {
				if ((currentAssets != null) && (currentAssets.size() > 0)) {
					woAsset = currentAssets.stream().filter((x) -> x.getId().equals(asset.getId())).findAny()
							.orElseGet(WorkOrderAsset::new);
				} else {
					woAsset = new WorkOrderAsset();
				}
				createWorkOrderAsset(result.getDomainEntity(), asset, woAsset);
				woAssets.add(woAsset);
			}
		}
		result.getDomainEntity().setWorkOrderAssets(woAssets);
	}

	private void createWorkOrderAsset(WorkOrder domain, AssetDTO asset, WorkOrderAsset woAsset) {
		woAsset.setAsset(assetDao.findOne(asset.getId()));
		woAsset.setWorkOrder(domain);
		woAsset.setIsDeleted(false);
	}

	private void setBusiness(WorkOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setAccount(WorkOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getAccountId() != null)) {
			result.getDomainEntity().setAccount(accountDao.findOne(result.getDtoEntity().getAccountId()));
		}
	}

	private void setChargeDepartment(WorkOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getChargeDepartmentId() != null)) {
			result.getDomainEntity()
					.setChargeDepartment(chargeDepartmentDao.findOne(result.getDtoEntity().getChargeDepartmentId()));
		}
	}

	private void setCompletedBy(WorkOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getCompletedByUserId() != null)) {
			result.getDomainEntity().setCompletedByUser(userDao.findOne(result.getDtoEntity().getCompletedByUserId()));
		}
	}

	private void setRequestedBy(WorkOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getRequestedByUserId() != null)) {
			result.getDomainEntity().setRequestedByUser(userDao.findOne(result.getDtoEntity().getRequestedByUserId()));
		}
	}

	private void setMaintenanceType(WorkOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getMaintenanceTypeId() != null)) {
			result.getDomainEntity()
					.setMaintenanceType(maintenanceTypeDao.findOne(result.getDtoEntity().getMaintenanceTypeId()));
		}
	}

	private void setPriority(WorkOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getPriorityId() != null)) {
			result.getDomainEntity().setPriority(priorityDao.findOne(result.getDtoEntity().getPriorityId()));
		}
	}

	private void setSite(WorkOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getSiteId() != null)) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		}
	}

	private void setProject(WorkOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getProjectId() != null)) {
			result.getDomainEntity().setProject(projectDao.findOne(result.getDtoEntity().getProjectId()));
		}
	}

	private void setWorkOrderNotification(WorkOrderResult result) throws Exception {
		Set<WorkOrderNotification> workOrderNotifications = new HashSet<>();
		List<WorkOrderNotificationDTO> woOrderNotificationDTOs = result.getDtoEntity().getNotifications();

		if ((woOrderNotificationDTOs != null) && (woOrderNotificationDTOs.size() > 0)) {

			Set<WorkOrderNotification> currentWorkOrderNotifications = result.getDomainEntity()
					.getWorkOrderNotifications();
			WorkOrderNotification workOrderNotification;

			for (WorkOrderNotificationDTO workOrderNotificationDTO : woOrderNotificationDTOs) {

				workOrderNotification = new WorkOrderNotification();
				if ((currentWorkOrderNotifications != null) && (currentWorkOrderNotifications.size() > 0)) {
					workOrderNotification = currentWorkOrderNotifications.stream()
							.filter((x) -> x.getId().equals(workOrderNotificationDTO.getId())).findAny()
							.orElseGet(WorkOrderNotification::new);
				} else {
					workOrderNotification = new WorkOrderNotification();
				}
				createNotification(workOrderNotification, workOrderNotificationDTO, result.getDomainEntity());
				workOrderNotifications.add(workOrderNotification);
			}
		}

		result.getDomainEntity().setWorkOrderNotifications(workOrderNotifications);
	}

	private void createNotification(WorkOrderNotification domain, WorkOrderNotificationDTO dto, WorkOrder workOrder)
			throws Exception {
		WorkOrderNotificationMapper.getInstance().dtoToDomain(dto, domain);
		domain.setUser(dto.getUserId() != null ? userDao.findOne(dto.getUserId()) : null);
		domain.setWorkOrder(workOrder);
	}

	private void setWorkOrderMeterReadings(WorkOrderResult result) throws Exception {
		Set<WorkOrderMeterReading> meterReadings = new HashSet<>();
		if ((result.getDtoEntity().getMeterReadings() != null)
				&& (result.getDtoEntity().getMeterReadings().size() > 0)) {

			for (WorkOrderMeterReadingDTO workOrderMeterReadingDTO : result.getDtoEntity().getMeterReadings()) {
				WorkOrderMeterReading workOrderMeterReading;
				if (workOrderMeterReadingDTO.getId() != null) {
					workOrderMeterReading = result.getDomainEntity().getWorkOrderMeterReadings().stream()
							.filter((x) -> x.getId().equals(workOrderMeterReadingDTO.getId())).findAny()
							.orElseGet(WorkOrderMeterReading::new);
				} else {
					workOrderMeterReading = new WorkOrderMeterReading();
				}

				workOrderMeterReading.setAssetMeterReading(setMeterReading(workOrderMeterReadingDTO, result));
				workOrderMeterReading.setDescription(workOrderMeterReadingDTO.getMeterReadingDescription());
				workOrderMeterReading.setWorkOrder(result.getDomainEntity());
				workOrderMeterReading.setIsDeleted(false);

				meterReadings.add(workOrderMeterReading);
			}

			result.getDomainEntity().setWorkOrderMeterReadings(meterReadings);
		}
	}

	private AssetMeterReading setMeterReading(WorkOrderMeterReadingDTO workOrderMeterReadingDTO, WorkOrderResult result)
			throws Exception {
		AssetMeterReading assetMeterReading = assetMeterReadingDao
				.findOne(workOrderMeterReadingDTO.getMeterReadingId());
		List<AssetMeterReadingValueDTO> assetMeterReadingValueDTOs = getAssetMeterReadingValue(result,
				workOrderMeterReadingDTO);
		updateMeterReading(assetMeterReading, assetMeterReadingValueDTOs, workOrderMeterReadingDTO);
		assetService.updateAverageMeterReadingValue(assetMeterReading);
		return assetMeterReading;
	}

	private synchronized void updateMeterReading(AssetMeterReading domain,
			List<AssetMeterReadingValueDTO> assetMeterReadingValueDTOs,
			WorkOrderMeterReadingDTO workOrderMeterReadingDTO) throws Exception {
		if (domain.getAssetMeterReadingValues() == null) {
			domain.setAssetMeterReadingValues(new HashSet<AssetMeterReadingValue>());
		}

		for (AssetMeterReadingValueDTO assetMeterReadingValueDto : assetMeterReadingValueDTOs) {
			if ((workOrderMeterReadingDTO.getMeterReadingCurrentValueIndex() != null) && (workOrderMeterReadingDTO.getMeterReadingCurrentValueIndex() >= 0)) {
				AssetMeterReadingValue meterReadingValue = new AssetMeterReadingValue();
				AssetMeterReadingValueMapper.getInstance().dtoToDomain(assetMeterReadingValueDto, meterReadingValue);
				meterReadingValue.setAssetMeterReading(domain);
				if (workOrderMeterReadingDTO.getMeterReadingCurrentValueIndex().equals(assetMeterReadingValueDto.getAssetMeterReadingValueIndex())) {
					domain.setCurrentAssetMeterReadingValue(meterReadingValue);
				}
				domain.getAssetMeterReadingValues().add(meterReadingValue);
			}
		}
	}

	private List<AssetMeterReadingValueDTO> getAssetMeterReadingValue(WorkOrderResult result,
			WorkOrderMeterReadingDTO workOrderMeterReadingDTO) {
		List<AssetMeterReadingValueDTO> assetMeterReadingValueDTOs = new ArrayList<>();
		for (WorkOrderMeterReadingValueDTO workOrderMeterReadingValueDTO : result.getDtoEntity().getWorkOrderMeterReadingValues()) {
			if (workOrderMeterReadingValueDTO.getWoMeterReadingId().equals(workOrderMeterReadingDTO.getMeterReadingId())) {
				AssetMeterReadingValueDTO assetMeterReadingValueDTO = new AssetMeterReadingValueDTO();
				assetMeterReadingValueDTO.setAssetMeterReadingValueId(workOrderMeterReadingValueDTO.getWoMeterReadingValueId());
				assetMeterReadingValueDTO.setAssetMeterReadingValueAddedDate(workOrderMeterReadingValueDTO.getWoMeterReadingValueAddedDate());
				assetMeterReadingValueDTO.setAssetMeterReadingValue(workOrderMeterReadingValueDTO.getWoMeterReadingValue());
				assetMeterReadingValueDTO.setAssetMeterReadingValueIndex(workOrderMeterReadingValueDTO.getWoMeterReadingValueIndex());
				assetMeterReadingValueDTOs.add(assetMeterReadingValueDTO);
			}
		}

		return assetMeterReadingValueDTOs;
	}

	private void setWorkOrderMisCost(WorkOrderResult result) {
		Set<WorkOrderMiscellaneousExpense> workOrderMiscellaneousExpenses = new HashSet<>();
		for (MiscellaneousExpenseDTO miscellaneousExpenseDTO : result.getDtoEntity().getMiscellaneousExpenses()) {
			WorkOrderMiscellaneousExpense miscellaneousExpense = new WorkOrderMiscellaneousExpense();
			miscellaneousExpense.setMiscellaneousExpenseType( miscellaneousExpenseTypeDao.findOne(miscellaneousExpenseDTO.getMiscellaneousExpenseTypeId()));
			miscellaneousExpense.setEstimatedUnitCost(miscellaneousExpenseDTO.getEstimatedUnitCost());
			miscellaneousExpense.setEstimatedQuantity(miscellaneousExpenseDTO.getEstimatedQuantity());
			miscellaneousExpense.setEstimatedTotalCost(miscellaneousExpenseDTO.getEstimatedTotalCost());
			miscellaneousExpense.setActualUnitCost(miscellaneousExpenseDTO.getActualUnitCost());
			miscellaneousExpense.setActualQuantityCost(miscellaneousExpenseDTO.getActualQuantity());
			miscellaneousExpense.setActualTotalCost(miscellaneousExpenseDTO.getActualTotalCost());
			miscellaneousExpense.setDescription(miscellaneousExpenseDTO.getDescription());
			miscellaneousExpense.setWorkOrder(result.getDomainEntity());
			workOrderMiscellaneousExpenses.add(miscellaneousExpense);
		}

		result.getDomainEntity().setWorkOrderMiscellaneousExpenses(workOrderMiscellaneousExpenses);
	}

	private void setWorkOrderTask(WorkOrderResult result) throws Exception {

		Set<WorkOrderTask> taskList = new HashSet<WorkOrderTask>();
		for (WorkOrderTaskDTO workOrderTaskDTO : result.getDtoEntity().getTasks()) {
			WorkOrderTask workOrderTask;
			if (workOrderTaskDTO.getId() != null) {
				workOrderTask = result.getDomainEntity().getWorkOrderTasks().stream().filter((x) -> x.getId().equals(workOrderTaskDTO.getId())).findAny().orElseGet(WorkOrderTask::new);
			} else {
				workOrderTask = new WorkOrderTask();
			}

			createWorkOrderTask(result, workOrderTaskDTO, workOrderTask);
			setWorkOrderTaskPart(result, workOrderTaskDTO, workOrderTask);

			taskList.add(workOrderTask);
		}

		result.getDomainEntity().setWorkOrderTasks(taskList);
	}

	private void createWorkOrderTask(WorkOrderResult result, WorkOrderTaskDTO workOrderTaskDTO, WorkOrderTask workOrderTask) {
		
		if (workOrderTaskDTO.getTaskGroupId() != null) { 
			workOrderTask.setTaskGroup(taskGroupDao.findOne(workOrderTaskDTO.getTaskGroupId()));
		}

		if (workOrderTaskDTO.getAssignedAssetId() != null) {
			workOrderTask.setAsset(assetDao.findOne(workOrderTaskDTO.getAssignedAssetId()));
		}

		if (workOrderTaskDTO.getAssignedUserId() != null) {
			workOrderTask.setAssignedUser(userDao.findOne(workOrderTaskDTO.getAssignedUserId()));
		}

		if (workOrderTaskDTO.getCompletedUserId() != null) {
			workOrderTask.setCompletedUser(userDao.findOne(workOrderTaskDTO.getCompletedUserId()));
		}

		workOrderTask.setTaskType(workOrderTaskDTO.getTaskType()); 
		workOrderTask.setName(workOrderTaskDTO.getName());
		workOrderTask.setDescription(workOrderTaskDTO.getDescription());
		workOrderTask.setCompletionNote(workOrderTaskDTO.getCompletionNote());
		workOrderTask.setStartedDate(DateUtil.getDateObj(workOrderTaskDTO.getStartedDate()));
		workOrderTask.setEstimatedHours(workOrderTaskDTO.getEstimatedHours());
		workOrderTask.setCompletedDate(DateUtil.getDateObj(workOrderTaskDTO.getCompletedDate()));
		workOrderTask.setSpentHours(workOrderTaskDTO.getSpentHours());
		workOrderTask.setCompletionRemark(workOrderTaskDTO.getCompletionRemark());
		workOrderTask.setWorkOrder(result.getDomainEntity());
		
	}

	private void stateChange(WorkOrderDTO workOrderDTO) {
		WorkOrderState workOrderState = new WorkOrderState();
		workOrderState.setNotificationStateType(workOrderDTO, emailAndNotificationSender);
	}

	private void setWorkOrderTaskPart(WorkOrderResult result, WorkOrderTaskDTO taskDto, WorkOrderTask task) throws Exception {
		
		Set<WorkOrderPart> parts = new HashSet<>();
		if ((result.getDtoEntity().getParts() != null) && (result.getDtoEntity().getParts().size() > 0)) {
			Set<WorkOrderPart> currentParts = task.getWorkOrderParts();

			for (WorkOrderPartDTO partDTO : result.getDtoEntity().getParts()) {

				if (partDTO.getWoPartTaskIndex().equals(taskDto.getIndex())) {

					WorkOrderPart part;
					if ((currentParts != null) && (currentParts.size() > 0)) {
						part = currentParts.stream()
								.filter((x) -> x.getStock().getId().equals(partDTO.getWoPartStockId())).findAny()
								.orElseGet(WorkOrderPart::new);
					} else {
						part = new WorkOrderPart();
					}

					createWorkOrderPart(partDTO, part, result.getDomainEntity(), task);
					parts.add(part);

				}
			}
		}

		result.getDomainEntity().setWorkOrderParts(parts);
	}

	private void createWorkOrderPart(WorkOrderPartDTO dto, WorkOrderPart domain, WorkOrder workOrder, WorkOrderTask task) throws Exception {
		WorkOrderPartMapper.getInstance().dtoToDomain(dto, domain);
		domain.setWorkOrder(workOrder);
		domain.setWorkOrderTask(task);
		domain.setIsDeleted(false);

		if ((dto.getWoPartPartId() != null) && (dto.getWoPartPartId() > 0)) {
			domain.setPart(assetDao.findOne(dto.getWoPartPartId()));
		}

		if ((dto.getWoPartStockId() != null) && (dto.getWoPartStockId() > 0)) {
			domain.setStock(stockDao.findOne(dto.getWoPartStockId()));
		}
	}

	@Override
	public DataTablesOutput<WorkOrderDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<WorkOrder> domainOut;
		WorkOrderSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = workOrderDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<WorkOrder> specification = (root, query, cb) -> cb.equal(root.get("business"),
					AuthenticationUtil.getLoginUserBusiness());
			domainOut = workOrderDao.findAll(input, specification);
		} else {
			Specification<WorkOrder> specification = (root, query, cb) -> cb.equal(root.get("site"),
					AuthenticationUtil.getLoginSite().getSite());
			domainOut = workOrderDao.findAll(input, specification);
		}
		DataTablesOutput<WorkOrderDTO> out = WorkOrderMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<WorkOrderDTO> findAllByProjectId(FocusDataTablesInput input, Integer id) throws Exception {
		WorkOrderSearchPropertyMapper.getInstance().generateDataTableInput(input);
		Specification<WorkOrder> specification = (root, query, cb) -> cb.equal(root.get("project").get("id"), id);
		DataTablesOutput<WorkOrder> domainOut = workOrderDao.findAll(input, specification);
		DataTablesOutput<WorkOrderDTO> out = WorkOrderMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<WorkOrderDTO> findWorkOrdersByDate(FocusDataTablesInput input, String date)
			throws Exception {
		WorkOrderSearchPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<WorkOrder> domainOut;

		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			Specification<WorkOrder> specification = (root, query, cb) -> {
				return getWorkOrderByDatePredicate(date, root, cb);
			};
			domainOut = workOrderDao.findAll(input, specification);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<WorkOrder> specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
				Predicate predicate2 = getWorkOrderByDatePredicate(date, root, cb);
				return cb.and(predicate, predicate2);
			};
			domainOut = workOrderDao.findAll(input, specification);
		} else {
			Specification<WorkOrder> specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
				Predicate predicate2 = getWorkOrderByDatePredicate(date, root, cb);
				return cb.and(predicate, predicate2);
			};
			domainOut = workOrderDao.findAll(input, specification);
		}
		DataTablesOutput<WorkOrderDTO> out = WorkOrderMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	private Predicate getWorkOrderByDatePredicate(String date, Root<WorkOrder> root, CriteriaBuilder cb) {
		return cb.equal(root.get("startDate"), DateUtil.getDateObj(date));
	}

	private void setWorkOrderFiles(WorkOrderResult result) throws Exception {

		Set<WorkOrderFile> workOrderFiles = new HashSet<>();

		if ((result.getDtoEntity().getFiles() != null) && (result.getDtoEntity().getFiles().size() > 0)) {

			Set<WorkOrderFile> currentWorkOrderFiles = result.getDomainEntity().getWorkOrderFiles();

			for (WorkOrderFileDTO workOrderFileDTO : result.getDtoEntity().getFiles()) {

				WorkOrderFile workOrderFile;

				if (workOrderFileDTO.getId() != null) {
					workOrderFile = currentWorkOrderFiles.stream()
							.filter((x) -> x.getId().equals(workOrderFileDTO.getId())).findAny()
							.orElseGet(WorkOrderFile::new);
				} else {
					workOrderFile = new WorkOrderFile();
				}

				WorkOrderFileMapper.getInstance().dtoToDomain(workOrderFileDTO, workOrderFile);
				workOrderFile.setWorkOrder(result.getDomainEntity());
				workOrderFiles.add(workOrderFile);
			}
		}

		result.getDomainEntity().setWorkOrderFiles(workOrderFiles);
	} 
	
	private void setWoNotes(WorkOrderResult result) throws Exception {
		Set<WorkOrderNotes> workOrderNotes = new HashSet<>();
		List<WorkOrderNoteDTO> workOrderNoteDTOs = result.getDtoEntity().getWorkOrderNoteDTOs();

		if (( workOrderNoteDTOs != null) && (workOrderNoteDTOs.size() > 0)) {
			Set<WorkOrderNotes> currentNotes = result.getDomainEntity().getWorkOrderNotes();
			WorkOrderNotes workOrderNote;
			for (WorkOrderNoteDTO noteDTO : workOrderNoteDTOs) {
				if ((currentNotes != null) && (currentNotes.size() > 0)) {
					workOrderNote = currentNotes.stream().filter((x) -> x.getId().equals(noteDTO.getId())).findAny().orElseGet(WorkOrderNotes :: new);
				} else {
					workOrderNote = new WorkOrderNotes();
				}
				createWorkOrderNote(noteDTO, workOrderNote, result.getDomainEntity(), result.getDtoEntity());
				workOrderNotes.add(workOrderNote);
			}
		}
		result.getDomainEntity().setWorkOrderNotes(workOrderNotes);
	}

	private void createWorkOrderNote(WorkOrderNoteDTO dto, WorkOrderNotes domain, WorkOrder workOrder, WorkOrderDTO WorkOrderDTO) throws Exception { 
		WorkOrderNoteMapper.getInstance().dtoToDomain(dto, domain); 
		domain.setWorkOrder(workOrder);
    }  

	private void setWoStatus(WorkOrderResult result) {
		
		Set<WorkOrderNotes> currentNotes = result.getDomainEntity().getWorkOrderNotes();
		
		if (result.getDtoEntity().getCurrentStatusId() != null && currentNotes != null && currentNotes.size() > 0) {
			Optional<WorkOrderNotes> optionalWorkOrderNote = currentNotes.stream().filter((x) -> x.getId() !=null && x.getId().equals(result.getDtoEntity().getCurrentStatusId())).findAny();
			if (optionalWorkOrderNote.isPresent()) {				
				result.getDomainEntity().setCurrentStatus(optionalWorkOrderNote.get());
			}
		} else { 
			setStatusChangeNote(result, currentNotes);
		}
	}

	private void setStatusChangeNote(WorkOrderResult result, Set<WorkOrderNotes> currentNotes) {
		WorkOrderNotes workOrderNote = new WorkOrderNotes();  
		workOrderNote.setNotes("Work Order Start Status is Set as " + result.getDtoEntity().getWorkOrderStatus());
		workOrderNote.setNoteDate(new Date());
		workOrderNote.setWorkOrderStatus(result.getDtoEntity().getWorkOrderStatus());
		workOrderNote.setIsDeleted(Boolean.FALSE);
		workOrderNote.setWorkOrder(result.getDomainEntity());
		currentNotes.add(workOrderNote); 
		result.getDomainEntity().setCurrentStatus(workOrderNote);
	}

	@Override
	public void workorderFileDownload(Integer refId, HttpServletResponse response) throws Exception {
		String uploadLocation = new File(environment.getProperty("upload.location")).getPath();
		if (refId != null) {
			WorkOrderFile file = workOrderDao.findByFileId(refId);
			String externalFilePath = uploadLocation + file.getFileLocation();
			FileDownloadUtil.flushFile(externalFilePath, file.getFileType(), response);
		}
	}

	@Override
	public String workorderFileUpload(MultipartFile file, String refId) throws Exception {
		String uploadFolder = environment.getProperty("upload.workorder.file.folder");
		String uploadLocation = environment.getProperty("upload.location");
		try {
			return FileUploadUtil.createFile(file, refId, uploadFolder, uploadLocation);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RestResult<String> findCurrentWorkOrderCode(Integer businessId) {
		RestResult<String> result = new RestResult<>();
		try {
			if ((businessId != null) && (businessId > 0)) {

				Business business = businessDao.findById(businessId);

				List<WorkOrder> lastInsertwo = workOrderDao.findLastInsertWoByBusiness(businessId);

				StringBuilder code = new StringBuilder("WO/" + String.format("%03d", business.getId()));

				if ((lastInsertwo != null) && (lastInsertwo.size() > 0)) {
					String str[] = lastInsertwo.get(0).getCode().split("/");
					code.append("/" + String.format("%05d", Integer.parseInt(str[2]) + 1));
				} else {
					code.append("/00001");
				}
				result.setData(code.toString());
			}
		} catch (Exception e) {
			result.setStatus(ResultStatus.ERROR);
			result.setMsg("Error Occured while code generation.");
		}
		return result;
	}

	@Override
	public DataTablesOutput<WorkOrderDTO> findAllByBusiness(FocusDataTablesInput dataTablesInput, Integer id) throws Exception {
		DataTablesOutput<WorkOrderDTO> out;
		Specification<WorkOrder> specification = (root, query, cb) -> {
			return cb.equal(root.get("business").get("id"), id);
		};
		DataTablesOutput<WorkOrder> domainOut = workOrderDao.findAll(dataTablesInput, specification);
		out = WorkOrderMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}
	
	@Override
	public WorkOrderResult statusChange(Integer id, WorkOrderStatus status, String date, String note) throws Exception {
		WorkOrderResult result = new WorkOrderResult(workOrderDao.findOne(id), new WorkOrderDTO());
		try {
			setStatusChangeData(result, status, date, note); 
			result.setResultStatusSuccess();
			result.addToMessageList("SUCCESS! Work Order Status has Changed");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Status NOT changed! ");
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void setStatusChangeData(WorkOrderResult result, WorkOrderStatus status, String date, String note) throws Exception {
		setStatus(result, status, date, note);
		workOrderDao.save(result.getDomainEntity());
		updatePartStocks(result, status);
	}

	private void setStatus(WorkOrderResult result, WorkOrderStatus status, String date, String note) {
		WorkOrderNotes workOrderNote = new WorkOrderNotes();
		workOrderNote.setNotes(note);
		workOrderNote.setNoteDate(DateUtil.getDateObj(date));
		workOrderNote.setWorkOrder(result.getDomainEntity());
		workOrderNote.setWorkOrderStatus(status);
		workOrderNote.setIsDeleted(false);
		result.getDomainEntity().getWorkOrderNotes().add(workOrderNote);
		result.getDomainEntity().setCurrentStatus(workOrderNote);
	}

	private void updatePartStocks(WorkOrderResult result, WorkOrderStatus status) {
		if ((result.getDtoEntity().getWorkOrderStatus() == WorkOrderStatus.OPEN) && (status == WorkOrderStatus.APPROVED) && (result.getDomainEntity().getWorkOrderParts() != null)) {
			WorkOrder workOrder = workOrderDao.findOne(result.getDomainEntity().getId());
			for (WorkOrderPart workOrderPart : workOrder.getWorkOrderParts()) {
				Stock stock = workOrderPart.getStock();
				stock.setCurrentQuantity( stock.getCurrentQuantity().subtract(BigDecimal.valueOf(workOrderPart.getActualQuantity())));
				stockDao.save(stock);
			}
		}
	}

	@Override
	public Integer findAllOpenWorkOderCount() throws Exception {
	return (int) workOrderDao.count(getAllOpenWorkOder());
	}
	private Specification<WorkOrder> getAllOpenWorkOder(){
		Specification<WorkOrder> specification;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> {
				return getOpenWorkOrderPredicate(root, cb);
			};
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
				Predicate predicate2 = getOpenWorkOrderPredicate(root, cb);
				return cb.and(predicate, predicate2);
			};
		} else {
			specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
				Predicate predicate2 = getOpenWorkOrderPredicate( root, cb);
				return cb.and(predicate, predicate2);
			};
			
		}
		return specification;
		
	}
	
	private Predicate getOpenWorkOrderPredicate(Root<WorkOrder> root, CriteriaBuilder cb) {
		return cb.equal(root.get("currentStatus").get("workOrderStatus"), WorkOrderStatus.OPEN);
	}

	public Integer findAllHighPriorityWorkOderCount() throws Exception {
		 return (int) workOrderDao.count(getAllHighPriorityWorkOder());
	}
	
	private Specification<WorkOrder> getAllHighPriorityWorkOder(){
		Specification<WorkOrder> specification;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> {
				return getHighPriorityWorkOrderPredicate(root, cb);
			};
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
				Predicate predicate2 = getHighPriorityWorkOrderPredicate(root, cb);
				return cb.and(predicate, predicate2);
			};
		} else {
			specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
				Predicate predicate2 = getHighPriorityWorkOrderPredicate( root, cb);
				return cb.and(predicate, predicate2);
			};
			
		}
		return specification;
		
	}
	
	private Predicate getHighPriorityWorkOrderPredicate(Root<WorkOrder> root, CriteriaBuilder cb) {
		return cb.equal(root.get("priority").get("name"), "Highest");
	}

}
