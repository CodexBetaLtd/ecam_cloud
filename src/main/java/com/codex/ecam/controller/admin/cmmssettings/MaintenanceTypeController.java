package com.codex.ecam.controller.admin.cmmssettings;

import org.springframework.beans.factory.annotation.Autowired;
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
			MaintenanceTypeDTO maintenanceTypesDTO = maintenanceTypeService.findById(id);
			setCommonData(model, maintenanceTypesDTO);
			return "admin/cmmssetting/lookuptable/maintenancetype/add-view";
		} catch (Exception e) {
            model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
            return "redirect:/cmmssettings/";
        }
	}


	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deletemaintenancetype(Integer id, Model model, RedirectAttributes ra) {

		MaintenanceTypeResult result = maintenanceTypeService.delete(id);

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

		MaintenanceTypeResult result = maintenanceTypeService.save(maintenanceTypeDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, maintenanceTypeDTO);

		return "admin/cmmssetting/lookuptable/maintenancetype/add-view";
	}

	private void setCommonData(Model model, MaintenanceTypeDTO maintenanceTypeDTO) {
		model.addAttribute("maintenanceType", maintenanceTypeDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
