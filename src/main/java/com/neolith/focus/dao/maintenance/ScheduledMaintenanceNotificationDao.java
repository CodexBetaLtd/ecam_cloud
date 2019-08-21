package com.neolith.focus.dao.maintenance;

import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenanceNotification;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface ScheduledMaintenanceNotificationDao extends DataTablesRepository<ScheduledMaintenanceNotification, Integer> {

    @Modifying
    @Transactional
    @Query("delete from ScheduledMaintenanceNotification where scheduledMaintenance.id = :scheduledMaintenanceId")
    void deleteByScheduledMaintenanceId(@Param("scheduledMaintenanceId") Integer scheduledMaintenanceId);

}
