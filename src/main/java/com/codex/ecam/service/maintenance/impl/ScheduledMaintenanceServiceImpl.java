package com.codex.ecam.service.maintenance.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.SMMeterReadingType;
import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.dao.admin.AccountDao;
import com.codex.ecam.dao.admin.ChargeDepartmentDao;
import com.codex.ecam.dao.admin.MaintenanceTypeDao;
import com.codex.ecam.dao.admin.PriorityDao;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.asset.AssetEventTypeAssetDao;
import com.codex.ecam.dao.asset.AssetMeterReadingDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.inventory.StockDao;
import com.codex.ecam.dao.maintenance.ProjectDao;
import com.codex.ecam.dao.maintenance.ScheduledMaintenanceDao;
import com.codex.ecam.dao.maintenance.TaskGroupDao;
import com.codex.ecam.dao.maintenance.WorkOrderDao;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceDTO;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceFileDTO;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceNotificationDTO;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenancePartDTO;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTaskDTO;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTriggerDTO;
import com.codex.ecam.mappers.maintenance.schedulemaintenance.ScheduledMaintenanceFileMapper;
import com.codex.ecam.mappers.maintenance.schedulemaintenance.ScheduledMaintenanceMapper;
import com.codex.ecam.mappers.maintenance.schedulemaintenance.ScheduledMaintenanceNotificationMapper;
import com.codex.ecam.mappers.maintenance.schedulemaintenance.ScheduledMaintenancePartMapper;
import com.codex.ecam.mappers.maintenance.schedulemaintenance.ScheduledMaintenanceTaskMapper;
import com.codex.ecam.mappers.maintenance.schedulemaintenance.ScheduledMaintenanceTriggerMapper;
import com.codex.ecam.model.asset.AssetMeterReading;
import com.codex.ecam.model.asset.AssetMeterReadingValue;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.maintenance.CalendarEvent;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenance;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceAsset;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceFile;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceNotification;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenancePart;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTask;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.RestResult;
import com.codex.ecam.result.maintenance.ScheduledMaintenanceResult;
import com.codex.ecam.service.maintenance.api.ScheduledMaintenanceService;
import com.codex.ecam.service.maintenance.api.ScheduledMaintenanceTriggerService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.FileDownloadUtil;
import com.codex.ecam.util.FileUploadUtil;
import com.codex.ecam.util.SchedulingUtil;
import com.codex.ecam.util.search.scheduledmaintenance.ScheduledMaintenanceSearchPropertyMapper; 

@Service
public class ScheduledMaintenanceServiceImpl implements ScheduledMaintenanceService {

	private final static Logger logger = LoggerFactory.getLogger(ScheduledMaintenanceServiceImpl.class);

	@Autowired
	private ScheduledMaintenanceDao scheduledMaintenanceDao;

	@Autowired
	private ScheduledMaintenanceTriggerService scheduledMaintenanceTriggerService;

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
	private AssetEventTypeAssetDao assetEventTypeAssetDao;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AssetMeterReadingDao assetMeterReadingDao;

	@Autowired
	private TaskGroupDao taskGroupDao;

	@Autowired
	private StockDao stockDao;
	@Autowired
	private WorkOrderDao workORderDao;
	

	@Autowired
	Environment environment;

	@Override
	public ScheduledMaintenanceDTO findById(Integer id) throws Exception {
		ScheduledMaintenance domain = scheduledMaintenanceDao.findById(id);
		if (domain != null) {
			return ScheduledMaintenanceMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public ScheduledMaintenanceResult save(ScheduledMaintenanceDTO dto) {
		ScheduledMaintenanceResult result = createScheduledMaintenanceResult(dto);
		try {
			saveOrUpdate(result);
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Scheduled Maintenance Already updated. Please Reload ScheduledMaintenance.");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	private ScheduledMaintenanceResult createScheduledMaintenanceResult(ScheduledMaintenanceDTO dto) {
		ScheduledMaintenanceResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new ScheduledMaintenanceResult(scheduledMaintenanceDao.findOne(dto.getId()), dto);
		} else {
			result = new ScheduledMaintenanceResult(new ScheduledMaintenance(), dto);
		}

		return result;
	}

	private String getMessageByAction(ScheduledMaintenanceResult result) {
		if (result.getDtoEntity().getId() == null) {
			return "ScheduledMaintenance Added Successfully.";
		} else {
			return "ScheduledMaintenance Updated Successfully.";
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(ScheduledMaintenanceResult result) throws Exception {
		ScheduledMaintenanceMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setScheduledMaintenanceData(result);
		addSchedules(result);
		scheduledMaintenanceDao.save(result.getDomainEntity());
		result.addToMessageList(getMessageByAction(result));
		result.updateDtoIdAndVersion();
	}

	private void addSchedules(ScheduledMaintenanceResult result) throws Exception {

		ScheduledMaintenance sm = result.getDomainEntity();

		if (sm.getIsRunning() && (sm.getScheduledMaintenanceTriggers() != null) && (sm.getScheduledMaintenanceTriggers().size() > 0)) {

			for (ScheduledMaintenanceTrigger smt : sm.getScheduledMaintenanceTriggers()) {
				if (smt.getTriggerType().equals(SMTriggerType.TIME_TRIGGER) && isPropertyChange(smt)) {
					setCalenderEvent(smt);
					if ((smt.getTtCreateWOOnStartDate() != null) && smt.getTtCreateWOOnStartDate()) {
						WorkOrder wo = scheduledMaintenanceTriggerService.createWorkOrderFromTrigger(smt);
						result.addToMessageList("Work Order Created. WO Code : " + wo.getCode());
						workORderDao.save(wo);
						Set<WorkOrder> list = new HashSet<>(1);
						list.add(wo);
						sm.setWorkOrders(list);
					}
				}
			}
		}
	}

	private void setCalenderEvent(ScheduledMaintenanceTrigger smt) {
		CalendarEvent event = SchedulingUtil.getNextCalenderEvent(smt);

		List<CalendarEvent> events = new ArrayList<>();
		events.add(event);

		smt.setCalendarEvents(events);

		smt.setTtNextCalenderEvent(event);
	}

	private boolean isPropertyChange(ScheduledMaintenanceTrigger trigger) {
		if ( (trigger.getId() == null) || trigger.getIsPropertyChange() ) {
			return true;
		}
		return false;
	}

	private void setScheduledMaintenanceData(ScheduledMaintenanceResult result) throws Exception {
		setAssetToScheduledMaintenance(result);
		setBusiness(result);
		setSite(result);
		setAccount(result);
		setChargeDepartment(result);
		setMaintenanceType(result);
		setPriority(result);
		setProject(result);
		setRequestor(result);
		setTriggers(result);
		setScheduledMaintenanceNotification(result);
		setScheduledMaintenanceFiles(result);
	}

	private void setTriggers(ScheduledMaintenanceResult result) throws Exception {

		Set<ScheduledMaintenanceTrigger> list = new HashSet<>();

		if ((result.getDtoEntity().getTriggers() != null) && (result.getDtoEntity().getTriggers().size() > 0)) {

			Set<ScheduledMaintenanceTrigger> currentTriggers = result.getDomainEntity().getScheduledMaintenanceTriggers();

			ScheduledMaintenanceTrigger trigger;

			for (ScheduledMaintenanceTriggerDTO dto : result.getDtoEntity().getTriggers()) {

				if ((currentTriggers != null) && (currentTriggers.size() > 0)) {
					trigger = currentTriggers.stream().filter((x) -> x.getId().equals(dto.getId())).findAny().orElseGet(ScheduledMaintenanceTrigger :: new);
				} else {
					trigger = new ScheduledMaintenanceTrigger();
				}

				createScheduleMaintenaceTrigger(result.getDomainEntity(), trigger, dto);
				setScheduledMaintenanceTask(result, trigger);
				list.add(trigger);
			}
		}
		result.getDomainEntity().setScheduledMaintenanceTriggers(list);
	}

	private ScheduledMaintenanceTrigger createScheduleMaintenaceTrigger(ScheduledMaintenance scheduledMaintenance, ScheduledMaintenanceTrigger domain, ScheduledMaintenanceTriggerDTO dto) throws Exception {
		ScheduledMaintenanceTriggerMapper.getInstance().dtoToDomain(dto, domain);
		domain.setScheduledMaintenance(scheduledMaintenance);
		setAsset(dto, domain);
		setAssetEventTypeAsset(dto, domain);
		setAssetMeterReading(dto, domain);

		return domain;
	}

	private void setScheduledMaintenanceTask(ScheduledMaintenanceResult result, ScheduledMaintenanceTrigger trigger) throws Exception {

		Set<ScheduledMaintenanceTask> taskList = new HashSet<>();

		if ((result.getDtoEntity().getScheduledTasks() != null) && (result.getDtoEntity().getScheduledTasks().size() > 0)) {

			for (ScheduledMaintenanceTaskDTO scheduledMaintenanceTaskDTO : result.getDtoEntity().getScheduledTasks()) {

				if ( scheduledMaintenanceTaskDTO.getTriggerIndex().equals(trigger.getTriggerIndex()) ) {

					ScheduledMaintenanceTask scheduledMaintenanceTask = createScheduledTask(result, scheduledMaintenanceTaskDTO, trigger);
					setPartToScheduledMaintenanceTask(result, scheduledMaintenanceTaskDTO, scheduledMaintenanceTask);
					taskList.add(scheduledMaintenanceTask);

				}
			}
		}

		trigger.setScheduledMaintenanceTasks(taskList);
	}

	private ScheduledMaintenanceTask createScheduledTask(ScheduledMaintenanceResult result, ScheduledMaintenanceTaskDTO scheduledMaintenanceTaskDTO, ScheduledMaintenanceTrigger trigger) throws Exception {

		ScheduledMaintenanceTask scheduledMaintenanceTask;

		if ((trigger.getScheduledMaintenanceTasks() != null) && (trigger.getScheduledMaintenanceTasks().size() > 0) && (scheduledMaintenanceTaskDTO.getId() != null)) {
			scheduledMaintenanceTask = trigger.getScheduledMaintenanceTasks().stream().filter((x) -> x.getId().equals(scheduledMaintenanceTaskDTO.getId())).findAny().orElseGet(ScheduledMaintenanceTask :: new);
		} else {
			scheduledMaintenanceTask = new ScheduledMaintenanceTask();
		}

		ScheduledMaintenanceTaskMapper.getInstance().dtoToDomain(scheduledMaintenanceTaskDTO, scheduledMaintenanceTask);
		scheduledMaintenanceTask.setScheduledMaintenanceTrigger(trigger);
		setScheduledTaskAssignedUser(scheduledMaintenanceTaskDTO, scheduledMaintenanceTask);
		setScheduledTaskTaskGroup(scheduledMaintenanceTaskDTO, scheduledMaintenanceTask);

		return scheduledMaintenanceTask;
	}

	private void setPartToScheduledMaintenanceTask(ScheduledMaintenanceResult result, ScheduledMaintenanceTaskDTO smTaskDto, ScheduledMaintenanceTask smTask) throws Exception {
		Set<ScheduledMaintenancePart> parts = new HashSet<>();

		if ((result.getDtoEntity().getParts() != null) && (result.getDtoEntity().getParts().size() > 0)) {

			Set<ScheduledMaintenancePart> currentParts = smTask.getScheduledMaintenanceParts();

			for (ScheduledMaintenancePartDTO partDTO : result.getDtoEntity().getParts()) {

				if ( partDTO.getPartTaskIndex().equals(smTaskDto.getIndex()) ) {
					ScheduledMaintenancePart part;
					if ((currentParts != null) && (currentParts.size() > 0)) {
						part = currentParts.stream().filter((x) -> x.getStock().getId().equals(partDTO.getPartStockId())).findAny().orElseGet(ScheduledMaintenancePart :: new);
					} else {
						part = new ScheduledMaintenancePart();
					}
					createScheduledMaintenanceTaskPart(partDTO, part, result.getDomainEntity(), smTask);
					parts.add(part);
				}
			}
		}

		smTask.setScheduledMaintenanceParts(parts);
	}

	private void createScheduledMaintenanceTaskPart(ScheduledMaintenancePartDTO dto, ScheduledMaintenancePart domain , ScheduledMaintenance scheduledMaintenance, ScheduledMaintenanceTask smTask) throws Exception {
		ScheduledMaintenancePartMapper.getInstance().dtoToDomain(dto, domain);
		domain.setScheduledMaintenanceTask(smTask);
		domain.setIsDeleted(false);

		if ((dto.getPartPartId() != null) && (dto.getPartPartId() > 0)) {
			domain.setPart(assetDao.findOne(dto.getPartPartId()));
		}

		if ((dto.getPartStockId() != null) && (dto.getPartStockId() > 0)) {
			domain.setStock(stockDao.findOne(dto.getPartStockId()));
		}
	}

	private void setScheduledMaintenanceNotification(ScheduledMaintenanceResult result) throws Exception {
		Set<ScheduledMaintenanceNotification> smNotifications = new HashSet<>();
		List<ScheduledMaintenanceNotificationDTO> smNotificationDTOs = result.getDtoEntity().getNotifications();

		if ((smNotificationDTOs != null) && (smNotificationDTOs.size() > 0)) {

			Set<ScheduledMaintenanceNotification> currentSmNotifications = result.getDomainEntity().getScheduledMaintenanceNotifications();
			ScheduledMaintenanceNotification smNotification;

			for (ScheduledMaintenanceNotificationDTO smNotificationDto : smNotificationDTOs) {

				smNotification = new ScheduledMaintenanceNotification();
				if ((currentSmNotifications != null) && (currentSmNotifications.size() > 0)) {
					smNotification = currentSmNotifications.stream().filter((x) -> x.getId().equals(smNotificationDto.getId())).findAny().orElseGet(ScheduledMaintenanceNotification :: new);
				} else {
					smNotification = new ScheduledMaintenanceNotification();
				}
				createNotification(smNotification, smNotificationDto, result.getDomainEntity());
				smNotifications.add(smNotification);
			}
		}

		result.getDomainEntity().setScheduledMaintenanceNotifications(smNotifications);
	}

	private void createNotification(ScheduledMaintenanceNotification domain, ScheduledMaintenanceNotificationDTO dto, ScheduledMaintenance scheduledMaintenance) throws Exception {
		ScheduledMaintenanceNotificationMapper.getInstance().dtoToDomain(dto, domain);
		domain.setUser(dto.getUserId() != null ? userDao.findOne(dto.getUserId()) : null);
		domain.setScheduledMaintenance(scheduledMaintenance);
	}

	private void setScheduledTaskTaskGroup(ScheduledMaintenanceTaskDTO scheduledMaintenanceTaskDTO, ScheduledMaintenanceTask scheduledMaintenanceTask) {
		if (scheduledMaintenanceTaskDTO.getTaskGroupId() != null) {
			scheduledMaintenanceTask.setTaskGroup(taskGroupDao.findOne(scheduledMaintenanceTaskDTO.getTaskGroupId()));
		}
	}

	private void setScheduledTaskAssignedUser(ScheduledMaintenanceTaskDTO scheduledMaintenanceTaskDTO, ScheduledMaintenanceTask scheduledMaintenanceTask) {
		if (scheduledMaintenanceTaskDTO.getUserId() != null) {
			scheduledMaintenanceTask.setAssignedUser(userDao.findOne(scheduledMaintenanceTaskDTO.getUserId()));
		}
	}

	private void setAssetToScheduledMaintenance(ScheduledMaintenanceResult result) {

		Set<ScheduledMaintenanceAsset> list = new HashSet<ScheduledMaintenanceAsset>();

		if ((result.getDtoEntity().getAssets() != null) && (result.getDtoEntity().getAssets().size() > 0)) {
			Set<ScheduledMaintenanceAsset> currentAssets = result.getDomainEntity().getScheduledMaintenanceAssets();

			ScheduledMaintenanceAsset smAsset;
			for (AssetDTO asset : result.getDtoEntity().getAssets()) {
				if ((currentAssets != null) && (currentAssets.size() > 0)) {
					smAsset = currentAssets.stream().filter((x) -> x.getId().equals(asset.getId())).findAny().orElseGet(ScheduledMaintenanceAsset :: new);
				} else {
					smAsset = new ScheduledMaintenanceAsset();
				}
				createScheduledMaintenaceAsset(result.getDomainEntity(), asset, smAsset);
				list.add(smAsset);
			}
		}
		result.getDomainEntity().setScheduledMaintenanceAssets(list);
	}


	private void createScheduledMaintenaceAsset(ScheduledMaintenance scheduledMaintenance, AssetDTO asset, ScheduledMaintenanceAsset smAsset) {
		smAsset.setAsset(assetDao.findOne(asset.getId()));
		smAsset.setScheduledMaintenance(scheduledMaintenance);
		smAsset.setIsDeleted(false);
	}

	private void setAssetMeterReading(ScheduledMaintenanceTriggerDTO dto, ScheduledMaintenanceTrigger domain) {
		if ((dto.getMrtAssetMeterReadingId() != null) && (dto.getMrtAssetMeterReadingId() > 0)) {
			AssetMeterReading assetMeterReading=assetMeterReadingDao.findOne(dto.getMrtAssetMeterReadingId());

			domain.setMrtAssetMeterReading(assetMeterReading);
			Double mrtNextMeterReading=0.0;
			if(domain.getTriggerType().equals(SMTriggerType.METER_READING_TRIGGER)){
				if(domain.getMrtType().equals(SMMeterReadingType.EVERY)){
					mrtNextMeterReading=dto.getMrtStartMeterReading()+dto.getMrtEveryValue();
				}else if(domain.getMrtType().equals(SMMeterReadingType.WHEN)){
					mrtNextMeterReading=dto.getMrtConditionValue();
				}
			}

			domain.setMrtNextMeterReading(mrtNextMeterReading);
			setMeterReadingData(dto,domain);
		}
	}
	
	private void setMeterReadingData(ScheduledMaintenanceTriggerDTO dto,ScheduledMaintenanceTrigger domain){
		domain.setMrtLogicType(dto.getMrtLogicType());
		domain.setMrtType(dto.getMrtType());
		domain.setMrtEndMeterReading(dto.getMrtEndMeterReading());
		domain.setMrtStartMeterReading(dto.getMrtStartMeterReading());

	}

	private void setAssetEventTypeAsset(ScheduledMaintenanceTriggerDTO dto, ScheduledMaintenanceTrigger domain) {
		if ((dto.getEtAssetEventTypeAssetId() != null) && (dto.getEtAssetEventTypeAssetId() > 0)) {
			domain.setEtAssetEventTypeAsset(assetEventTypeAssetDao.findOne(dto.getEtAssetEventTypeAssetId()));
		}
	}

	private void setAsset(ScheduledMaintenanceTriggerDTO dto, ScheduledMaintenanceTrigger domain) {
		if ((dto.getAssetId() != null) && (dto.getAssetId() > 0)) {
			domain.setAsset(assetDao.findOne(dto.getAssetId()));
		}
	}

	private void setRequestor(ScheduledMaintenanceResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getRequestorId() != null)) {
			result.getDomainEntity().setRequestor(userDao.findOne(result.getDtoEntity().getRequestorId()));
		}
	}

	private void setBusiness(ScheduledMaintenanceResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setAccount(ScheduledMaintenanceResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getAccountId() != null)) {
			result.getDomainEntity().setAccount(accountDao.findOne(result.getDtoEntity().getAccountId()));
		}
	}

	private void setChargeDepartment(ScheduledMaintenanceResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getChargeDepartmentId() != null)) {
			result.getDomainEntity().setChargeDepartment(chargeDepartmentDao.findOne(result.getDtoEntity().getChargeDepartmentId()));
		}
	}

	private void setMaintenanceType(ScheduledMaintenanceResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getMaintenanceTypeId() != null)) {
			result.getDomainEntity().setMaintenanceType(maintenanceTypeDao.findOne(result.getDtoEntity().getMaintenanceTypeId()));
		}
	}

	private void setPriority(ScheduledMaintenanceResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getPriorityId() != null)) {
			result.getDomainEntity().setPriority(priorityDao.findOne(result.getDtoEntity().getPriorityId()));
		}
	}

	private void setSite(ScheduledMaintenanceResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getSiteId() != null)) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		}
	}

	private void setProject(ScheduledMaintenanceResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getProjectId() != null)) {
			result.getDomainEntity().setProject(projectDao.findOne(result.getDtoEntity().getProjectId()));
		}
	}

	@Override
	public ScheduledMaintenanceResult delete(Integer id) {
		ScheduledMaintenanceResult result = new ScheduledMaintenanceResult(null, null);
		try {
			scheduledMaintenanceDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("ScheduledMaintenance Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("ScheduledMaintenance Already Used. Cannot delete.");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Override
	public DataTablesOutput<ScheduledMaintenanceDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<ScheduledMaintenance> domainOut;
		ScheduledMaintenanceSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = scheduledMaintenanceDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<ScheduledMaintenance> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = scheduledMaintenanceDao.findAll(input, specification);
		} else {
			Specification<ScheduledMaintenance> specification = (root, query, cb) -> cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
			domainOut = scheduledMaintenanceDao.findAll(input, specification);
		}
		DataTablesOutput<ScheduledMaintenanceDTO> out = ScheduledMaintenanceMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<ScheduledMaintenanceDTO> findAllByProjectId(FocusDataTablesInput input, Integer id) throws Exception {
		Specification<ScheduledMaintenance> specification = (root, query, cb) -> cb.equal(root.get("project").get("id"), id);
		DataTablesOutput<ScheduledMaintenance> domainOut = scheduledMaintenanceDao.findAll(input, specification);
		DataTablesOutput<ScheduledMaintenanceDTO> out = ScheduledMaintenanceMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public void scheduledMaintenanceFileDownload(Integer refId,HttpServletResponse response) throws Exception {
		String uploadLocation = new File(environment.getProperty("upload.location")).getPath();
		if ( refId != null ) {
			ScheduledMaintenanceFile file = scheduledMaintenanceDao.findByFileId(refId);
			String externalFilePath = uploadLocation + file.getFileLocation();
			FileDownloadUtil.flushFile(externalFilePath, file.getFileType(), response);
		}
	}

	@Override
	public String scheduledMaintenanceFileUpload(MultipartFile file,String refId) throws Exception {
		String uploadFolder = environment.getProperty("upload.scheduledMaintenance.file.folder");
		String uploadLocation = environment.getProperty("upload.location");
		try {
			return FileUploadUtil.createFile(file,refId,uploadFolder,uploadLocation);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void setScheduledMaintenanceFiles(ScheduledMaintenanceResult result) throws Exception {
		Set<ScheduledMaintenanceFile> scheduledMaintenanceFiles = new HashSet<>();

		if ( (result.getDtoEntity().getFiles() != null) && (result.getDtoEntity().getFiles().size() > 0) ) {

			Set<ScheduledMaintenanceFile> currentScheduledMaintenanceFiles = result.getDomainEntity().getScheduledMaintenanceFiles();

			for (ScheduledMaintenanceFileDTO scheduledMaintenanceFileDTO : result.getDtoEntity().getFiles()) {

				ScheduledMaintenanceFile scheduledMaintenanceFile;

				if ( scheduledMaintenanceFileDTO.getId() != null ) {
					scheduledMaintenanceFile = currentScheduledMaintenanceFiles.stream().filter((x) -> x.getId().equals(scheduledMaintenanceFileDTO.getId())).findAny().orElseGet(ScheduledMaintenanceFile :: new);
				} else {
					scheduledMaintenanceFile = new ScheduledMaintenanceFile();
				}

				ScheduledMaintenanceFileMapper.getInstance().dtoToDomain(scheduledMaintenanceFileDTO, scheduledMaintenanceFile);
				scheduledMaintenanceFile.setScheduledMaintenance(result.getDomainEntity());

				scheduledMaintenanceFiles.add(scheduledMaintenanceFile);
			}

		}

		result.getDomainEntity().setScheduledMaintenanceFiles(scheduledMaintenanceFiles);
	}

	@Override
	public RestResult<String> findCurrentScheduledMaintenanceCode(Integer businessId) {
		RestResult<String> result = new RestResult<>();
		try {
			if ( (businessId != null) && (businessId > 0) ) {

				Business business = businessDao.findById(businessId);

				List<ScheduledMaintenance> lastInsertSm = scheduledMaintenanceDao.findLastInsertSmByBusiness(businessId);

				StringBuilder code = new StringBuilder("SM/" + String.format("%03d", business.getId()));

				if ((lastInsertSm != null) && (lastInsertSm.size() > 0)) {
					String str[] = lastInsertSm.get(0).getCode().split("/");
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

}
