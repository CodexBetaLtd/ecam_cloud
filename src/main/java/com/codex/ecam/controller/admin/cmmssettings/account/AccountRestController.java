package com.codex.ecam.controller.admin.cmmssettings.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.AccountDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.AccountService;

import javax.validation.Valid;

@RestController
@RequestMapping(AccountRestController.REQUEST_MAPPING_URL)
public class AccountRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/account";

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<AccountDTO> getAll(@Valid FocusDataTablesInput input){
		try {
			return accountService.findAll(input);
		} catch (final Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<AccountDTO>();
		}
	}

}
