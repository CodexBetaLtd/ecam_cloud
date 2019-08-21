package com.codex.ecam.controller.biz.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;
import com.codex.ecam.result.admin.BusinessClassificationResult;
import com.codex.ecam.service.biz.api.BusinessClassificationService;
import com.codex.ecam.service.biz.api.BusinessService;

import java.util.ArrayList;


@Controller
@RequestMapping(BusinessClassificationController.REQUEST_MAPPING_URL)
public class BusinessClassificationController {

	public static final String REQUEST_MAPPING_URL = "/businessClassification";

	@Autowired
	private BusinessClassificationService businessClassificationService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new BusinessClassificationDTO());
		return "admin/cmmssetting/lookuptable/businessclassification/add-view";
	}

	@RequestMapping(value = "/edit",method= RequestMethod.GET)
	public String findBusinessClassificationById(Integer id, Model model) {
		try {
			BusinessClassificationDTO businessClassificationDTO = businessClassificationService.findById(id);
			setCommonData(model, businessClassificationDTO);
			return "admin/cmmssetting/lookuptable/businessclassification/add-view";
		} catch (Exception e) {
			model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
			return "redirect:/cmmssettings/";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model) {

		BusinessClassificationResult result = businessClassificationService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, new BusinessClassificationDTO());

		return "admin/cmmssetting/lookuptable/businessclassification/add-view";
	}


	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("businessClassification") BusinessClassificationDTO businessClassificationDTO, Model model) throws Exception {

		BusinessClassificationResult result = businessClassificationService.save(businessClassificationDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, businessClassificationDTO);
		return "admin/cmmssetting/lookuptable/businessclassification/add-view";
	}

	private void setCommonData(Model model, BusinessClassificationDTO businessClassificationDTO) {
		model.addAttribute("businessClassification", businessClassificationDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
