package com.codex.ecam.controller.admin.cmmssettings.businessclassification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
			final BusinessClassificationDTO businessClassificationDTO = businessClassificationService.findById(id);
			setCommonData(model, businessClassificationDTO);
			return "admin/cmmssetting/lookuptable/businessclassification/add-view";
		} catch (final Exception e) {
			model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
			return "redirect:/cmmssettings/";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model) {

		final BusinessClassificationResult result = businessClassificationService.delete(id);

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

		final BusinessClassificationResult result = businessClassificationService.save(businessClassificationDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, businessClassificationDTO);
		return "admin/cmmssetting/lookuptable/businessclassification/add-view";
	}
	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final BusinessClassificationResult result = businessClassificationService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Business Classification already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/cmmssetting/lookuptable/businessclassification/home-view";
	}

	private void setCommonData(Model model, BusinessClassificationDTO businessClassificationDTO) {
		model.addAttribute("businessClassification", businessClassificationDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
