package com.codex.ecam.controller.maintenance.scheduledmaintenance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codex.ecam.validation.maintenance.scheduledmaintenance.ScheduledMaintenanceCustomValidator;

@Controller
@RequestMapping(ScheduledMaintenanceValidationController.REQUEST_MAPPING_URL)
public class ScheduledMaintenanceValidationController {

	public static final String REQUEST_MAPPING_URL = "/validate/scheduledmaintenance";

	@Autowired
	private ScheduledMaintenanceCustomValidator validator;

	@RequestMapping(value = "/validate-by-code", method = RequestMethod.GET)
	public @ResponseBody Boolean assetCodeValidate(@RequestParam(value = "id") Integer id, @RequestParam(value = "code") String code) {
		return validator.validateDuplicateSmCode(id, code);
	}

}