package com.neolith.focus.dto.maintenance.scheduledmaintenance;

import com.neolith.focus.dto.BaseDTO;

public class ScheduledMaintenanceTaskTriggerDTO extends BaseDTO {

    private Integer id;
    private Integer scheduledMaintenanceTaskId;
    private Integer scheduledMaintenanceTriggerId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScheduledMaintenanceTaskId() {
        return scheduledMaintenanceTaskId;
    }

    public void setScheduledMaintenanceTaskId(Integer scheduledMaintenanceTaskId) {
        this.scheduledMaintenanceTaskId = scheduledMaintenanceTaskId;
    }

    public Integer getScheduledMaintenanceTriggerId() {
        return scheduledMaintenanceTriggerId;
    }

    public void setScheduledMaintenanceTriggerId(Integer scheduledMaintenanceTriggerId) {
        this.scheduledMaintenanceTriggerId = scheduledMaintenanceTriggerId;
    }
}
