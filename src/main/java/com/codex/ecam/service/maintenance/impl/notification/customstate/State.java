package com.codex.ecam.service.maintenance.impl.notification.customstate;

import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;

public interface State {

    void stateAction(WorkOrderDTO workOrderDTO);

}
