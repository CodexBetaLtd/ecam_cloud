package com.codex.ecam.service.maintenance.impl.notification.custom;


import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;

public interface EmailNotificationObserver {

    void update(WorkOrderDTO workOrderDTO);

}
