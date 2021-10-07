package com.codex.ecam.controller.admin.cmmssettings.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.admin.AccountDTO;
import com.codex.ecam.result.admin.AccountResult;
import com.codex.ecam.service.admin.api.AccountService;
import com.codex.ecam.service.biz.api.BusinessService;

import java.util.ArrayList;

@Controller
@RequestMapping(AccountController.REQUEST_MAPPING_URL)
public class AccountController {

	public static final String REQUEST_MAPPING_URL = "/account";

	@Autowired
	private AccountService accountService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new AccountDTO());
		return "admin/cmmssetting/lookuptable/account/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String findByIDAccount(Integer id, Model model, RedirectAttributes ra) {
		try {
			final AccountDTO accountDTO = accountService.findById(id);
			setCommonData(model, accountDTO);
			return "admin/cmmssetting/lookuptable/account/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/cmmssettings/";
		}
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteAccount(Integer id, Model model, RedirectAttributes ra) {

		final AccountResult result = accountService.delete(id);

		if(result.getStatus().equals(ResultStatus.ERROR)){
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, new AccountDTO());
		return "admin/cmmssetting/lookuptable/account/add-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("account") AccountDTO accountDTO, Model model) throws Exception {

		final AccountResult result = accountService.save(accountDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, accountDTO);
		return "admin/cmmssetting/lookuptable/account/add-view";
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final AccountResult result = accountService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Account already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "admin/cmmssetting/lookuptable/account/home-view";
	}

	private void setCommonData(Model model, AccountDTO accountDTO) {
		model.addAttribute("account", accountDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}
}
