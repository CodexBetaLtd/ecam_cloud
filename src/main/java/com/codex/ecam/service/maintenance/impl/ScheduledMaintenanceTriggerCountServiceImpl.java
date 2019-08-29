package com.codex.ecam.service.maintenance.impl; 

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codex.ecam.dao.maintenance.ScheduledMaintenanceTriggerDao;
import com.codex.ecam.dto.dashboard.ScheduledMaintenanceTriggerCountDTO;
import com.codex.ecam.service.maintenance.api.ScheduledMaintenanceTriggerCountService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.DateUtil; 

@Service
public class ScheduledMaintenanceTriggerCountServiceImpl implements ScheduledMaintenanceTriggerCountService {

	@Autowired
	private ScheduledMaintenanceTriggerDao smTriggerDao;

	@Override
	public ScheduledMaintenanceTriggerCountDTO getCount() {  
		ScheduledMaintenanceTriggerCountDTO scheduledMaintenanceCountDTO = new ScheduledMaintenanceTriggerCountDTO();
		setSmOutDatedData(scheduledMaintenanceCountDTO);
		setSmCurrentWeekData(scheduledMaintenanceCountDTO);
		setSmNextWeekData(scheduledMaintenanceCountDTO); 
		setSmNextMonthData(scheduledMaintenanceCountDTO);  
		return scheduledMaintenanceCountDTO;
	}  

	private void setSmOutDatedData(ScheduledMaintenanceTriggerCountDTO scheduledMaintenanceCountDTO) {
		Calendar cal = getEndCalendar();   
		Date toDate = cal.getTime();

		Calendar cal2 = getStartCalendar();
		cal2.add(Calendar.MONTH, - 1);
		Date fromDate = cal2.getTime(); 
		
		scheduledMaintenanceCountDTO.setSmOutDatedFromDate(DateUtil.getSimpleDateString(fromDate));
		scheduledMaintenanceCountDTO.setSmOutDatedToDate(DateUtil.getSimpleDateString(toDate));
		scheduledMaintenanceCountDTO.setSmOutDatedCount(getWoTaskCount(toDate, fromDate));
		
	} 

	private void setSmCurrentWeekData(ScheduledMaintenanceTriggerCountDTO scheduledMaintenanceCountDTO) {
		
		Calendar cal = getStartCalendar(); 
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		Date fromDate = cal.getTime(); 
		
		Calendar cal2 = getEndCalendar();  
		cal2.set(Calendar.DAY_OF_WEEK, cal2.getFirstDayOfWeek());
		cal2.add(Calendar.WEEK_OF_YEAR, 1); 
		cal2.add(Calendar.DATE, -1); 
		Date toDate = cal2.getTime();
		
		scheduledMaintenanceCountDTO.setSmCurrentWeekFromDate(DateUtil.getSimpleDateString(fromDate));
		scheduledMaintenanceCountDTO.setSmCurrentWeekToDate(DateUtil.getSimpleDateString(toDate));
		scheduledMaintenanceCountDTO.setSmCurrentWeekCount(getWoTaskCount(toDate, fromDate)); 
	}

	private void setSmNextWeekData(ScheduledMaintenanceTriggerCountDTO scheduledMaintenanceCountDTO) {
		Calendar cal = getStartCalendar(); 
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		Date fromDate = cal.getTime(); 
		
		Calendar cal2 = getEndCalendar();
		cal2.set(Calendar.DAY_OF_WEEK, cal2.getFirstDayOfWeek());
		cal2.add(Calendar.WEEK_OF_YEAR, 2);
		cal2.add(Calendar.DATE, -1); 
		Date toDate = cal2.getTime(); 
		
		scheduledMaintenanceCountDTO.setSmNextWeekFromDate(DateUtil.getSimpleDateString(fromDate));
		scheduledMaintenanceCountDTO.setSmNextWeekToDate(DateUtil.getSimpleDateString(toDate));
		scheduledMaintenanceCountDTO.setSmNextWeekCount(getWoTaskCount(toDate, fromDate)); 
	} 

	private void setSmNextMonthData(ScheduledMaintenanceTriggerCountDTO scheduledMaintenanceCountDTO) {

		Calendar cal = getStartCalendar(); 
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		Date fromDate = cal.getTime(); 
		
		Calendar cal2 = getEndCalendar();
		cal2.set(Calendar.DAY_OF_MONTH, 1);
		cal2.add(Calendar.MONTH, 2); 
		cal2.add(Calendar.DATE, -1); 
		Date toDate = cal2.getTime();
		
		scheduledMaintenanceCountDTO.setSmNextMonthFromDate(DateUtil.getSimpleDateString(fromDate));
		scheduledMaintenanceCountDTO.setSmNextMonthToDate(DateUtil.getSimpleDateString(toDate));
		scheduledMaintenanceCountDTO.setSmNextMonthCount(getWoTaskCount(toDate, fromDate)); 
	}  
	
	private Calendar getStartCalendar() {
		Calendar cal = Calendar.getInstance();  
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}

	private Calendar getEndCalendar() {
		Calendar cal = Calendar.getInstance();  
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59); 
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}

	private Integer getWoTaskCount(Date toDate, Date fromDate) { 
		if ( AuthenticationUtil.isAuthUserAdminLevel() ) {
			return smTriggerDao.getSMTriggerCount(fromDate, toDate);			
		} else if ( AuthenticationUtil.isAuthUserSystemLevel() ) {
			return smTriggerDao.getSMTriggerCountByBusiness(fromDate, toDate, AuthenticationUtil.getCurrentUser().getBusiness().getId());
		} else if(AuthenticationUtil.isAuthUserGeneralLevel()) {
			return smTriggerDao.getSMTriggerCountBySite(fromDate, toDate, AuthenticationUtil.getCurrentUser().getSite().getId());
		} else {
			return 0;
		}
	} 

}
