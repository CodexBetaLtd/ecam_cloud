package com.codex.ecam.controller.admin.cmmssettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.admin.CertificationDTO;
import com.codex.ecam.result.admin.CertificationResult;
import com.codex.ecam.service.admin.api.CertificationService;
import com.codex.ecam.service.biz.api.BusinessService;

import java.util.ArrayList;

@Controller
@RequestMapping(CertificationController.REQUEST_MAPPING_URL)
public class CertificationController {

	public static final String REQUEST_MAPPING_URL = "/certification";

	@Autowired
	private CertificationService certificationService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new CertificationDTO());
		return "admin/cmmssetting/lookuptable/certification/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String findByIDCertification(Integer id, Model model) {
		try {
			final CertificationDTO certificationDTO = certificationService.findById(id);
			setCommonData(model, certificationDTO);
			return "admin/cmmssetting/lookuptable/certification/add-view";
		} catch (final Exception e) {
			model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
			return "redirect:/cmmssettings/";
		}
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteCertification(Integer id, Model model, RedirectAttributes ra) {

		final CertificationResult result = certificationService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, new CertificationDTO());

		return "admin/cmmssetting/lookuptable/certification/add-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("certification") CertificationDTO certificationDTO, Model model, String module) throws Exception {

		final CertificationResult result = certificationService.save(certificationDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, certificationDTO);
		if(module != null && "user".equals(module)){
			return "admin/user/modal/usercertificationtype/certification-type-add-modal";
		} else {
			return "admin/cmmssetting/lookuptable/certification/add-view";
		}
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final CertificationResult result = certificationService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Certification already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/cmmssetting/lookuptable/certification/home-view";
	}

	private void setCommonData(Model model, CertificationDTO certificationDTO) {
		model.addAttribute("certification", certificationDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
