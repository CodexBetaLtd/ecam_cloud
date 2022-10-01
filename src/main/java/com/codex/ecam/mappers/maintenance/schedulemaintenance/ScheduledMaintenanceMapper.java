package com.codex.ecam.mappers.maintenance.schedulemaintenance;

import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceDTO;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceFileDTO;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenancePartDTO;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTaskDTO;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTriggerDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.mappers.asset.AssetMapper;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenance;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceAsset;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceFile;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenancePart;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTask;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;

public class ScheduledMaintenanceMapper extends GenericMapper<ScheduledMaintenance, ScheduledMaintenanceDTO> {

	private static ScheduledMaintenanceMapper instance = null;

	private ScheduledMaintenanceMapper() {
	}

	public static ScheduledMaintenanceMapper getInstance() {
		if (instance == null) {
			instance = new ScheduledMaintenanceMapper();
		}
		return instance;
	}

	@Override
	public ScheduledMaintenanceDTO domainToDto(ScheduledMaintenance domain) throws Exception {
		final ScheduledMaintenanceDTO dto = new ScheduledMaintenanceDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());
		dto.setAdminNotes(domain.getAdminNotes());
		dto.setCompletionNotes(domain.getCompletionNotes());
		dto.setrType(domain.getrType());
		dto.setScheduleDescription(domain.getScheduleDescription());
		dto.setNotifyCreatorWhenDWOE(domain.getNotifyCreatorWhenDWOE());
		dto.setNotifyTechniciansWhenDWOE(domain.getNotifyTechniciansWhenDWOE());
		dto.setSuggestedCompletion(domain.getSuggestedCompletion());
		dto.setSuggestedTime(domain.getSuggestedTime());
		dto.setTimeEstimatedHours(domain.getTimeEstimatedHours());
		dto.setIsRunning(domain.getIsRunning());
		dto.setWorkOrderStatus(domain.getStartAsWoStatus());

		setBusiness(domain, dto);
		setSite(domain, dto);
		setPriority(domain, dto);
		setMaintenanceType(domain, dto);
		setAccount(domain, dto);
		setChargeDepartment(domain, dto);
		setAssignedUser(domain, dto);
		setProject(domain, dto);
		setNotifications(domain, dto);
		setAssets(domain, dto);
		setTriggers(domain, dto);
		setScheduledMaintenanceFile(domain, dto); // this should be done after setting trigger indexes

		setCommanDTOFields(dto, domain);

		return dto;
	}

	private void setBusiness(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) {
		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
		}
	}

	private void setSite(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) {
		if (domain.getSite() != null) {
			dto.setSiteId(domain.getSite().getId());
		}
	}

	private void setPriority(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) {
		if (domain.getPriority() != null) {
			dto.setPriorityId(domain.getPriority().getId());
			dto.setPriorityName(domain.getPriority().getName());
		}
	}

	private void setMaintenanceType(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) {
		if (domain.getMaintenanceType() != null) {
			dto.setMaintenanceTypeId(domain.getMaintenanceType().getId());
			dto.setMaintenanceTypeName(domain.getMaintenanceType().getName());
		}
	}

	private void setAccount(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) {
		if (domain.getAccount() != null) {
			dto.setAccountId(domain.getAccount().getId());
			dto.setAccountName(domain.getAccount().getCode());
		}
	}

	private void setChargeDepartment(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) {
		if (domain.getChargeDepartment() != null) {
			dto.setChargeDepartmentId(domain.getChargeDepartment().getId());
			dto.setChargeDepartmentName(domain.getChargeDepartment().getCode());
		}
	}

	private void setAssignedUser(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) {
		if (domain.getRequestor() != null) {
			dto.setRequestorId(domain.getRequestor().getId());
			dto.setRequestorName(domain.getRequestor().getFullName());
		}
	}

	private void setProject(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) {
		if (domain.getProject() != null) {
			dto.setProjectId(domain.getProject().getId());
			dto.setProjectName(domain.getProject().getName());
		}
	}

	private void setNotifications(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) throws Exception {
		if (domain.getScheduledMaintenanceNotifications() != null && domain.getScheduledMaintenanceNotifications().size() > 0) {
			dto.setNotifications(ScheduledMaintenanceNotificationMapper.getInstance().domainToDTOList(domain.getScheduledMaintenanceNotifications()));
		}
	}

	private void setTriggers(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) throws Exception {
		if ( domain.getScheduledMaintenanceTriggers() != null && domain.getScheduledMaintenanceTriggers().size() > 0 ) {
			Integer index = 0;
			for ( final ScheduledMaintenanceTrigger trigger : domain.getScheduledMaintenanceTriggers()) {
				final ScheduledMaintenanceTriggerDTO triggetDto = ScheduledMaintenanceTriggerMapper.getInstance().domainToDto(trigger);
				triggetDto.setIndex(index);
				dto.getTriggers().add(triggetDto);
				setScheduledTasks(domain, dto, trigger, index);
				index++;
			}
		}
	}

	private void setScheduledTasks(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto, ScheduledMaintenanceTrigger trigger, Integer triggerIndex) throws Exception {

		if (trigger.getScheduledMaintenanceTasks() != null && trigger.getScheduledMaintenanceTasks().size() > 0) {
			Integer taskIndex = 0;
			for ( final ScheduledMaintenanceTask task : trigger.getScheduledMaintenanceTasks() ) {
				final ScheduledMaintenanceTaskDTO taskDto = ScheduledMaintenanceTaskMapper.getInstance().domainToDto(task);
				taskIndex = getTaskIndex(dto);
				taskDto.setTriggerIndex(triggerIndex);
				taskDto.setIndex(taskIndex);
				dto.getScheduledTasks().add(taskDto);
				setParts(domain, dto, task, taskIndex);

			}
		}
	}

	private void setParts(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto, ScheduledMaintenanceTask task, Integer taskIndex) throws Exception {
		if (task.getScheduledMaintenanceParts() != null && task.getScheduledMaintenanceParts().size() > 0) {
			for ( final ScheduledMaintenancePart part : task.getScheduledMaintenanceParts() ) {
				final ScheduledMaintenancePartDTO partDto = ScheduledMaintenancePartMapper.getInstance().domainToDto(part);
				partDto.setPartTaskIndex(taskIndex);
				dto.getParts().add(partDto);
			}
		}
	}

	private Integer getTaskIndex(ScheduledMaintenanceDTO dto) {
		if ( dto.getScheduledTasks() != null && dto.getScheduledTasks().size() > 0 ) {
			return dto.getScheduledTasks().size();
		}

		return 0;
	}

	private void setAssets(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) throws Exception {
		if (domain.getScheduledMaintenanceAssets().size() > 0) {
			final StringBuilder nameStr = new StringBuilder();
			for (final ScheduledMaintenanceAsset smAsset : domain.getScheduledMaintenanceAssets()) {
				dto.getAssets().add(AssetMapper.getInstance().domainToDto(smAsset.getAsset()));
				if (nameStr.length() > 0) {
					nameStr.append("," + smAsset.getAsset().getName());
				} else {
					nameStr.append(smAsset.getAsset().getName());
				}
			}
			dto.setAssetNameStr(nameStr.toString());
		}
	}

	private void setScheduledMaintenanceFile(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) throws Exception {
		if (domain.getScheduledMaintenanceFiles().size() > 0) {
			for (final ScheduledMaintenanceFile scheduledMaintenanceFile :domain.getScheduledMaintenanceFiles()) {
				final ScheduledMaintenanceFileDTO scheduledMaintenanceFileDTO=new ScheduledMaintenanceFileDTO();
				scheduledMaintenanceFileDTO.setId(scheduledMaintenanceFile.getId());
				scheduledMaintenanceFileDTO.setItemDescription(scheduledMaintenanceFile.getItemDescription());
				scheduledMaintenanceFileDTO.setVersion(scheduledMaintenanceFile.getVersion());
				scheduledMaintenanceFileDTO.setFileType(scheduledMaintenanceFile.getFileType());
				scheduledMaintenanceFileDTO.setFileLocation(scheduledMaintenanceFile.getFileLocation());
				scheduledMaintenanceFileDTO.setFileDate(scheduledMaintenanceFile.getFileDate());

				dto.getFiles().add(scheduledMaintenanceFileDTO);
			}
		}
	}

	private void setAssetsStr(ScheduledMaintenance domain, ScheduledMaintenanceDTO dto) throws Exception {
		if (domain.getScheduledMaintenanceAssets().size() > 0) {
			final StringBuilder nameStr = new StringBuilder();
			for (final ScheduledMaintenanceAsset smAsset : domain.getScheduledMaintenanceAssets()) {
				if (nameStr.length() > 0) {
					nameStr.append("," + smAsset.getAsset().getName());
				} else {
					nameStr.append(smAsset.getAsset().getName());
				}
			}
			dto.setAssetNameStr(nameStr.toString());
		}
	}

	@Override
	public void dtoToDomain(ScheduledMaintenanceDTO dto, ScheduledMaintenance domain) throws Exception {
		domain.setId(dto.getId());
		domain.setCode(dto.getCode());
		domain.setDescription(dto.getDescription());
		domain.setAdminNotes(dto.getAdminNotes());
		domain.setCompletionNotes(dto.getCompletionNotes());
		domain.setrType(dto.getrType());
		domain.setScheduleDescription(dto.getScheduleDescription());
		domain.setNotifyCreatorWhenDWOE(dto.getNotifyCreatorWhenDWOE());
		domain.setNotifyTechniciansWhenDWOE(dto.getNotifyTechniciansWhenDWOE());
		domain.setSuggestedCompletion(dto.getSuggestedCompletion());
		domain.setSuggestedTime(dto.getSuggestedTime());
		domain.setTimeEstimatedHours(dto.getTimeEstimatedHours());
		domain.setIsRunning(dto.getIsRunning());
		domain.setStartAsWoStatus(dto.getWorkOrderStatus());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public ScheduledMaintenanceDTO domainToDtoForDataTable(ScheduledMaintenance domain) throws Exception {
		final ScheduledMaintenanceDTO dto = new ScheduledMaintenanceDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());
		setAssetsStr(domain, dto);

		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}

		return dto;
	}

}
