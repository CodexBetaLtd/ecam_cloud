package com.neolith.focus.controller.biz.business;

import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.controller.admin.AdminBaseController;
import com.neolith.focus.dto.biz.business.BusinessDTO;
import com.neolith.focus.mappers.admin.BusinessMapper;
import com.neolith.focus.result.admin.BusinessResult;
import com.neolith.focus.service.admin.api.CountryService;
import com.neolith.focus.service.admin.api.CurrencyService;
import com.neolith.focus.service.biz.api.BusinessClassificationService;
import com.neolith.focus.service.biz.api.BusinessService;
import com.neolith.focus.service.biz.api.BusinessTypeService;
import com.neolith.focus.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
			return "redirect:/business/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			BusinessDTO business = businessService.findById(id);
			setCommonData(model, business);
			return "biz/business/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/business/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {

		BusinessResult result = businessService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
		return "redirect:/business/index";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("business") @Valid BusinessDTO business, Model model, RedirectAttributes ra) throws Exception {

		BusinessResult result = businessService.save(business);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, business);
		return "biz/business/add-view";
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
