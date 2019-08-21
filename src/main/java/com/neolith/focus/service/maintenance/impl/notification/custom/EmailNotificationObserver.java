package com.neolith.focus.service.maintenance.impl.notification.custom;


import com.neolith.focus.dto.maintenance.workOrder.WorkOrderDTO;

public interface EmailNotificationObserver {

    void update(WorkOrderDTO workOrderDTO);

}
