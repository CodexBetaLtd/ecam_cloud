package com.codex.ecam.controller.biz.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.controller.admin.AdminBaseController;
import com.codex.ecam.dto.biz.business.BusinessDTO;
import com.codex.ecam.mappers.admin.BusinessMapper;
import com.codex.ecam.result.admin.BusinessResult;
import com.codex.ecam.result.admin.UserResult;
import com.codex.ecam.service.admin.api.CountryService;
import com.codex.ecam.service.admin.api.CurrencyService;
import com.codex.ecam.service.biz.api.BusinessClassificationService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.biz.api.BusinessTypeService;
import com.codex.ecam.util.AuthenticationUtil;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping(BusinessController.REQUEST_MAPPING_URL)
public class BusinessController extends AdminBaseController {

	public static final String REQUEST_MAPPING_URL = "/business";

	@Autowired
	private BusinessService businessService;

	@Autowired
	private BusinessClassificationService businessClassificationService;

	@Autowired
	private BusinessTypeService businessTypeService;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private CountryService countryService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		if (!AuthenticationUtil.isAuthUserAdminLevel()) {
			setCommonData(model, BusinessMapper.getInstance().domainToDto(AuthenticationUtil.getLoginUserBusiness()));
		}
		return "biz/business/home-view";
	}


	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) throws Exception {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		if (!AuthenticationUtil.isAuthUserAdminLevel()) {
			setCommonData(model, BusinessMapper.getInstance().domainToDto(AuthenticationUtil.getLoginUserBusiness()));
		}
		return "biz/business/home-view";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, new BusinessDTO());
			return "biz/business/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
			return "redirect:/business/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			final BusinessDTO business = businessService.findById(id);
			setCommonData(model, business);
			return "biz/business/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/business/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {

		final BusinessResult result = businessService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
		return "redirect:/business/index";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("business") @Valid BusinessDTO business, Model model, RedirectAttributes ra) throws Exception {

		final BusinessResult result = businessService.save(business);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, business);
		return "biz/business/add-view";
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final BusinessResult result = businessService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Business already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "biz/business/home-view";
	}

	private void setCommonData(Model model, BusinessDTO business) {
		model.addAttribute("business", business);
		model.addAttribute("businessClassifications", businessClassificationService.findAll());
		model.addAttribute("businessTypes", businessTypeService.findAll());
		model.addAttribute("currencyList", currencyService.findAll());
		model.addAttribute("countryList", countryService.findAll());
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}
}
