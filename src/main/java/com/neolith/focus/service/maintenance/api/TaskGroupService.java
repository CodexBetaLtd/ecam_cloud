package com.neolith.focus.service.maintenance.api;

import java.util.List;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.maintenance.task.TaskDTO;
import com.neolith.focus.dto.maintenance.task.TaskGroupDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.maintenance.TaskGroupResult;

public interface TaskGroupService {

	TaskGroupResult newTaskGroup();

	TaskGroupResult findById(Integer id) throws Exception;

	TaskGroupResult delete(Integer id);

	TaskGroupResult save(TaskGroupDTO taskDTO);

	TaskDTO findTasksById(Integer id) throws Exception;

	List<TaskGroupDTO> findAll() throws Exception;

	List<TaskDTO> findAllTasksByTaskGroup(Integer id) throws Exception;

	DataTablesOutput<TaskGroupDTO> findAll(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<TaskDTO> findAllTasksByTaskGroup(FocusDataTablesInput input, Integer id) throws Exception;

	DataTablesOutput<TaskGroupDTO> findTaskGroupByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception;

}
