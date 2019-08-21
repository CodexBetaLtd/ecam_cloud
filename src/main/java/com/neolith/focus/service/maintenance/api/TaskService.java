package com.neolith.focus.service.maintenance.api;

import com.neolith.focus.dto.maintenance.task.TaskDTO;
import com.neolith.focus.result.maintenance.TaskResult;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface TaskService {

    TaskDTO findById(Integer id) throws Exception;

    TaskResult delete(Integer id);

    TaskResult save(TaskDTO taskDTO);

    TaskResult update(TaskDTO taskDTO);

    DataTablesOutput<TaskDTO> findAll(DataTablesInput input) throws Exception;

}
