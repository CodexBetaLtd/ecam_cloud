package com.neolith.focus.service.maintenance.impl.notification.customstate;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderDTO;

public interface State {

    void stateAction(WorkOrderDTO workOrderDTO);

}
