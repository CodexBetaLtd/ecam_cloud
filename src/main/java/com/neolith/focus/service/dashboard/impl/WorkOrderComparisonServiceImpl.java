package com.neolith.focus.service.dashboard.impl;

import java.util.Calendar;
import java.util.Date; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neolith.focus.constants.WorkOrderStatus;
import com.neolith.focus.dao.maintenance.WorkOrderDao; 
import com.neolith.focus.dto.dashboard.WorkOrderComparisonChartDataDTO; 
import com.neolith.focus.service.dashboard.api.WorkOrderComparisonService; 

@Service
public class WorkOrderComparisonServiceImpl implements WorkOrderComparisonService {

	@Autowired
	private WorkOrderDao workOrderDao;
	
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

	@Override
	public WorkOrderComparisonChartDataDTO getWoComparisonChartData() {
		WorkOrderComparisonChartDataDTO dto = new WorkOrderComparisonChartDataDTO();
		setPreviousWeekWoData(dto);
		setCurrentWeekWoData(dto);
		setNextWeekWoData(dto); 
		return dto;
	}

	private void setPreviousWeekWoData(WorkOrderComparisonChartDataDTO dto) {
		Calendar cal = getEndCalendar();   
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		cal.add(Calendar.DAY_OF_YEAR, -1);
		Date toDate = cal.getTime();
		
		Calendar cal2 = getStartCalendar(); 
		cal2.set(Calendar.DAY_OF_WEEK, cal2.getFirstDayOfWeek());
		cal2.add(Calendar.WEEK_OF_YEAR, -1);
		Date fromDate = cal2.getTime(); 
		
		dto.setPreviousWeekOpenWo(workOrderDao.findAllWorkOrdersOnDurationByStatus(fromDate, toDate, WorkOrderStatus.OPEN));
		dto.setPreviousWeekClosedWo(workOrderDao.findAllWorkOrdersOnDurationByStatus(fromDate, toDate, WorkOrderStatus.CLOSED));
	} 

	private void setCurrentWeekWoData(WorkOrderComparisonChartDataDTO dto) {
		Calendar cal = getStartCalendar(); 
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		Date fromDate = cal.getTime(); 
		
		Calendar cal2 = getEndCalendar();   
		cal2.set(Calendar.DAY_OF_WEEK, cal2.getFirstDayOfWeek());
		cal2.add(Calendar.WEEK_OF_YEAR, 1);
		cal2.add(Calendar.DAY_OF_YEAR, -1);
		Date toDate = cal2.getTime();

		
		dto.setCurrentWeekOpenWo(workOrderDao.findAllWorkOrdersOnDurationByStatus(fromDate, toDate, WorkOrderStatus.OPEN));
		dto.setCurrentWeekClosedWo(workOrderDao.findAllWorkOrdersOnDurationByStatus(fromDate, toDate, WorkOrderStatus.CLOSED));
	}  
	
	private void setNextWeekWoData(WorkOrderComparisonChartDataDTO dto) {
		Calendar cal = getStartCalendar(); 
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		Date fromDate = cal.getTime(); 

		Calendar cal2 = getEndCalendar(); 
		cal2.set(Calendar.DAY_OF_WEEK, cal2.getFirstDayOfWeek());
		cal2.add(Calendar.WEEK_OF_YEAR, 2);
		cal2.add(Calendar.DAY_OF_YEAR, -1);
		Date toDate = cal2.getTime();
		
		dto.setNextWeekOpenWo(workOrderDao.findAllWorkOrdersOnDurationByStatus(fromDate, toDate, WorkOrderStatus.OPEN));
		dto.setNextWeekClosedWo(workOrderDao.findAllWorkOrdersOnDurationByStatus(fromDate, toDate, WorkOrderStatus.CLOSED));
	}
	
}
