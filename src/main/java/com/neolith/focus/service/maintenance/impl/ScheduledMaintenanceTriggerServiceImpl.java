package com.neolith.focus.service.maintenance.impl;

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

import com.neolith.focus.constants.LogType;
import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.constants.SMTriggerType; 
import com.neolith.focus.dao.maintenance.ScheduledMaintenanceDao;
import com.neolith.focus.dao.maintenance.ScheduledMaintenanceTriggerDao;
import com.neolith.focus.dao.maintenance.WorkOrderDao; 
import com.neolith.focus.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTriggerDTO; 
import com.neolith.focus.mappers.maintenance.schedulemaintenance.ScheduledMaintenanceTriggerMapper;
import com.neolith.focus.mappers.maintenance.schedulemaintenance.ScheduledMaintenanceTriggerToWorkOrderMapper;
import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenance; 
import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;
import com.neolith.focus.model.maintenance.workorder.WorkOrder;
import com.neolith.focus.model.maintenance.workorder.WorkOrderLog; 
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.maintenance.WorkOrderResult;
import com.neolith.focus.service.maintenance.api.ScheduledMaintenanceTriggerService;
import com.neolith.focus.service.maintenance.api.ScheduledService;
import com.neolith.focus.service.maintenance.api.WorkOrderService;
import com.neolith.focus.util.AuthenticationUtil;
import com.neolith.focus.util.DateUtil; 
import com.neolith.focus.util.search.scheduledmaintenance.ScheduledMaintenanceTriggerSearchPropertyMapper;

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
		Date today = Calendar.getInstance().getTime();
		DataTablesOutput<ScheduledMaintenanceTrigger> domainOut;

		if(AuthenticationUtil.isAuthUserAdminLevel()){
			Specification<ScheduledMaintenanceTrigger> specification = (root, query, cb) -> {
				return cb.greaterThanOrEqualTo(root.get("ttNextCalenderEvent").get("scheduledDate"), today);
			};
			domainOut = scheduledMaintenanceTriggerDao.findAll(input, specification);
		} else if(AuthenticationUtil.isAuthUserSystemLevel()){
			Specification<ScheduledMaintenanceTrigger> specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
				Predicate predicate2 = cb.greaterThanOrEqualTo(root.get("ttNextCalenderEvent").get("scheduledDate"), today);
				return cb.and(predicate, predicate2);
			};
			domainOut = scheduledMaintenanceTriggerDao.findAll(input, specification);
		} else {
			Specification<ScheduledMaintenanceTrigger> specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
				Predicate predicate2 = cb.greaterThanOrEqualTo(root.get("ttNextCalenderEvent").get("scheduledDate"), today);
				return cb.and(predicate, predicate2);
			};
			domainOut = scheduledMaintenanceTriggerDao.findAll(input, specification);
		}

		DataTablesOutput<ScheduledMaintenanceTriggerDTO> out = ScheduledMaintenanceTriggerMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	} 

	@Override
	public DataTablesOutput<ScheduledMaintenanceTriggerDTO> findTasksBetweenDates(FocusDataTablesInput dataTablesInput, String from, String to) throws Exception {
		Calendar cal = getStartCalendar(DateUtil.getDateObj(from)); 
		Date fromDate = cal.getTime();
		
		Calendar cal2 = getEndCalendar(DateUtil.getDateObj(to)); 
		Date toDate = cal2.getTime();
		
		ScheduledMaintenanceTriggerSearchPropertyMapper.getInstance().generateDataTableInput(dataTablesInput); 
		Specification<ScheduledMaintenanceTrigger> specification = getScheduledTaskBetweenDateRange(toDate, fromDate);
		DataTablesOutput<ScheduledMaintenanceTrigger> domainOut = scheduledMaintenanceTriggerDao.findAll(dataTablesInput, specification);
		
		return ScheduledMaintenanceTriggerMapper.getInstance().domainToDTODataTablesOutput(domainOut);
	} 
	
	private Calendar getEndCalendar(Date dateObj) {
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(dateObj);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59); 
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}


	private Calendar getStartCalendar(Date dateObj) {
		Calendar cal = Calendar.getInstance(); 
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
			Predicate toPredicate = cb.greaterThanOrEqualTo(root.get("ttNextCalenderEvent").get("scheduledDate"), fromDate);  
			Predicate fromPredicate = cb.lessThanOrEqualTo(root.get("ttNextCalenderEvent").get("scheduledDate"), toDate); 
			return cb.and( fromPredicate, toPredicate );
		};
	}

	@Override
	public WorkOrder createWorkOrderFromTrigger(ScheduledMaintenanceTrigger smt) throws Exception {

		WorkOrder wo = ScheduledMaintenanceTriggerToWorkOrderMapper.getInstance().createWorkOrderFromTrigger(smt);

		if ( smt.getTriggerType().equals( SMTriggerType.TIME_TRIGGER )) {
			scheduledService.setNextCalendarEvent(smt, smt.getTtNextCalenderEvent().getScheduledDate());
		}

		return wo;
	}

	@Override
	public WorkOrder createWorkOrderFromManualTrigger(ScheduledMaintenanceTrigger smt, WorkOrder wo) throws Exception {

		ScheduledMaintenanceTriggerToWorkOrderMapper.getInstance().createWorkOrderFromTrigger(smt, wo);

		if ( smt.getTriggerType().equals( SMTriggerType.TIME_TRIGGER )) {
			scheduledService.setNextCalendarEvent(smt, new Date());
		}

		return wo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public WorkOrderResult manualTrigger(String idStr, Integer smId) {
		WorkOrderResult result = new WorkOrderResult(null, null);
		try {
			ScheduledMaintenance sm = scheduledMaintenanceDao.findOne(smId);
			WorkOrder wo = ScheduledMaintenanceTriggerToWorkOrderMapper.getInstance().createWorkOrderFromScheduledMaintenance(sm);
			wo.setScheduledMaintenance(sm);

			List<Integer> ids = Arrays.asList(idStr.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
			for (Integer id : ids) {
				ScheduledMaintenanceTrigger trigger = scheduledMaintenanceTriggerDao.findOne(id);
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
			result.addToMessageList("Successfully Generated the Work Order With ID Code");
			result.addToMessageList(wo.getCode());
			result.addToMessageList(wo.getId().toString());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setStatus(ResultStatus.ERROR);
			result.addToErrorList("Work Order Creation Failed.");
		}

		return result;
	}

	private void addWoLog(WorkOrder wo) {

		Set<WorkOrderLog> logs = new HashSet<WorkOrderLog>();

		WorkOrderLog log = new WorkOrderLog();
		log.setWorkOrder(wo);
		log.setNotes("Work Order Created from SM : " + wo.getScheduledMaintenance().getCode());
		log.setLogType(LogType.CREATE);
		log.setIsDeleted(false);

		logs.add(log);

		wo.setWorkOrderLogs(logs);
	}

}
