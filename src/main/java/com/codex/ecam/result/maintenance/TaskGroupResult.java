package com.codex.ecam.result.maintenance;

import com.codex.ecam.dto.maintenance.task.TaskGroupDTO;
import com.codex.ecam.model.maintenance.task.TaskGroup;
import com.codex.ecam.result.BaseResult;

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
