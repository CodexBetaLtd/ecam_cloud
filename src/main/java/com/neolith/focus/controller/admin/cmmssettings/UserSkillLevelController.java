package com.neolith.focus.controller.admin.cmmssettings;

import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.dto.admin.UserSkillLevelDTO;
import com.neolith.focus.result.admin.UserSkillLevelResult;
import com.neolith.focus.service.admin.api.UserSkillLevelService;
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
@RequestMapping(UserSkillLevelController.REQUEST_MAPPING_URL)
public class UserSkillLevelController {

	public static final String REQUEST_MAPPING_URL = "/userskilllevel";

	@Autowired
	private UserSkillLevelService userSkillLevelService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new UserSkillLevelDTO());
		return "admin/cmmssetting/lookuptable/userskilllevel/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String findByidUserSkillLevel(Integer id, Model model, RedirectAttributes ra) {
		try {
			UserSkillLevelDTO userSkillLevelDTO = userSkillLevelService.findById(id);
			setCommonData(model, userSkillLevelDTO);
			return "admin/cmmssetting/lookuptable/userskilllevel/add-view";
		} catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
            return "redirect:/cmmssettings/";
        }
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteAccount(Integer id, Model model, RedirectAttributes ra) {

		UserSkillLevelResult result = userSkillLevelService.delete(id);

		if(result.getStatus().equals(ResultStatus.ERROR)){
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, new UserSkillLevelDTO());
		return "admin/cmmssetting/lookuptable/userskilllevel/add-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("userSkillLevel") UserSkillLevelDTO dto, Model model) throws Exception { 
		save(dto, model);
		return "admin/cmmssetting/lookuptable/userskilllevel/add-view";
	}
	
	@RequestMapping(value = "/user-skill-save", method = RequestMethod.POST)
	public String userskillsave(@ModelAttribute("userSkillLevel") UserSkillLevelDTO dto, Model model) throws Exception { 
		save(dto, model);
		return "admin/user/modal/userskilllevel/user-skill-level-add-modal";
	}

	private void save(UserSkillLevelDTO dto, Model model) throws Exception {
		UserSkillLevelResult result = userSkillLevelService.save(dto);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, dto);
	}

	@RequestMapping(value = "/skillleveldelete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteSkillLevel(Integer id, Model model, RedirectAttributes ra) {
		UserSkillLevelResult result = userSkillLevelService.delete(id);
		if(result.getStatus().equals(ResultStatus.ERROR)){
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }
        setCommonData(model, new UserSkillLevelDTO());
		return "admin/user/modal/userskilllevel/user-skill-level-select-modal";
	}

	private void setCommonData(Model model, UserSkillLevelDTO dto) {
		model.addAttribute("userSkillLevel", dto);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}
}
