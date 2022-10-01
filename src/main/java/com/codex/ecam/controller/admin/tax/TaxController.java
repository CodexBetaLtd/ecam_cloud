package com.codex.ecam.controller.admin.tax;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.admin.cmmssetting.tax.TaxDTO;
import com.codex.ecam.result.admin.TaxResult;
import com.codex.ecam.service.admin.api.TaxService;
import com.codex.ecam.service.biz.api.BusinessService;

@Controller
@RequestMapping(TaxController.REQUEST_MAPPING_URL)
public class TaxController {

	public static final String REQUEST_MAPPING_URL = "/tax";

	@Autowired
	private TaxService taxService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "admin/cmmssetting/tax/home-view";
	}

	@RequestMapping(value = "/value-add-modal-view", method = RequestMethod.GET)
	public String getUserView(Model model) {
		return "admin/cmmssetting/tax/modal/value-add-modal";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success,
			@ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "admin/cmmssetting/tax/home-view";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, new TaxDTO());
			return "admin/cmmssetting/tax/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
			return "redirect:/tax/index";
		}
	}

	@RequestMapping(value = "/edit", method = { RequestMethod.POST, RequestMethod.GET })
	public String editTax(Integer id, Model model, RedirectAttributes ra) {
		try {
			final TaxDTO taxDTO = taxService.findById(id);
			setCommonData(model, taxDTO);
			return "admin/cmmssetting/tax/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/tax/index";
		}
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.GET })
	public String deleteTax(Integer id, Model model) {

		final TaxResult result = taxService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		return "admin/cmmssetting/tax/home-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("tax") @Valid TaxDTO taxDTO, Model model, String module) {

		TaxResult result = null;
		try {
			result = taxService.save(taxDTO);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, taxDTO);

		return "admin/cmmssetting/tax/add-view";
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final TaxResult result = taxService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Tax already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/cmmssetting/tax/home-view";
	}

	private void setCommonData(Model model, TaxDTO dto) {
		model.addAttribute("tax", dto);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
