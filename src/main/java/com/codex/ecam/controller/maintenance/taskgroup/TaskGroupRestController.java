package com.codex.ecam.controller.maintenance.taskgroup;


import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.maintenance.task.TaskDTO;
import com.codex.ecam.dto.maintenance.task.TaskGroupDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.maintenance.api.TaskGroupService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(TaskGroupRestController.REQUEST_MAPPING_URL)
public class TaskGroupRestController {

    public static final String REQUEST_MAPPING_URL = "/restapi/taskGroup";

    @Autowired
    private TaskGroupService taskGroupService; 

    @RequestMapping(value = "/taskGroupHomeList", method = RequestMethod.GET)
    public DataTablesOutput<TaskGroupDTO> findAllTaskGroupHomeList(@Valid FocusDataTablesInput dataTablesInput) throws Exception {
        return taskGroupService.findAll(dataTablesInput);
    }
    
    @RequestMapping(value = "/tableData", method = RequestMethod.GET)
    public DataTablesOutput<TaskGroupDTO> findAllTaskGroupDataTable(FocusDataTablesInput input) throws Exception {
    	return taskGroupService.findAll(input);
    }
    
    @RequestMapping(value = "/tabledata-by-business", method = RequestMethod.GET)
    public DataTablesOutput<TaskGroupDTO> findTaskGroupByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception {
    	return taskGroupService.findTaskGroupByBusiness(input, bizId);
    }

    @RequestMapping(value = "/taskGroupList", method = RequestMethod.GET)
    public List<TaskGroupDTO> findAllTaskGroup() throws Exception {
        return taskGroupService.findAll();
    }
    
    @RequestMapping(value = "/tasks-by-task-group", method = RequestMethod.GET)
    public List<TaskDTO> findTasksByTaskGroup(@Valid Integer id) throws Exception {
    	return taskGroupService.findAllTasksByTaskGroup(id);
    }

    @RequestMapping(value = "/tabledata-tasks-by-task-group", method = RequestMethod.GET)
    public DataTablesOutput<TaskDTO> findTasksByTaskGroup(FocusDataTablesInput input, @Valid Integer id) throws Exception {
        return taskGroupService.findAllTasksByTaskGroup(input, id);
    }

    @RequestMapping(value = "/taskById", method = RequestMethod.GET)
    public TaskDTO findTaskByTaskId(@Valid Integer id) throws Exception {
    	return taskGroupService.findTasksById(id);
    }
    
    @RequestMapping(value = "/getTaskByAssetCategory", method = RequestMethod.GET)
    public List<TaskDTO> findTaskByAssetCategory(@Valid Integer assetId) throws Exception {
        return taskGroupService.findAllTasksByAssetCategory(assetId);
    }




}
