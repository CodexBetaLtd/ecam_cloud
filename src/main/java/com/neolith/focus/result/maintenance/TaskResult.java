package com.neolith.focus.result.maintenance;

import com.neolith.focus.dto.maintenance.task.TaskDTO;
import com.neolith.focus.model.maintenance.task.Task;
import com.neolith.focus.result.BaseResult;

public class TaskResult extends BaseResult<Task, TaskDTO> {
	
    public TaskResult(Task domain, TaskDTO dto) {
        super(domain, dto);
    }
    
    @Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}
}
