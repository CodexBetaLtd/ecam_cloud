package com.codex.ecam.mappers.maintenance.schedulemaintenance;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.codex.ecam.constants.SMABCTriggerType;
import com.codex.ecam.constants.SMTriggerTaskType;
import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.constants.SMTriggerTypeMeterReading;
import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenance;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceNotification;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenancePart;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTask;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.model.maintenance.workorder.WorkOrderAsset;
import com.codex.ecam.model.maintenance.workorder.WorkOrderNotes;
import com.codex.ecam.model.maintenance.workorder.WorkOrderNotification;
import com.codex.ecam.model.maintenance.workorder.WorkOrderPart;
import com.codex.ecam.model.maintenance.workorder.WorkOrderTask;

public class ScheduledMaintenanceTriggerToWorkOrderMapper {

	private static ScheduledMaintenanceTriggerToWorkOrderMapper instance = null;

	private ScheduledMaintenanceTriggerToWorkOrderMapper() {
	}

	public static ScheduledMaintenanceTriggerToWorkOrderMapper getInstance() {
		if (instance == null) {
			instance = new ScheduledMaintenanceTriggerToWorkOrderMapper();
		}
		return instance;
	}

	public WorkOrder createWorkOrderFromTrigger(ScheduledMaintenanceTrigger smt) throws Exception {
		WorkOrder wo = createWorkOrderFromScheduledMaintenance(smt.getScheduledMaintenance());
		createWorkOrderFromTrigger(smt, wo);

		return wo;
	}

	public void createWorkOrderFromTrigger(ScheduledMaintenanceTrigger smt, WorkOrder wo) throws Exception {

		if (wo.getWorkOrderAssets() != null ) {
			wo.getWorkOrderAssets().addAll(createAssetsFromTrigger(wo, smt));
		} else {
			wo.setWorkOrderAssets(createAssetsFromTrigger(wo, smt));
		}

		if ( wo.getWorkOrderTasks() != null ) {
			wo.getWorkOrderTasks().addAll(createWoTasksFromTrigger(wo, smt));
		} else {
			wo.setWorkOrderTasks(createWoTasksFromTrigger(wo, smt));
		}


	}

	public WorkOrder createWorkOrderFromScheduledMaintenance(ScheduledMaintenance sm) throws Exception {
		WorkOrder wo = new WorkOrder();

		wo.setSite(sm.getSite());
		wo.setBusiness(sm.getBusiness());
		wo.setAccount(sm.getAccount());
		wo.setChargeDepartment(sm.getChargeDepartment());
		wo.setRequestedByUser(sm.getRequestor());
		wo.setMaintenanceType(sm.getMaintenanceType());
		wo.setPriority(sm.getPriority());
		wo.setProject(sm.getProject());
		wo.setAdminNotes(sm.getAdminNotes());
		wo.setInstruction(sm.getDescription());
		wo.setWorkOrderNotifications(getNotifications(sm.getScheduledMaintenanceNotifications()));
		setWoStatus(wo, sm.getStartAsWoStatus());  
		return wo;
	}
	
	private void setWoStatus(WorkOrder wo, WorkOrderStatus startAsWoStatus) { 
		Set<WorkOrderNotes> workOrderNotes = new HashSet<>();
		WorkOrderNotes workOrderNote = new WorkOrderNotes();
		workOrderNote.setNotes("Work Order Start Status is Set to " + startAsWoStatus);
		workOrderNote.setNoteDate(new Date());
		workOrderNote.setWorkOrder(wo);
		workOrderNote.setWorkOrderStatus(startAsWoStatus);
		workOrderNote.setIsDeleted(false);
		workOrderNotes.add(workOrderNote);
		wo.setWorkOrderNotes(workOrderNotes);
		wo.setCurrentStatus(workOrderNote); 
	}

	private Set<WorkOrderAsset> createAssetsFromTrigger(WorkOrder wo, ScheduledMaintenanceTrigger trigger) {
		Set<WorkOrderAsset> woAssets = new HashSet<>();

		WorkOrderAsset woAsset = new WorkOrderAsset();
		woAsset.setAsset(trigger.getAsset());
		woAsset.setWorkOrder(wo);
		woAsset.setIsDeleted(false);

		woAssets.add(woAsset);

		return woAssets;
	}

	private Set<WorkOrderNotification> getNotifications( Set<ScheduledMaintenanceNotification> scheduledMaintenanceNotifications) {
		Set<WorkOrderNotification> woNotifications = new HashSet<>();
		return woNotifications;
	}

	private Set<WorkOrderTask> createWoTasksFromTrigger(WorkOrder wo, ScheduledMaintenanceTrigger trigger) {

		Set<WorkOrderTask> woTasks = new HashSet<>();
		
		if ((trigger.getScheduledMaintenanceTasks() != null) && (trigger.getScheduledMaintenanceTasks().size() > 0)) {
			if(trigger.getTriggerType().equals(SMTriggerType.ABC_METER_READING_TRIGGER)){
				Double currentValue = trigger.getMrtAssetMeterReading().getCurrentAssetMeterReadingValue().getMeterReadingValue();
				;
				SMTriggerTypeMeterReading triggerTypeMeterReading=SMTriggerTypeMeterReading.processMeterReadingToCurrentTrigger(currentValue, trigger.getSmabcTriggerType());
					if(triggerTypeMeterReading.getSmTriggerTaskType().equals(SMTriggerTaskType.A_TASK)){
						getATasks(wo,woTasks,trigger.getScheduledMaintenanceTasks(),trigger.getAsset());
					}else if(triggerTypeMeterReading.getSmTriggerTaskType().equals(SMTriggerTaskType.B_TASK)){
						getATasks(wo,woTasks,trigger.getScheduledMaintenanceTasks(),trigger.getAsset());
						getBTasks(wo,woTasks,trigger.getScheduledMaintenanceTasks(),trigger.getAsset());
					}else if(triggerTypeMeterReading.getSmTriggerTaskType().equals(SMTriggerTaskType.C_TASK)){
						getATasks(wo,woTasks,trigger.getScheduledMaintenanceTasks(),trigger.getAsset());
						getBTasks(wo,woTasks,trigger.getScheduledMaintenanceTasks(),trigger.getAsset());
						getCTasks(wo,woTasks,trigger.getScheduledMaintenanceTasks(),trigger.getAsset());
					}

				//createWorkOrderTasksFromTaskABCTriggers(wo, woTasks, trigger.getScheduledMaintenanceTasks(), trigger.getAsset());

			}else{
				createWorkOrderTasksFromTaskTriggers(wo, woTasks, trigger.getScheduledMaintenanceTasks(), trigger.getAsset());
			}
		}

		return woTasks;
	}

	private void getATasks(WorkOrder workOrder,Set<WorkOrderTask> woTasks,Set<ScheduledMaintenanceTask> scheduledMaintenanceTasks, Asset asset){
		for(ScheduledMaintenanceTask task: scheduledMaintenanceTasks){
			if(task.getTaskGroup().getName().equals("A service")){
				woTasks.add(createWorkOrderTaskFromTaskTrigger(workOrder,task, asset));
			}
		}
	}
	private void getBTasks(WorkOrder workOrder,Set<WorkOrderTask> woTasks,Set<ScheduledMaintenanceTask> scheduledMaintenanceTasks, Asset asset){
		for(ScheduledMaintenanceTask task: scheduledMaintenanceTasks){
			if(task.getTaskGroup().getName().equals("B service")){
				woTasks.add(createWorkOrderTaskFromTaskTrigger(workOrder,task, asset));
			}
		}
	}
	private void getCTasks(WorkOrder workOrder,Set<WorkOrderTask> woTasks,Set<ScheduledMaintenanceTask> scheduledMaintenanceTasks, Asset asset){
		for(ScheduledMaintenanceTask task: scheduledMaintenanceTasks){
			if(task.getTaskGroup().getName().equals("C service")){
				woTasks.add(createWorkOrderTaskFromTaskTrigger(workOrder,task, asset));
			}
		}
}
	

	private void createWorkOrderTasksFromTaskTriggers(WorkOrder wo, Set<WorkOrderTask> woTasks, Set<ScheduledMaintenanceTask> smTasks, Asset asset) {
		WorkOrderTask woTask;
		for (ScheduledMaintenanceTask task : smTasks) {
			woTask = createWorkOrderTaskFromTaskTrigger(wo, task, asset);
			woTasks.add(woTask);
		}
	}
	private void createWorkOrderTasksFromTaskABCTriggers(WorkOrder wo, Set<WorkOrderTask> woTasks, Set<ScheduledMaintenanceTask> smTasks, Asset asset) {
		WorkOrderTask woTask;
		for (ScheduledMaintenanceTask task : smTasks) {
			woTask = createWorkOrderTaskFromTaskTrigger(wo, task, asset);
			woTasks.add(woTask);
		}
	}

	private WorkOrderTask createWorkOrderTaskFromTaskTrigger(WorkOrder wo, ScheduledMaintenanceTask smTask, Asset asset) {
		WorkOrderTask woTask = new WorkOrderTask();

		woTask.setAsset(asset);
		woTask.setScheduledMaintenanceTask(smTask);
		woTask.setEstimatedHours(smTask.getEstimatedHours());
		woTask.setAssignedUser(smTask.getAssignedUser());
		woTask.setDescription(smTask.getDescription());
		woTask.setName(smTask.getDescription());
		woTask.setTaskGroup(smTask.getTaskGroup());
		woTask.setTaskType(smTask.getTaskType());
		woTask.setWorkOrder(wo);

		setPartsToWoTask(smTask, woTask);

		return woTask;
	}

	private void setPartsToWoTask(ScheduledMaintenanceTask smTask, WorkOrderTask woTask) {
		Set<WorkOrderPart> parts = new HashSet<>();
		WorkOrderPart part;

		for ( ScheduledMaintenancePart smPart  : smTask.getScheduledMaintenanceParts() ) {

			part = new WorkOrderPart();
			part.setEstimatedQuantity(smPart.getSuggestedQuantity());
			part.setWorkOrder(woTask.getWorkOrder());
			part.setWorkOrderTask(woTask);
			part.setPart(smPart.getPart());
			part.setStock(smPart.getStock());
			part.setIsDeleted(false);

			parts.add(part);
		}

		woTask.setWorkOrderParts(parts);
	}

}
