package com.neolith.focus.service.maintenance.impl.notification.customstate;

import com.neolith.focus.dto.maintenance.workOrder.WorkOrderDTO;

public class WorkOrderContext {


    private State state = null;
    private WorkOrderDTO workOrderDTO = new WorkOrderDTO();

    public WorkOrderContext(WorkOrderDTO workOrderDTO) {
        this.workOrderDTO = workOrderDTO;
    }

    public void upDateStateAction() {
        state.stateAction(workOrderDTO);
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public WorkOrderDTO getWorkOrderDTO() {
        return workOrderDTO;
    }

    public void setWorkOrderDTO(WorkOrderDTO workOrderDTO) {
        this.workOrderDTO = workOrderDTO;
    }
}
