package com.neolith.focus.controller.maintenance.project;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neolith.focus.dto.maintenance.ProjectDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.maintenance.api.ProjectService;

@RestController
@RequestMapping(ProjectRestController.REQUEST_MAPPING_URL)
public class ProjectRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/project";

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<ProjectDTO> findAllProjects(@Valid FocusDataTablesInput input) throws Exception {
		try {
			return projectService.findAll(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getProjectByBusiness", method = RequestMethod.GET)
	public DataTablesOutput<ProjectDTO> findAllProjects(@Valid FocusDataTablesInput input, @Valid Integer id) throws Exception {
		try {
			return projectService.findProjectByBusiness(input, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
