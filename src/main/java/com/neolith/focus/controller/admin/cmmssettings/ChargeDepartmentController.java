package com.neolith.focus.controller.admin.cmmssettings;

import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.dto.admin.ChargeDepartmentDTO;
import com.neolith.focus.result.admin.ChargeDepartmentResult;
import com.neolith.focus.service.admin.api.ChargeDepartmentService;
import com.neolith.focus.service.biz.api.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			ChargeDepartmentDTO chargeDepartmentDTO = chargeDeparmentService.findById(id);
			setCommonData(model, chargeDepartmentDTO);
			return "admin/cmmssetting/lookuptable/chargedepartments/add-view";
		} catch (Exception e) {
            model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
            return "redirect:/cmmssettings/";
        }
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET})
	public String deleteAccount(Integer id, Model model, RedirectAttributes ra) {
		ChargeDepartmentResult result = chargeDeparmentService.delete(id);
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

		ChargeDepartmentResult result = chargeDeparmentService.save(chargeDepartmentDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, chargeDepartmentDTO);

		return "admin/cmmssetting/lookuptable/chargedepartments/add-view";

	}

	private void setCommonData(Model model, ChargeDepartmentDTO chargeDepartmentDTO) {
		model.addAttribute("chargeDepartment", chargeDepartmentDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}
}
