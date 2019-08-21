package com.codex.ecam.controller.maintenance.miscellaneousExpense;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.maintenance.workOrder.MiscellaneousExpenseDTO;
import com.codex.ecam.service.maintenance.api.MiscellaneousExpenseService;

@RestController
@RequestMapping(MiscellaneousExpenseRestController.REQUEST_MAPPING_URL)
public class MiscellaneousExpenseRestController {

	public static final String REQUEST_MAPPING_URL = "/restapi/miscellaneousExpense";

	@Autowired
	private MiscellaneousExpenseService miscellaneousExpenseService;

	@RequestMapping(value = "/findMiscellaneousExpense", method = RequestMethod.GET)
	public MiscellaneousExpenseDTO getMiscellaneousExpense(Model model, @Valid Integer id) {
		try {
			return miscellaneousExpenseService.findById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new MiscellaneousExpenseDTO();
		}
	}

}
