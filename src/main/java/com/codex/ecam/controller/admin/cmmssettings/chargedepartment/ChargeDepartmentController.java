package com.codex.ecam.controller.admin.cmmssettings.chargedepartment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.admin.ChargeDepartmentDTO;
import com.codex.ecam.result.admin.ChargeDepartmentResult;
import com.codex.ecam.service.admin.api.ChargeDepartmentService;
import com.codex.ecam.service.biz.api.BusinessService;

import java.util.ArrayList;

@Controller
@RequestMapping(ChargeDepartmentController.REQUEST_MAPPING_URL)
public class ChargeDepartmentController {

	public static final String REQUEST_MAPPING_URL = "/chargeDepartment";

	@Autowired
	private ChargeDepartmentService chargeDeparmentService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new ChargeDepartmentDTO());
		return "admin/cmmssetting/lookuptable/chargedepartments/add-view";
	}

	@RequestMapping(value = "/edit",method= RequestMethod.GET)
	public  String  findByIdAccount(Integer id,Model model) {
		try {
			final ChargeDepartmentDTO chargeDepartmentDTO = chargeDeparmentService.findById(id);
			setCommonData(model, chargeDepartmentDTO);
			return "admin/cmmssetting/lookuptable/chargedepartments/add-view";
		} catch (final Exception e) {
			model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
			return "redirect:/cmmssettings/";
		}
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
	public String deleteAccount(Integer id, Model model, RedirectAttributes ra) {
		final ChargeDepartmentResult result = chargeDeparmentService.delete(id);
		if(result.getStatus().equals(ResultStatus.ERROR)){
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, new ChargeDepartmentDTO());
		return "admin/cmmssetting/lookuptable/chargedepartments/add-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("chargeDepartment") ChargeDepartmentDTO chargeDepartmentDTO, Model model) throws Exception {

		final ChargeDepartmentResult result = chargeDeparmentService.save(chargeDepartmentDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, chargeDepartmentDTO);

		return "admin/cmmssetting/lookuptable/chargedepartments/add-view";

	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final ChargeDepartmentResult result = chargeDeparmentService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Charge Department already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/cmmssetting/lookuptable/chargeDepartment/home-view";
	}

	private void setCommonData(Model model, ChargeDepartmentDTO chargeDepartmentDTO) {
		model.addAttribute("chargeDepartment", chargeDepartmentDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}
}
