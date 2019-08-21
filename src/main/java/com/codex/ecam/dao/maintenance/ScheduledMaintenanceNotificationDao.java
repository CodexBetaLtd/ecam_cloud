package com.codex.ecam.dao.maintenance;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceNotification;

import javax.transaction.Transactional;


@Repository
public interface ScheduledMaintenanceNotificationDao extends DataTablesRepository<ScheduledMaintenanceNotification, Integer> {

    @Modifying
    @Transactional
    @Query("delete from ScheduledMaintenanceNotification where scheduledMaintenance.id = :scheduledMaintenanceId")
    void deleteByScheduledMaintenanceId(@Param("scheduledMaintenanceId") Integer scheduledMaintenanceId);

}
