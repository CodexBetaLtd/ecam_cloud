package com.codex.ecam.controller.admin.cmmssettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.admin.PriorityDTO;
import com.codex.ecam.result.admin.PriorityResult;
import com.codex.ecam.service.admin.api.PriorityService;
import com.codex.ecam.service.biz.api.BusinessService;

import java.util.ArrayList;

@Controller
@RequestMapping(PriorityController.REQUEST_MAPPING_URL)
public class PriorityController {

	public static final String REQUEST_MAPPING_URL = "/priorities";

	@Autowired
	private PriorityService priorityService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new PriorityDTO());
		return "admin/cmmssetting/lookuptable/priorities/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String findByIDPriorities(Integer id, Model model) {
		try {
			PriorityDTO prioritiesDTO = priorityService.findById(id);
			setCommonData(model, prioritiesDTO);
			return "admin/cmmssetting/lookuptable/priorities/add-view";
		} catch (Exception e) {
            model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
            return "redirect:/cmmssettings/";
        }
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deletePriorities(Integer id, Model model, RedirectAttributes ra) {
		PriorityResult result = priorityService.delete(id);
		if(result.getStatus().equals(ResultStatus.ERROR)){
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }
        setCommonData(model, new PriorityDTO());
		return "admin/cmmssetting/lookuptable/priorities/add-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("priority") PriorityDTO priorityDTO, Model model) throws Exception {

		PriorityResult result = priorityService.save(priorityDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, priorityDTO);

		return "admin/cmmssetting/lookuptable/priorities/add-view";
	}

	private void setCommonData(Model model, PriorityDTO priorityDTO) {
		model.addAttribute("priority", priorityDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}
