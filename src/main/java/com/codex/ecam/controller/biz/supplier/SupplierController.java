package com.codex.ecam.controller.biz.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.biz.supplier.SupplierDTO;
import com.codex.ecam.result.biz.SupplierResult;
import com.codex.ecam.service.admin.api.CountryService;
import com.codex.ecam.service.admin.api.CurrencyService;
import com.codex.ecam.service.biz.api.BusinessClassificationService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.biz.api.BusinessTypeService;
import com.codex.ecam.service.biz.api.SupplierService;

import java.util.ArrayList;

import javax.validation.Valid;

@Controller
@RequestMapping(SupplierController.REQUEST_MAPPING_URL)
public class SupplierController {

	public static final String REQUEST_MAPPING_URL = "/supplier";

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private BusinessClassificationService businessClassificationService;

	@Autowired
	private BusinessTypeService businessTypeService;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "biz/supplier/home-view";
	}

	@RequestMapping(value = "/view/modal/currencies", method = RequestMethod.GET)
	public String getCurrencyModalView(Model model, @RequestParam(name = "title", defaultValue = "Currency(s)")String title) {
		model.addAttribute("title", title);
		return "general/table/currencies";
	}

	@RequestMapping(value = "/view/modal/business-classification", method = RequestMethod.GET)
	public String getBusinessClassificationModalView(Model model, @RequestParam(name = "title", defaultValue = "Business Classification(s)")String title) {
		model.addAttribute("title", title);
		return "general/table/business-classifications";
	}

	@RequestMapping(value = "/view/modal/countries", method = RequestMethod.GET)
	public String getCountryModalView(Model model, @RequestParam(name = "title", defaultValue = "Country(s)")String title) {
		model.addAttribute("title", title);
		return "general/table/countries";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model, RedirectAttributes ra) {
		SupplierResult result;
		try {
			result = supplierService.newSupplier();
			setCommonData(model, result.getDtoEntity());
			return "biz/supplier/add-view";
		} catch (final Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("error", "Error While Loading Initial Data.");
			return "redirect:/supplier/";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		SupplierResult result;
		try {
			result = supplierService.findById(id);
			setCommonData(model, result.getDtoEntity());
			return "biz/supplier/add-view";
		} catch (final Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("error", "Error occurred. Please Try again.");
			return "redirect:/supplier/";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("supplier") @Valid SupplierDTO supplierDTO, Model model, RedirectAttributes ra) {
		SupplierResult result;
		try {
			if (supplierDTO.getId() != null && supplierDTO.getId() > 0) {
				result = supplierService.update(supplierDTO);
			} else {
				result = supplierService.save(supplierDTO);
			}
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList());
			} else {
				model.addAttribute("success", result.getMsgList());
			}
			setCommonData(model, supplierDTO);
			return "biz/supplier/add-view";
		} catch (final Exception e) {
			e.printStackTrace();
			return "redirect:/supplier/add";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {
		SupplierResult result;
		try {
			result = supplierService.delete(id);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				ra.addFlashAttribute("error", result.getErrorList());
			} else {
				ra.addFlashAttribute("success", result.getMsgList());
			}
			return "redirect:/supplier/";
		} catch (final Exception e) {
			e.printStackTrace();
			return "redirect:/supplier/";
		}

	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final SupplierResult result = supplierService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Supplier already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "biz/supplier/home-view";
	}

	private void setCommonData(Model model, SupplierDTO supplierDTO) {
		model.addAttribute("supplier", supplierDTO);
		model.addAttribute("businessClassifications", businessClassificationService.findAll());
		model.addAttribute("businessTypes", businessTypeService.findAll());
		model.addAttribute("currencyList", currencyService.findAll());
		model.addAttribute("countryList", countryService.findAll());
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		//		model.addAttribute("sites", assetService.findSiteByBusinessId(supplierDTO.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
	}


}
