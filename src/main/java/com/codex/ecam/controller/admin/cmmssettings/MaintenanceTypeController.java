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
import com.codex.ecam.dto.admin.MaintenanceTypeDTO;
import com.codex.ecam.result.admin.MaintenanceTypeResult;
import com.codex.ecam.service.admin.api.MaintenanceTypeService;
import com.codex.ecam.service.biz.api.BusinessService;

import java.util.ArrayList;

@Controller
@RequestMapping(MaintenanceTypeController.REQUEST_MAPPING_URL)
public class MaintenanceTypeController {

	public static final String REQUEST_MAPPING_URL = "/maintenanceType";

	@Autowired
	private MaintenanceTypeService maintenanceTypeService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new MaintenanceTypeDTO());
		return "admin/cmmssetting/lookuptable/maintenancetype/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String findByIDmaintenancetype(Integer id, Model model) {
		try {
			final MaintenanceTypeDTO maintenanceTypesDTO = maintenanceTypeService.findById(id);
			setCommonData(model, maintenanceTypesDTO);
			return "admin/cmmssetting/lookuptable/maintenancetype/add-view";
		} catch (final Exception e) {
			model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
			return "redirect:/cmmssettings/";
		}
	}


	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deletemaintenancetype(Integer id, Model model, RedirectAttributes ra) {

		final MaintenanceTypeResult result = maintenanceTypeService.delete(id);

		if(result.getStatus().equals(ResultStatus.ERROR)){
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, new MaintenanceTypeDTO());

		return "admin/cmmssetting/lookuptable/maintenancetype/add-view";
	}


	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("maintenancetype") MaintenanceTypeDTO maintenanceTypeDTO, Model model) throws Exception {

		final MaintenanceTypeResult result = maintenanceTypeService.save(maintenanceTypeDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, maintenanceTypeDTO);

		return "admin/cmmssetting/lookuptable/maintenancetype/add-view";
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final MaintenanceTypeResult result = maintenanceTypeService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Maintenance Type already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/cmmssetting/lookuptable/maintenancetype/home-view";
	}

	private void setCommonData(Model model, MaintenanceTypeDTO maintenanceTypeDTO) {
		model.addAttribute("maintenanceType", maintenanceTypeDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
