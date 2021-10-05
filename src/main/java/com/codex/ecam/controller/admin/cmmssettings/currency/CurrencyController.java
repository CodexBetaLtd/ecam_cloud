package com.codex.ecam.controller.admin.cmmssettings.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.admin.CurrencyDTO;
import com.codex.ecam.result.admin.CurrencyResult;
import com.codex.ecam.service.admin.api.CurrencyService;

import java.util.ArrayList;


@Controller
@RequestMapping(CurrencyController.REQUEST_MAPPING_URL)
public class CurrencyController {

	public static final String REQUEST_MAPPING_URL = "/currency";

	@Autowired
	private CurrencyService currencyService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new CurrencyDTO());
		return "admin/cmmssetting/lookuptable/currency/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String findByIDCurreancy(Integer id, Model model) {
		try {
			final CurrencyDTO currencyDTO = currencyService.findById(id);
			setCommonData(model, currencyDTO);
			return "admin/cmmssetting/lookuptable/currency/add-view";
		} catch (final Exception e) {
			model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
			return "redirect:/cmmssettings/";
		}
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteCurrency(Integer id, Model model, RedirectAttributes ra) {
		final CurrencyResult result = currencyService.delete(id);
		if(result.getStatus().equals(ResultStatus.ERROR)){
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, new CurrencyDTO());

		return "admin/cmmssetting/lookuptable/currency/add-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("currency") CurrencyDTO currencyDTO, Model model) throws Exception {

		final CurrencyResult result = currencyService.save(currencyDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, currencyDTO);

		return "admin/cmmssetting/lookuptable/currency/add-view";
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final CurrencyResult result = currencyService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Currency already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/cmmssetting/lookuptable/currency/home-view";
	}

	private void setCommonData(Model model, CurrencyDTO currencyDTO) {
		model.addAttribute("currency", currencyDTO);
	}

}
