package com.codex.ecam.mappers.maintenance.workorder;

import java.util.List;
import java.util.Optional;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderFileDTO;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderPartDTO;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderTaskDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.mappers.asset.AssetMapper;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.model.maintenance.workorder.WorkOrderAsset;
import com.codex.ecam.model.maintenance.workorder.WorkOrderFile;
import com.codex.ecam.model.maintenance.workorder.WorkOrderMeterReading;
import com.codex.ecam.model.maintenance.workorder.WorkOrderPart;
import com.codex.ecam.model.maintenance.workorder.WorkOrderTask;

public class WorkOrderMapper extends GenericMapper<WorkOrder, WorkOrderDTO> {

	private static WorkOrderMapper instance = null;

	private WorkOrderMapper() {
	}

	public static WorkOrderMapper getInstance() {
		if (instance == null) {
			instance = new WorkOrderMapper();
		}
		return instance;
	}

	@Override
	public WorkOrderDTO domainToDto(WorkOrder domain) throws Exception {
		WorkOrderDTO dto = new WorkOrderDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setRCAActionId(domain.getRCAActionId());
		dto.setRCACauseId(domain.getRCACauseId());
		dto.setRCAProblemId(domain.getRCAProblemId());
		dto.setDescription(domain.getDescription());
		dto.setInstruction(domain.getInstruction());
		dto.setEmailUserGuest(domain.getEmailUserGuest());
		dto.setNameUserGuest(domain.getNameUserGuest());
		dto.setPhoneUserGuest(domain.getPhoneUserGuest());
		dto.setAdminNotes(domain.getAdminNotes());
		dto.setCompletionNotes(domain.getCompletionNotes());
		dto.setProblem(domain.getProblem());
		dto.setRootCause(domain.getRootCause());
		dto.setSolution(domain.getSolution());
		dto.setForDate(domain.getForDate());
		dto.setStartDate(domain.getStartDate());
		dto.setDateCompleted(domain.getDateCompleted());
		dto.setSuggestedCompletionDate(domain.getSuggestedCompletionDate());
		dto.setAssetProductionTime(domain.getAssetProductionTime());
		dto.setSuggestedTime(domain.getSuggestedTime());
		dto.setTimeEstimatedHours(domain.getTimeEstimatedHours());
		dto.setTimeSpentHours(domain.getTimeSpentHours());
		dto.setTotalMaintHoursOffline(domain.getTotalMaintHoursOffline());
		dto.setTotalMaintHoursOnline(domain.getTotalMaintHoursOnline());
		dto.setDWOENotificationsSent(domain.getDWOENotificationsSent());
		dto.setWorkOrderStatusGroup(domain.getWorkOrderStatusGroup());

		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
		}

		if (domain.getSite() != null) {
			dto.setSiteId(domain.getSite().getId());
		}

		if (domain.getPriority() != null) {
			dto.setPriorityId(domain.getPriority().getId());
		}

		if (domain.getMaintenanceType() != null) {
			dto.setMaintenanceTypeId(domain.getMaintenanceType().getId());
		}

		if (domain.getAccount() != null) {
			dto.setAccountId(domain.getAccount().getId());
		}

		if (domain.getChargeDepartment() != null) {
			dto.setChargeDepartmentId(domain.getChargeDepartment().getId());
		}

		if (domain.getCompletedByUser() != null) {
			dto.setCompletedByUserId(domain.getCompletedByUser().getId());
			dto.setCompletedByUserName(domain.getCompletedByUser().getFullName());
		}

		if (domain.getRequestedByUser() != null) {
			dto.setRequestedByUserId(domain.getRequestedByUser().getId());
			dto.setRequestedByUserName(domain.getRequestedByUser().getFullName());
		}

		if (domain.getProject() != null) {
			dto.setProjectId(domain.getProject().getId());
			dto.setProjectName(domain.getProject().getName());
		}

		if (domain.getScheduledMaintenance() != null) {
			dto.setScheduledMaintenanceId(domain.getScheduledMaintenance().getId());
		}
		
		if (domain.getCurrentStatus() != null) {
			dto.setWorkOrderStatus(domain.getCurrentStatus().getWorkOrderStatus()); 
			dto.setCurrentStatusId(domain.getCurrentStatus().getId());
		}
		
		setAssets(domain, dto);
		setMeterReadings(domain, dto);
		setMiscellaneousExpenses(domain, dto);
		setNotifications(domain, dto);
		setTasks(domain, dto);
		setWorkOrderFile(domain, dto);
		setNotes(domain, dto);  

		setCommanDTOFields(dto, domain);

		return dto;
	}

	private void setNotes(WorkOrder domain, WorkOrderDTO dto) throws Exception {
		if ((domain.getWorkOrderNotes() != null) && (domain.getWorkOrderNotes().size() > 0)) {
			dto.setWorkOrderNoteDTOs(WorkOrderNoteMapper.getInstance().domainToDTOList(domain.getWorkOrderNotes()));
		}
	}

	private void setTasks(WorkOrder domain, WorkOrderDTO dto) throws Exception {
		if ((domain.getWorkOrderTasks() != null) && (domain.getWorkOrderTasks().size() > 0)) {
			Integer index = 0;
			for ( WorkOrderTask task : domain.getWorkOrderTasks() ) {
				WorkOrderTaskDTO taskDto = WorkOrderTaskMapper.getInstance().domainToDto(task);
				taskDto.setIndex(index);
				dto.getTasks().add(taskDto);
				setParts(domain, dto, task);
				index++;
			}
		}
	}

	private void setParts(WorkOrder domain, WorkOrderDTO dto, WorkOrderTask task) throws Exception {
		if ((task.getWorkOrderParts() != null) && (task.getWorkOrderParts().size() > 0)) {
			for ( WorkOrderPart part : task.getWorkOrderParts() ) {
				WorkOrderPartDTO partDto = WorkOrderPartMapper.getInstance().domainToDto(part);
				partDto.setWoPartTaskIndex(getTaskIndex(part.getWorkOrderTask().getId(), dto.getTasks()));
				dto.getParts().add(partDto);
			}
		}
	}

	private void setWorkOrderFile(WorkOrder domain, WorkOrderDTO dto) throws Exception {

		if (domain.getWorkOrderFiles().size() > 0) {

			for (WorkOrderFile workOrderFile :domain.getWorkOrderFiles()) {

				WorkOrderFileDTO workOrderFileDTO = new WorkOrderFileDTO();
				workOrderFileDTO.setId(workOrderFile.getId());
				workOrderFileDTO.setItemDescription(workOrderFile.getItemDescription());
				workOrderFileDTO.setVersion(workOrderFile.getVersion());
				workOrderFileDTO.setFileDate(workOrderFile.getFileDate());
				workOrderFileDTO.setFileLocation(workOrderFile.getFileLocation());
				workOrderFileDTO.setFileType(workOrderFile.getFileType());

				dto.getFiles().add(workOrderFileDTO);
			}
		}
	}

	private Integer getTaskIndex(Integer id, List<WorkOrderTaskDTO> woTasks) {
		Optional<WorkOrderTaskDTO> optionalTask = woTasks.stream().filter((x) -> x.getId() == id).findAny();
		if ( optionalTask.isPresent() ) {
			return optionalTask.get().getIndex();
		}
		return null;
	}

	private void setNotifications(WorkOrder domain, WorkOrderDTO dto) throws Exception {
		if ((domain.getWorkOrderNotifications() != null) && (domain.getWorkOrderNotifications().size() > 0)) {
			dto.setNotifications(WorkOrderNotificationMapper.getInstance().domainToDTOList(domain.getWorkOrderNotifications()));
		}
	}

	private void setMiscellaneousExpenses(WorkOrder domain, WorkOrderDTO dto) throws Exception {
		if ((domain.getWorkOrderMiscellaneousExpenses() != null) && (domain.getWorkOrderMiscellaneousExpenses().size() > 0)) {
			dto.setMiscellaneousExpenses(WorkOrderMiscellaneousExpenseMapper.getInstance().domainToDTOList(domain.getWorkOrderMiscellaneousExpenses()));
		}
	}

	private void setAssets(WorkOrder domain, WorkOrderDTO dto) throws Exception {
		if (domain.getWorkOrderAssets().size() > 0) {
			StringBuilder nameStr = new StringBuilder();
			for (WorkOrderAsset woAsset : domain.getWorkOrderAssets()) {
				dto.getAssets().add(AssetMapper.getInstance().domainToDto(woAsset.getAsset()));
				if (nameStr.length() > 0) {
					nameStr.append("," + woAsset.getAsset().getName());
				} else {
					nameStr.append(woAsset.getAsset().getName());
				}
			}

			dto.setAssetNameStr(nameStr.toString());
		}
	}

	private void setAssetStr(WorkOrder domain, WorkOrderDTO dto) throws Exception {
		if (domain.getWorkOrderAssets().size() > 0) {
			StringBuilder nameStr = new StringBuilder();
			for (WorkOrderAsset woAsset : domain.getWorkOrderAssets()) {
				if (nameStr.length() > 0) {
					nameStr.append("," + woAsset.getAsset().getName());
				} else {
					nameStr.append(woAsset.getAsset().getName());
				}
			}

			dto.setAssetNameStr(nameStr.toString());
		}
	}

	private void setMeterReadings(WorkOrder domain, WorkOrderDTO dto) throws Exception {
		if (domain.getWorkOrderMeterReadings().size() > 0) {
			for (WorkOrderMeterReading workOrderMeterReading : domain.getWorkOrderMeterReadings()) {
				dto.getMeterReadings().add(WorkOrderMeterReadingMapper.getInstance().domainToDto(workOrderMeterReading));
			}
		}
	}

	@Override
	public void dtoToDomain(WorkOrderDTO dto, WorkOrder domain) throws Exception {
		domain.setId(dto.getId());
		domain.setCode(dto.getCode());
		domain.setWorkOrderRequestId(dto.getWorkOrderRequestId()); 
		domain.setRCAActionId(dto.getRCAActionId());
		domain.setRCACauseId(dto.getRCACauseId());
		domain.setRCAProblemId(dto.getRCAProblemId());
		domain.setDescription(dto.getDescription());
		domain.setInstruction(dto.getInstruction());
		domain.setEmailUserGuest(dto.getEmailUserGuest());
		domain.setNameUserGuest(dto.getNameUserGuest());
		domain.setPhoneUserGuest(dto.getPhoneUserGuest());
		domain.setAdminNotes(dto.getAdminNotes());
		domain.setCompletionNotes(dto.getCompletionNotes());
		domain.setProblem(dto.getProblem());
		domain.setRootCause(dto.getRootCause());
		domain.setSolution(dto.getSolution());
		domain.setForDate(dto.getForDate());
		domain.setStartDate(dto.getStartDate());
		domain.setDateCompleted(dto.getDateCompleted());
		domain.setSuggestedCompletionDate(dto.getSuggestedCompletionDate());
		domain.setAssetProductionTime(dto.getAssetProductionTime());
		domain.setSuggestedTime(dto.getSuggestedTime());
		domain.setTimeEstimatedHours(dto.getTimeEstimatedHours());
		domain.setTimeSpentHours(dto.getTimeSpentHours());
		domain.setTotalMaintHoursOffline(dto.getTotalMaintHoursOffline());
		domain.setTotalMaintHoursOnline(dto.getTotalMaintHoursOnline());
		domain.setDWOENotificationsSent(dto.getDWOENotificationsSent());
		domain.setWorkOrderStatusGroup(dto.getWorkOrderStatusGroup());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public WorkOrderDTO domainToDtoForDataTable(WorkOrder domain) throws Exception {
		WorkOrderDTO dto = new WorkOrderDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());
		dto.setStartDate(domain.getStartDate());
		setAssetStr(domain, dto);
		if (domain.getRequestedByUser() != null) {
			dto.setRequestedByUserName(domain.getRequestedByUser().getFullName());
		}
		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;
	}

}
