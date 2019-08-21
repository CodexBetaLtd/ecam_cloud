package com.codex.ecam.dao.maintenance;

import java.util.Date;
import java.util.List;
import java.util.Set;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenance;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceFile;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface ScheduledMaintenanceDao extends FocusDataTableRepository<ScheduledMaintenance, Integer> {

	ScheduledMaintenance findById(Integer id);

	@Query("select sm from ScheduledMaintenance as sm join sm.scheduledMaintenanceTriggers as smt join smt.ttNextCalenderEvent as nce "
			+ "where smt.triggerType = :type and nce.scheduledDate >= :startTime and nce.scheduledDate <= :endTime group by sm")
	Set<ScheduledMaintenance> findCurrentHourTriggeredSms( @Param("startTime") Date startTime, @Param("endTime") Date endTime,
			@Param("type") SMTriggerType type);

	@Query("from ScheduledMaintenance where code = :code and (:id is null or id != :id)")
	List<ScheduledMaintenance> findDuplicateByCodeAndId(@Param("id") Integer id, @Param("code") String code);
	
    @Query("from ScheduledMaintenanceFile where id = :id")
    ScheduledMaintenanceFile findByFileId(@Param("id") Integer id);

	@Query("from ScheduledMaintenance where id = ( select max(id) from ScheduledMaintenance where business.id = :businessId )")
	List<ScheduledMaintenance> findLastInsertSmByBusiness(@Param("businessId") Integer businessId);

}
