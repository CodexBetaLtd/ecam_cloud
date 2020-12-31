package com.codex.ecam.service.dashboard.impl;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.dao.maintenance.WorkOrderDao;
import com.codex.ecam.dto.dashboard.WorkOrderComparisonChartDataDTO;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.service.dashboard.api.WorkOrderComparisonService;
import com.codex.ecam.util.AuthenticationUtil; 

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
	
		setOpenCloseDataByUserLevel(dto, fromDate, toDate);
		
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

		
		setOpenCloseDataByUserLevel(dto, fromDate, toDate);
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
		
		setOpenCloseDataByUserLevel(dto, fromDate, toDate);
	}
	
	private void setOpenCloseDataByUserLevel(WorkOrderComparisonChartDataDTO dto, Date fromDate, Date toDate) {
		
		if ( AuthenticationUtil.isAuthUserAdminLevel() ) {
			dto.setNextWeekOpenWo(workOrderDao.findAllWorkOrdersOnDurationByStatus(fromDate, toDate, WorkOrderStatus.OPEN));
			dto.setNextWeekClosedWo(workOrderDao.findAllWorkOrdersOnDurationByStatus(fromDate, toDate, WorkOrderStatus.CLOSED));
			findOnTimeCompletedWoWithCompltedWo(dto);
		} else if ( AuthenticationUtil.isAuthUserSystemLevel() ) {
			dto.setNextWeekOpenWo(workOrderDao.findAllWorkOrdersOnDurationByStatusBusiness(fromDate, toDate, WorkOrderStatus.OPEN, AuthenticationUtil.getCurrentUser().getBusiness().getId()));
			dto.setNextWeekClosedWo(workOrderDao.findAllWorkOrdersOnDurationByStatusBusiness(fromDate, toDate, WorkOrderStatus.CLOSED, AuthenticationUtil.getCurrentUser().getBusiness().getId()));
			findOnTimeCompletedWoWithCompltedWo(dto);
		} else if ( AuthenticationUtil.isAuthUserGeneralLevel() ) {
			dto.setNextWeekOpenWo(workOrderDao.findAllWorkOrdersOnDurationByStatusSite(fromDate, toDate, WorkOrderStatus.OPEN, AuthenticationUtil.getCurrentUser().getSite().getId()));
			dto.setNextWeekClosedWo(workOrderDao.findAllWorkOrdersOnDurationByStatusSite(fromDate, toDate, WorkOrderStatus.CLOSED, AuthenticationUtil.getCurrentUser().getSite().getId()));
			findOnTimeCompletedWoWithCompltedWo(dto);
		} else {
			dto.setNextWeekOpenWo(0);
			dto.setNextWeekClosedWo(0);

			dto.setAllCompletedWo(0);
			dto.setAllOnTimeCompletedWo(0);
		}		
		
	}
	
	
	private void findOnTimeCompletedWoWithCompltedWo(WorkOrderComparisonChartDataDTO dto){

		dto.setAllCompletedWo((int) workOrderDao.count(getCompltedWo()));
		dto.setAllOnTimeCompletedWo((int) workOrderDao.count(getOnTimeCompletedWo()));
	}
	
	private Specification<WorkOrder> getCompltedWo(){
		Specification<WorkOrder> specification;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> {
				return getAllCompletedWoPredicate(root, cb);
			};
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
				Predicate predicate2 = getAllCompletedWoPredicate(root, cb);
				return cb.and(predicate, predicate2);
			};
		} else {
			specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
				Predicate predicate2 = getAllCompletedWoPredicate( root, cb);
				return cb.and(predicate, predicate2);
			};
			
		}
		return specification;
	}
	private Specification<WorkOrder> getOnTimeCompletedWo(){
		Specification<WorkOrder> specification;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> {
				return getAllOnTimeCompletedWoPredicate(root, cb);
			};
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
				Predicate predicate2 = getAllOnTimeCompletedWoPredicate(root, cb);
				return cb.and(predicate, predicate2);
			};
		} else {
			specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
				Predicate predicate2 = getAllOnTimeCompletedWoPredicate( root, cb);
				return cb.and(predicate, predicate2);
			};
			
		}
		return specification;
	}
	private Predicate getAllCompletedWoPredicate(Root<WorkOrder> root, CriteriaBuilder cb) {
		return cb.equal(root.get("currentStatus").get("workOrderStatus"), WorkOrderStatus.CLOSED);
	}
	
	private Predicate getAllOnTimeCompletedWoPredicate(Root<WorkOrder> root, CriteriaBuilder cb) {
		return cb.and(
				cb.equal(root.get("suggestedCompletionDate"), root.get("suggestedCompletionDate")),
				getAllCompletedWoPredicate(root,cb)
				);
	}
}
