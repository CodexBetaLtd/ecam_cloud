package com.codex.ecam.dao.maintenance;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTask;
import com.codex.ecam.repository.FocusDataTableRepository;

import javax.transaction.Transactional;


@Repository
public interface ScheduledMaintenanceTaskDao extends FocusDataTableRepository<ScheduledMaintenanceTask, Integer> {

//    @Modifying
//    @Transactional
//    @Query("delete from ScheduledMaintenanceNotification where scheduledMaintenance.id = :scheduledMaintenanceId")
//    void deleteByScheduledMaintenanceId(@Param("scheduledMaintenanceId") Integer scheduledMaintenanceId);

    @Modifying
    @Transactional
    @Query("delete from ScheduledMaintenanceTask where id = :smTaskId")
    void deleteTask(@Param("smTaskId") Integer smTaskId);


}
