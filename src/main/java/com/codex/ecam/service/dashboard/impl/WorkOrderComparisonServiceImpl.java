package com.codex.ecam.service.dashboard.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.codex.ecam.util.DateUtil;

@Service
public class WorkOrderComparisonServiceImpl implements WorkOrderComparisonService {

	@Autowired
	private WorkOrderDao workOrderDao;

	@Override
	public WorkOrderComparisonChartDataDTO getWoComparisonChartData() {
		final WorkOrderComparisonChartDataDTO dto = new WorkOrderComparisonChartDataDTO();
		setPreviousWeekWoData(dto);
		setCurrentWeekWoData(dto);
		setNextWeekWoData(dto);
		findOnTimeCompletedWoWithCompltedWo(dto);
		return dto;
	}

	private void setPreviousWeekWoData(WorkOrderComparisonChartDataDTO dto) {

		final Calendar cal2 = DateUtil.getDayStartCalandar(null);
		cal2.setFirstDayOfWeek(Calendar.MONDAY);
		cal2.set(Calendar.DAY_OF_WEEK, cal2.getFirstDayOfWeek());
		cal2.add(Calendar.WEEK_OF_YEAR, -1);
		final Date fromDate = cal2.getTime();

		final Calendar cal = DateUtil.getDayEndCalandar(null);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		cal.add(Calendar.DAY_OF_YEAR, -1);
		final Date toDate = cal.getTime();

		dto.setPreviousWeekOpenWo( getCountOnRangeByStatus(fromDate, toDate, WorkOrderStatus.OPEN) );
		dto.setPreviousWeekClosedWo( getCountOnRangeByStatus(fromDate, toDate, WorkOrderStatus.CLOSED) );
	}

	private void setCurrentWeekWoData(WorkOrderComparisonChartDataDTO dto) {
		final Calendar cal = DateUtil.getDayStartCalandar(null);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		final Date fromDate = cal.getTime();

		final Calendar cal2 = DateUtil.getDayEndCalandar(null);
		cal2.setFirstDayOfWeek(Calendar.MONDAY);
		cal2.set(Calendar.DAY_OF_WEEK, cal2.getFirstDayOfWeek());
		cal2.add(Calendar.WEEK_OF_YEAR, 1);
		cal2.add(Calendar.DAY_OF_YEAR, -1);
		final Date toDate = cal2.getTime();

		dto.setCurrentWeekOpenWo( getCountOnRangeByStatus(fromDate, toDate, WorkOrderStatus.OPEN) );
		dto.setCurrentWeekClosedWo( getCountOnRangeByStatus(fromDate, toDate, WorkOrderStatus.CLOSED) );
	}

	private void setNextWeekWoData(WorkOrderComparisonChartDataDTO dto) {
		final Calendar cal =  DateUtil.getDayStartCalandar(null);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		final Date fromDate = cal.getTime();

		final Calendar cal2 = DateUtil.getDayEndCalandar(null);
		cal2.setFirstDayOfWeek(Calendar.MONDAY);
		cal2.set(Calendar.DAY_OF_WEEK, cal2.getFirstDayOfWeek());
		cal2.add(Calendar.WEEK_OF_YEAR, 2);
		cal2.add(Calendar.DAY_OF_YEAR, -1);
		final Date toDate = cal2.getTime();

		dto.setNextWeekOpenWo( getCountOnRangeByStatus(fromDate, toDate, WorkOrderStatus.OPEN) );
		dto.setNextWeekClosedWo( getCountOnRangeByStatus(fromDate, toDate, WorkOrderStatus.CLOSED) );

	}

	private Integer getCountOnRangeByStatus(Date fromDate, Date toDate, WorkOrderStatus status) {
		Integer count = 0;

		if ( AuthenticationUtil.isAuthUserAdminLevel() ) {
			count = workOrderDao.findAllWorkOrdersOnDurationByStatus(fromDate, toDate, status);
		} else if ( AuthenticationUtil.isAuthUserSystemLevel() ) {
			count = workOrderDao.findAllWorkOrdersOnDurationByStatusBusiness(fromDate, toDate, status, AuthenticationUtil.getCurrentUser().getBusiness().getId());
		} else if ( AuthenticationUtil.isAuthUserGeneralLevel() ) {
			count = workOrderDao.findAllWorkOrdersOnDurationByStatusSite(fromDate, toDate, status, AuthenticationUtil.getCurrentUser().getSite().getId());
		}

		return count;
	}

	private void findOnTimeCompletedWoWithCompltedWo(WorkOrderComparisonChartDataDTO dto){

		dto.setAllCompletedWo((int) workOrderDao.count(getCompltedWo()));
		dto.setAllOnTimeCompletedWo((int) workOrderDao.count(getOnTimeCompletedWo()));
	}

	private Specification<WorkOrder> getCompltedWo(){
		final Specification<WorkOrder> specification = (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();

			predicates.add(getAllCompletedWoPredicate(root, cb));

			if (AuthenticationUtil.isAuthUserSystemLevel()) {
				predicates.add(cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()));
			}
			else if (AuthenticationUtil.isAuthUserGeneralLevel()) {
				predicates.add(cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return specification;
	}

	private Specification<WorkOrder> getOnTimeCompletedWo(){
		final Specification<WorkOrder> specification = (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();

			predicates.add(getAllOnTimeCompletedWoPredicate(root, cb));

			if (AuthenticationUtil.isAuthUserSystemLevel()) {
				predicates.add(cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()));
			}
			else if (AuthenticationUtil.isAuthUserGeneralLevel()) {
				predicates.add(cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};

		return specification;
	}

	private Predicate getAllCompletedWoPredicate(Root<WorkOrder> root, CriteriaBuilder cb) {
		return cb.equal(root.get("currentStatus").get("workOrderStatus"), WorkOrderStatus.CLOSED);
	}

	private Predicate getAllOnTimeCompletedWoPredicate(Root<WorkOrder> root, CriteriaBuilder cb) {
		return cb.and(
				cb.isNotNull(root.get("suggestedCompletionDate")),
				cb.isNotNull(root.get("dateCompleted")),
				cb.equal(root.get("suggestedCompletionDate"), root.get("dateCompleted")),
				getAllCompletedWoPredicate(root,cb)
				);
	}
}
