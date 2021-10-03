package com.codex.ecam.service.maintenance.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.LogType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.dao.maintenance.ScheduledMaintenanceDao;
import com.codex.ecam.dao.maintenance.ScheduledMaintenanceTriggerDao;
import com.codex.ecam.dao.maintenance.WorkOrderDao;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTriggerDTO;
import com.codex.ecam.mappers.maintenance.schedulemaintenance.ScheduledMaintenanceTriggerMapper;
import com.codex.ecam.mappers.maintenance.schedulemaintenance.ScheduledMaintenanceTriggerToWorkOrderMapper;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenance;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.model.maintenance.workorder.WorkOrderLog;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.maintenance.WorkOrderResult;
import com.codex.ecam.service.maintenance.api.ScheduledMaintenanceTriggerService;
import com.codex.ecam.service.maintenance.api.ScheduledService;
import com.codex.ecam.service.maintenance.api.WorkOrderService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.DateUtil;
import com.codex.ecam.util.search.scheduledmaintenance.ScheduledMaintenanceTriggerSearchPropertyMapper;

@Service
public class ScheduledMaintenanceTriggerServiceImpl implements ScheduledMaintenanceTriggerService {

	private final static Logger logger = LoggerFactory.getLogger(ScheduledMaintenanceTriggerServiceImpl.class);

	@Autowired
	private ScheduledMaintenanceTriggerDao scheduledMaintenanceTriggerDao;

	@Autowired
	private WorkOrderService workOrderService;

	@Autowired
	private ScheduledService scheduledService;

	@Autowired
	private ScheduledMaintenanceDao scheduledMaintenanceDao;

	@Autowired
	private WorkOrderDao workOrderDao;

	@Override
	public DataTablesOutput<ScheduledMaintenanceTriggerDTO> findUpcomingScheduleMaintenance(FocusDataTablesInput input) throws Exception {
		final Date today = Calendar.getInstance().getTime();
		DataTablesOutput<ScheduledMaintenanceTrigger> domainOut;

		if(AuthenticationUtil.isAuthUserAdminLevel()){
			final Specification<ScheduledMaintenanceTrigger> specification = (root, query, cb) -> {
				return cb.greaterThanOrEqualTo(root.get("ttNextCalenderEvent").get("scheduledDate"), today);
			};
			domainOut = scheduledMaintenanceTriggerDao.findAll(input, specification);
		} else if(AuthenticationUtil.isAuthUserSystemLevel()){
			final Specification<ScheduledMaintenanceTrigger> specification = (root, query, cb) -> {
				final Predicate predicate = cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
				final Predicate predicate2 = cb.greaterThanOrEqualTo(root.get("ttNextCalenderEvent").get("scheduledDate"), today);
				return cb.and(predicate, predicate2);
			};
			domainOut = scheduledMaintenanceTriggerDao.findAll(input, specification);
		} else {
			final Specification<ScheduledMaintenanceTrigger> specification = (root, query, cb) -> {
				final Predicate predicate = cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
				final Predicate predicate2 = cb.greaterThanOrEqualTo(root.get("ttNextCalenderEvent").get("scheduledDate"), today);
				return cb.and(predicate, predicate2);
			};
			domainOut = scheduledMaintenanceTriggerDao.findAll(input, specification);
		}

		final DataTablesOutput<ScheduledMaintenanceTriggerDTO> out = ScheduledMaintenanceTriggerMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<ScheduledMaintenanceTriggerDTO> findTasksBetweenDates(FocusDataTablesInput dataTablesInput, String from, String to) throws Exception {
		final Calendar cal = getStartCalendar(DateUtil.getDateObj(from));
		final Date fromDate = cal.getTime();

		final Calendar cal2 = getEndCalendar(DateUtil.getDateObj(to));
		final Date toDate = cal2.getTime();

		ScheduledMaintenanceTriggerSearchPropertyMapper.getInstance().generateDataTableInput(dataTablesInput);
		final Specification<ScheduledMaintenanceTrigger> specification = getScheduledTaskBetweenDateRange(toDate, fromDate);
		final DataTablesOutput<ScheduledMaintenanceTrigger> domainOut = scheduledMaintenanceTriggerDao.findAll(dataTablesInput, specification);

		return ScheduledMaintenanceTriggerMapper.getInstance().domainToDTODataTablesOutput(domainOut);
	}

	private Calendar getEndCalendar(Date dateObj) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(dateObj);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}


	private Calendar getStartCalendar(Date dateObj) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(dateObj);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}


	private Specification<ScheduledMaintenanceTrigger> getScheduledTaskBetweenDateRange(Date toDate, Date fromDate) {
		return (root, query, cb) -> {
			final Predicate toPredicate = cb.greaterThanOrEqualTo(root.get("ttNextCalenderEvent").get("scheduledDate"), fromDate);
			final Predicate fromPredicate = cb.lessThanOrEqualTo(root.get("ttNextCalenderEvent").get("scheduledDate"), toDate);
			if ( AuthenticationUtil.isAuthUserSystemLevel() ) {
				final Predicate businessPredicate = cb.equal(root.get("asset").get("business").get("id"), AuthenticationUtil.getCurrentUser().getBusiness().getId());
				return cb.and( fromPredicate, toPredicate, businessPredicate );
			} else if (AuthenticationUtil.isAuthUserGeneralLevel()) {
				final Predicate sitePredicate = cb.equal(root.get("asset").get("site").get("id"), AuthenticationUtil.getCurrentUser().getSite().getId());
				return cb.and( fromPredicate, toPredicate, sitePredicate );
			} else {
				return cb.and( fromPredicate, toPredicate );
			}
		};
	}

	@Override
	public WorkOrder createWorkOrderFromTrigger(ScheduledMaintenanceTrigger smt) throws Exception {
		final WorkOrder wo = ScheduledMaintenanceTriggerToWorkOrderMapper.getInstance().createWorkOrderFromTrigger(smt);
		final String code=workOrderService.findCurrentWorkOrderCode(smt.getAsset().getBusiness().getId()).getData();
		wo.setCode(code);

		if ( smt.getTriggerType().equals( SMTriggerType.TIME_TRIGGER )) {
			scheduledService.setNextCalendarEvent(smt, smt.getTtNextCalenderEvent().getScheduledDate());
		}
		if ( smt.getTriggerType().equals( SMTriggerType.METER_READING_TRIGGER )) {
			scheduledService.setNextMeterReading(smt);
		}
		if ( smt.getTriggerType().equals( SMTriggerType.ABC_METER_READING_TRIGGER )) {
			scheduledService.setNextABCMeterReading(smt);
		}


		return wo;
	}

	@Override
	public WorkOrder createWorkOrderFromManualTrigger(ScheduledMaintenanceTrigger smt, WorkOrder wo) throws Exception {

		ScheduledMaintenanceTriggerToWorkOrderMapper.getInstance().createWorkOrderFromTrigger(smt, wo);

		if ( smt.getTriggerType().equals( SMTriggerType.TIME_TRIGGER )) {
			scheduledService.setNextCalendarEvent(smt, new Date());
		}

		if ( smt.getTriggerType().equals( SMTriggerType.METER_READING_TRIGGER )) {
			scheduledService.setNextMeterReading(smt);
		}

		if ( smt.getTriggerType().equals( SMTriggerType.ABC_METER_READING_TRIGGER )) {
			scheduledService.setNextABCMeterReading(smt);
		}

		return wo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public WorkOrderResult manualTrigger(String idStr, Integer smId) throws Exception {
		final WorkOrderResult result = new WorkOrderResult(null, null);
		try {
			final ScheduledMaintenance sm = scheduledMaintenanceDao.findOne(smId);
			final WorkOrder wo = ScheduledMaintenanceTriggerToWorkOrderMapper.getInstance().createWorkOrderFromScheduledMaintenance(sm);
			wo.setScheduledMaintenance(sm);

			final List<Integer> ids = Arrays.asList(idStr.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
			for (final Integer id : ids) {
				final ScheduledMaintenanceTrigger trigger = scheduledMaintenanceTriggerDao.findOne(id);
				if ( scheduledService.isAllSMWorkOrdersClosed( trigger.getScheduledMaintenanceTasks() ) ) {
					createWorkOrderFromManualTrigger(trigger, wo);
				} else {
					result.setStatus(ResultStatus.ERROR);
					result.addToErrorList("Work Order Creation stopped due to unclosed work orders in the selected triggers.");

					return result;
				}
			}

			wo.setCode(workOrderService.findCurrentWorkOrderCode(sm.getBusiness().getId()).getData() + "/SM_" + wo.getScheduledMaintenance().getId() );
			addWoLog(wo);
			workOrderDao.save(wo);
			result.setResultStatusSuccess();
			result.addToMessageList("Successfully Generated the Work Order With ID Code");
			result.addToMessageList(wo.getCode());
			result.addToMessageList(wo.getId().toString());

		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setResultStatusError();
			result.addToErrorList("Work Order Creation Failed.");
		}

		return result;
	}

	private void addWoLog(WorkOrder wo) {

		final Set<WorkOrderLog> logs = new HashSet<WorkOrderLog>();

		final WorkOrderLog log = new WorkOrderLog();
		log.setWorkOrder(wo);
		log.setNotes("Work Order Created from SM : " + wo.getScheduledMaintenance().getCode());
		log.setLogType(LogType.CREATE);
		log.setIsDeleted(false);

		logs.add(log);

		wo.setWorkOrderLogs(logs);
	}

}
