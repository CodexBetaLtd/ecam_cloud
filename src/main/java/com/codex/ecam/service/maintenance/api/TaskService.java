package com.codex.ecam.service.maintenance.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.maintenance.task.TaskDTO;
import com.codex.ecam.result.maintenance.TaskResult;

public interface TaskService {

    TaskDTO findById(Integer id) throws Exception;

    TaskResult delete(Integer id);

    TaskResult save(TaskDTO taskDTO);

    TaskResult update(TaskDTO taskDTO);

    DataTablesOutput<TaskDTO> findAll(DataTablesInput input) throws Exception;

}
