package com.neolith.focus.controller.admin.cmmssettings;

import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.dto.admin.AccountDTO;
import com.neolith.focus.result.admin.AccountResult;
import com.neolith.focus.service.admin.api.AccountService;
import com.neolith.focus.service.biz.api.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			AccountDTO accountDTO = accountService.findById(id);
			setCommonData(model, accountDTO);
			return "admin/cmmssetting/lookuptable/account/add-view";
		} catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
            return "redirect:/cmmssettings/";
        }
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteAccount(Integer id, Model model, RedirectAttributes ra) {

		AccountResult result = accountService.delete(id);

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

		AccountResult result = accountService.save(accountDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, accountDTO);
		return "admin/cmmssetting/lookuptable/account/add-view";
	}

	private void setCommonData(Model model, AccountDTO accountDTO) {
		model.addAttribute("account", accountDTO);
        model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
    }
}
