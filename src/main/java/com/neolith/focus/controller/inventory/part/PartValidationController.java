package com.neolith.focus.controller.inventory.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neolith.focus.validation.inventory.part.PartCustomValidator;

@Controller
@RequestMapping(PartValidationController.REQUEST_MAPPING_URL)
public class PartValidationController {

	public static final String REQUEST_MAPPING_URL = "/validate/part";

	@Autowired
	private PartCustomValidator validator;

	@RequestMapping(value = "/validate-by-code", method = RequestMethod.GET)
	public @ResponseBody Boolean assetCodeValidate(@RequestParam(value = "id") Integer id, @RequestParam(value = "code") String code) {
		return validator.validateDuplicateCode(id, code);
	}

}