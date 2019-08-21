package com.neolith.focus.result.maintenance;

import com.neolith.focus.dto.maintenance.task.TaskGroupDTO;
import com.neolith.focus.model.maintenance.task.TaskGroup;
import com.neolith.focus.result.BaseResult;

public class TaskGroupResult extends BaseResult<TaskGroup, TaskGroupDTO> {
	
    public TaskGroupResult(TaskGroup domain, TaskGroupDTO dto) {
        super(domain, dto);
    }
    
    @Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}
}
