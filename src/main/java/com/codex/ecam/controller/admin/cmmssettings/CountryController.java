package com.codex.ecam.controller.admin.cmmssettings;

import org.springframework.beans.factory.annotation.Autowired;
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
			CountryDTO countryDTO = countryService.findById(id);
			setCommonData(model, countryDTO);
			return "admin/cmmssetting/lookuptable/country/add-view";
		} catch (Exception e) {
            model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
            return "redirect:/cmmssettings/";
        }
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteCountry(Integer id, Model model, RedirectAttributes ra) {
		CountryResult result = countryService.delete(id);
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
		CountryResult result = countryService.save(countryDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, countryDTO);
		return "admin/cmmssetting/lookuptable/country/add-view";
	}

	private void setCommonData(Model model, CountryDTO countryDTO) {
		model.addAttribute("country", countryDTO);
	}

}
