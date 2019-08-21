package com.codex.ecam.controller.biz.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.asset.CustomerDTO;
import com.codex.ecam.result.asset.CustomerResult;
import com.codex.ecam.service.admin.api.CountryService;
import com.codex.ecam.service.asset.api.CustomerService;
import com.codex.ecam.service.biz.api.BusinessService;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping(CustomerController.REQUEST_MAPPING_URL)
public class CustomerController {

	public static final String REQUEST_MAPPING_URL = "/customer";

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "biz/customer/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "biz/customer/home-view";
	}

	@RequestMapping(value = "/assetmodelview", method = RequestMethod.GET)
	public String modelAssetView(Model model) {
		return "biz/customer/modal/asset-select-modal";
	}

	@RequestMapping(value = "/contactaddmodelview", method = RequestMethod.GET)
	public String modelContactView(Model model) {
		return "biz/customer/modal/customer-contact-add-modal";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, new CustomerDTO());
			return "biz/customer/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/customer/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {

			CustomerDTO customerDTO = customerService.findById(id);
			setCommonData(model, customerDTO);
			return "biz/customer/add-view";
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));
			return "redirect:/customer/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {

		CustomerResult result = customerService.delete(id);
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
		return "redirect:/customer/index";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("customer") @Valid CustomerDTO customer, Model model) throws Exception {

		CustomerResult result = customerService.save(customer);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			customer = customerService.findById(customer.getId());
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, customer);
		return "biz/customer/add-view";
	}

	private void setCommonData(Model model, CustomerDTO customer) {
		model.addAttribute("customer", customer);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("countries", countryService.findAll());
	}
}
