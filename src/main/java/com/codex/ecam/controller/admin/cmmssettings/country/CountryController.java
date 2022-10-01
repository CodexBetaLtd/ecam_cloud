package com.codex.ecam.controller.admin.cmmssettings.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.admin.CountryDTO;
import com.codex.ecam.result.admin.CountryResult;
import com.codex.ecam.service.admin.api.CountryService;

import java.util.ArrayList;


@Controller
@RequestMapping(CountryController.REQUEST_MAPPING_URL)
public class CountryController {

	public static final String REQUEST_MAPPING_URL = "/country";

	@Autowired
	private CountryService countryService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new CountryDTO());
		return "admin/cmmssetting/lookuptable/country/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String findByIDCounrty(Integer id, Model model) {
		try {
			final CountryDTO countryDTO = countryService.findById(id);
			setCommonData(model, countryDTO);
			return "admin/cmmssetting/lookuptable/country/add-view";
		} catch (final Exception e) {
			model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
			return "redirect:/cmmssettings/";
		}
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteCountry(Integer id, Model model, RedirectAttributes ra) {
		final CountryResult result = countryService.delete(id);
		if(result.getStatus().equals(ResultStatus.ERROR)){
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, new CountryDTO());
		return "admin/cmmssetting/lookuptable/country/add-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("country") CountryDTO countryDTO, Model model) throws Exception {
		final CountryResult result = countryService.save(countryDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, countryDTO);
		return "admin/cmmssetting/lookuptable/country/add-view";
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final CountryResult result = countryService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Country already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/cmmssetting/lookuptable/country/home-view";
	}

	private void setCommonData(Model model, CountryDTO countryDTO) {
		model.addAttribute("country", countryDTO);
	}

}
