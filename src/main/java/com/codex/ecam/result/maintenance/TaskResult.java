package com.codex.ecam.result.maintenance;

import com.codex.ecam.dto.maintenance.task.TaskDTO;
import com.codex.ecam.model.maintenance.task.Task;
import com.codex.ecam.result.BaseResult;

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
