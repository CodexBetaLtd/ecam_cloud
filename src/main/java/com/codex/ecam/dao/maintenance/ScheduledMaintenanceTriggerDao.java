package com.codex.ecam.dao.maintenance;

import java.util.Date;
import java.util.Set;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface ScheduledMaintenanceTriggerDao extends FocusDataTableRepository<ScheduledMaintenanceTrigger, Integer> {

	@Query("select smt from ScheduledMaintenanceTrigger as smt join smt.ttNextCalenderEvent as nce "
			+ "where smt.triggerType = :type and nce.scheduledDate >= :startTime and nce.scheduledDate <= :endTime")
	Set<ScheduledMaintenanceTrigger> findCurrentHourTriggers(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
			@Param("type") SMTriggerType type);

	@Query("select count(smt) from ScheduledMaintenanceTrigger as smt join smt.ttNextCalenderEvent as nce "
			+ "where nce.scheduledDate >= :from and nce.scheduledDate <= :to")
	Integer getSMTriggerCount(@Param("from")Date fromDate, @Param("to")Date toDate);
	
	@Query("select count(smt) from ScheduledMaintenanceTrigger as smt join smt.ttNextCalenderEvent as nce "
			+ "join smt.asset as asset join asset.business as business where nce.scheduledDate >= :from and "
			+ "nce.scheduledDate <= :to and business.id =:businessId")
	Integer getSMTriggerCountByBusiness( @Param("from") Date fromDate,  @Param("to") Date toDate,  @Param("businessId") Integer businessId);

	@Query("select count(smt) from ScheduledMaintenanceTrigger as smt join smt.ttNextCalenderEvent as nce "
			+ "join smt.asset as asset join asset.site as site where nce.scheduledDate >= :from and "
			+ "nce.scheduledDate <= :to and site.id =:siteId")
	Integer getSMTriggerCountBySite( @Param("from") Date fromDate,  @Param("to") Date toDate,  @Param("siteId") Integer siteId);

}
