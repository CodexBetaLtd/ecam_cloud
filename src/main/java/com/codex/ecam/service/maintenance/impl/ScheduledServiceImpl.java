package com.codex.ecam.service.maintenance.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.MeterReadingLogicType;
import com.codex.ecam.constants.SMABCTriggerType;
import com.codex.ecam.constants.SMMeterReadingType;
import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.constants.SMTriggerTypeMeterReading;
import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.dao.maintenance.ScheduledMaintenanceTriggerDao;
import com.codex.ecam.dao.maintenance.WorkOrderDao;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.maintenance.CalendarEvent;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceAsset;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTask;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.model.maintenance.workorder.WorkOrderTask;
import com.codex.ecam.service.maintenance.api.ScheduledMaintenanceTriggerService;
import com.codex.ecam.service.maintenance.api.ScheduledService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.SchedulingUtil;

@Service
public class ScheduledServiceImpl implements ScheduledService {

	private final static Logger logger = LoggerFactory.getLogger(ScheduledServiceImpl.class);

	@Autowired
	private ScheduledMaintenanceTriggerService scheduledMaintenanceTriggerService;

	@Autowired
	private ScheduledMaintenanceTriggerDao scheduledMaintenanceTriggerDao;

	@Autowired
	private WorkOrderDao workOrderDao;

	@Override
	@Scheduled(cron = "0 0 * * * *")
	public void triggerTimeSchedules() {

		try {

			logger.info("Triggers Fired........");

			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date startTime = cal.getTime();

			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			Date endTime = cal.getTime();

			Set<ScheduledMaintenanceTrigger> triggers = scheduledMaintenanceTriggerDao.findCurrentHourTriggers(startTime, endTime, SMTriggerType.TIME_TRIGGER);

			for(ScheduledMaintenanceTrigger smt : triggers){

				AuthenticationUtil.setTriggerUser(smt.getCreatedUser());
				createWorkOrderFromTriggerType(smt , SMTriggerType.TIME_TRIGGER);

			}

			logger.info("Triggers Successfully Completed and Stopped.");

		} catch (Exception e) {

			logger.error("Triggers Failed. Due To " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void notifyAssetTrigger(Asset asset, SMTriggerType triggerType) {
		if (asset != null) {

			Set<ScheduledMaintenanceAsset> smAssets = asset.getAssetScheduledMaintenances();

			if ((smAssets != null) && (smAssets.size() > 0)) {

				for (ScheduledMaintenanceAsset sma : smAssets) {

					for ( ScheduledMaintenanceTrigger smt : sma.getScheduledMaintenance().getScheduledMaintenanceTriggers() ) {

						createWorkOrderFromTriggerType(smt, triggerType);
					}
				}
			}
		}
	}

	public void createWorkOrderFromTriggerType(ScheduledMaintenanceTrigger smt, SMTriggerType triggerType) {

		if( smt.getTriggerType().equals(triggerType) && isAllSMWorkOrdersClosed( smt.getScheduledMaintenanceTasks() ) ){

			logger.info("Generating WO for Scheduled Maintenance : " + smt.getScheduledMaintenance().getCode() + " ( Trigger Id : " + smt.getId() + " )");

			try {
				generateWorkOrder(smt);
			} catch (Exception e) {
				logger.error("Generating WO failed For SM : " + smt.getScheduledMaintenance().getCode() + " ( Trigger Id : " + smt.getId() + " ) due to " + e.getMessage());
				e.printStackTrace();
			}
		} else {

			logger.info("There Are Open Work Orders. Not generated WO for Scheduled Maintenance : " + smt.getScheduledMaintenance().getCode() + " ( Trigger Id : " + smt.getId() + " ) ");
		}
	}

	@Override
	public boolean isAllSMWorkOrdersClosed(Set<ScheduledMaintenanceTask> smTasks) {

		if ((smTasks != null) && (smTasks.size() > 0)) {

			for ( ScheduledMaintenanceTask smTask : smTasks ) {
				for ( WorkOrderTask woTask : smTask.getWorkOrderTasks()) {
					if( woTask.getWorkOrder().getCurrentStatus().getWorkOrderStatus().equals(WorkOrderStatus.OPEN) ){
						return false;
					}
				}
			}
		}

		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private void generateWorkOrder(ScheduledMaintenanceTrigger smt) throws Exception {

		if (isTriggerConditionsMet(smt)) {

			logger.info("Creating Scheduled WOs For SM  : " + smt.getScheduledMaintenance().getCode());

			WorkOrder workOrder = scheduledMaintenanceTriggerService.createWorkOrderFromTrigger(smt);

			workOrderDao.save(workOrder);

			logger.info("Succefully Generate and saved the WO for SM : " + smt.getScheduledMaintenance().getCode() + " ( Trigger Id : " + smt.getId() + " ) ");
		} else {
			logger.info("Trigger Conditions Failed For SM  : " + smt.getScheduledMaintenance().getCode() + " ( Trigger Id : " + smt.getId() + " ) ");
		}
	}

	public Boolean isTriggerConditionsMet(ScheduledMaintenanceTrigger trigger) {

		Boolean isConditionMet = false;

		switch (trigger.getTriggerType()) {

		case EVENT_TRIGGER:
			isConditionMet = isEventTriggerFired(trigger);
			break;

		case TIME_TRIGGER:
			isConditionMet = isTimeTriggerConditionMet(trigger);
			break;

		case METER_READING_TRIGGER:
			isConditionMet =  isNextMeterReadingEventFired(trigger);
			break;
		case ABC_METER_READING_TRIGGER:
			isConditionMet =  isNextABCMeterReadingEventFired(trigger);
			break;

		default:
			break;
		}

		return isConditionMet;

	}

	private Boolean isTimeTriggerConditionMet(ScheduledMaintenanceTrigger trigger) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date startTime = cal.getTime();

		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		Date endTime = cal.getTime();

		return isNextCalenderEventFired(trigger, startTime, endTime);
	}

	private boolean isEventTriggerFired(ScheduledMaintenanceTrigger tr) {
		if (tr.getEtAssetEventTypeAsset().equals(tr.getAsset().getCurrentAssetEvent().getAssetEventTypeAsset())) {
			return true;
		}

		return false;
	}
	
	private boolean isNextABCMeterReadingEventFired(ScheduledMaintenanceTrigger tr) {

		Double currentValue = tr.getMrtAssetMeterReading().getCurrentAssetMeterReadingValue().getMeterReadingValue();
//			if(currentValue.equals(tr.getAmrtNextMeterReading())||currentValue.equals(tr.getBmrtNextMeterReading())||currentValue.equals(tr.getCmrtNextMeterReading()) ){
//				return true;
//			}
		
			if(currentValue.equals(SMTriggerTypeMeterReading.processMeterReadingToCurrent(currentValue,tr.getSmabcTriggerType()))){
				return true;	
			}

		return false;
	}

	private boolean isNextMeterReadingEventFired(ScheduledMaintenanceTrigger tr) {

		Double currentValue = tr.getMrtAssetMeterReading().getCurrentAssetMeterReadingValue().getMeterReadingValue();
		if(tr.getMrtType().equals(SMMeterReadingType.WHEN)){
			if (tr.getMrtLogicType().equals(MeterReadingLogicType.GREATER_THAN)) {

				if (currentValue > tr.getConditionValue()) {

					return true;
				}

			} else if (tr.getMrtLogicType().equals(MeterReadingLogicType.LESS_THAN)) {

				if (currentValue < tr.getConditionValue()) {

					return true;
				}

			} else if (tr.getMrtLogicType().equals(MeterReadingLogicType.GREATER_THAN_EQUAL)) {

				if (currentValue >= tr.getConditionValue()) {

					return true;
				}

			} 
			else if (tr.getMrtLogicType().equals(MeterReadingLogicType.LESS_THAN_EQUAL)) {

				if (currentValue <= tr.getConditionValue()) {

					return true;
				}

			} 
		}else if(tr.getMrtType().equals(SMMeterReadingType.EVERY)){
			if (currentValue.equals(tr.getMrtNextMeterReading())) {
				Double nextValue = tr.getMrtNextMeterReading() + tr.getConditionValue();

				if(!tr.getNoEndValue()){
					if (nextValue <= tr.getMrtEndMeterReading()) {
						tr.setMrtNextMeterReading(nextValue);
					} else {
						tr.setMrtNextMeterReading(null);
					}
				}else{
					tr.setMrtNextMeterReading(nextValue);

				}


				return true;
			}
		}


		return false;
	}

	private boolean isNextCalenderEventFired(ScheduledMaintenanceTrigger tr, Date startTime, Date endTime) {

		Date scheduledDate = tr.getTtNextCalenderEvent().getScheduledDate();

		if ( (scheduledDate.compareTo(startTime) >= 0) && (scheduledDate.compareTo(endTime) <= 0)) {
			setNextCalendarEvent(tr, scheduledDate);
			return true;
		}

		return false;
	}

	@Override
	public void setNextCalendarEvent(ScheduledMaintenanceTrigger smt, Date startDate) {
		CalendarEvent event = SchedulingUtil.getNextCalenderEvent(smt, startDate);

		if ( smt.getCalendarEvents() != null ) {
			smt.getCalendarEvents().add(event);
		} else {
			List<CalendarEvent> events = new ArrayList<>();
			events.add(event);
			smt.setCalendarEvents(events);
		}

		smt.setTtNextCalenderEvent(event);
		scheduledMaintenanceTriggerDao.save(smt);
	}

	public void setNextMeterReading(ScheduledMaintenanceTrigger smt) {
;
		scheduledMaintenanceTriggerDao.save(smt);
	}

	@Override
	public void setNextABCMeterReading(ScheduledMaintenanceTrigger smt) {
		Double currentValue = smt.getMrtAssetMeterReading().getCurrentAssetMeterReadingValue().getMeterReadingValue();
		Double nextTriggerValue=0.0;
		SMTriggerTypeMeterReading smTriggerTypeMeterReading=SMTriggerTypeMeterReading.processMeterReadingToCurrentTrigger(currentValue, smt.getSmabcTriggerType());
		SMTriggerTypeMeterReading smTriggerTypeMeterReadingNext=SMTriggerTypeMeterReading.findNextTriggerByIndex(smTriggerTypeMeterReading);
		nextTriggerValue=currentValue-smTriggerTypeMeterReading.getMeterReadingValue()+smTriggerTypeMeterReadingNext.getMeterReadingValue();
	if (smTriggerTypeMeterReading.equals(SMTriggerTypeMeterReading.T1_C1)) {
		nextTriggerValue=smt.getMrtNextMeterReading()+smTriggerTypeMeterReadingNext.getMeterReadingValue();
	} else if (smTriggerTypeMeterReading.equals(SMTriggerTypeMeterReading.T2_C2)) {
		nextTriggerValue=smt.getMrtNextMeterReading()+smTriggerTypeMeterReadingNext.getMeterReadingValue();
	}
		
		smt.setMrtNextMeterReading(nextTriggerValue);
		

		
		scheduledMaintenanceTriggerDao.save(smt);
		
	}
	
	
	
	
	
}
