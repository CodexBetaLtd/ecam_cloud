package com.codex.ecam.controller.admin.cmmssettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.admin.UserJobTitleDTO;
import com.codex.ecam.result.admin.UserJobTitleResult;
import com.codex.ecam.service.admin.api.UserJobTitleService;
import com.codex.ecam.service.biz.api.BusinessService;

import java.util.ArrayList;

@Controller
@RequestMapping(UserJobTitleController.REQUEST_MAPPING_URL)
public class UserJobTitleController {

	public static final String REQUEST_MAPPING_URL = "/userjobtitle";

	@Autowired
	private UserJobTitleService jobTitleService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		setCommonData(model, new UserJobTitleDTO());
		return "admin/cmmssetting/lookuptable/userjobtitle/add-view";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String findByIDAccount(Integer id, Model model, RedirectAttributes ra) {
		try {
			UserJobTitleDTO dto = jobTitleService.findById(id);
			setCommonData(model, dto);
			return "admin/cmmssetting/lookuptable/userjobtitle/add-view";
		} catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
            return "redirect:/cmmssettings/";
        }
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteAccount(Integer id, Model model, RedirectAttributes ra) {

		UserJobTitleResult result = jobTitleService.delete(id);

		if(result.getStatus().equals(ResultStatus.ERROR)){
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }
        setCommonData(model, new UserJobTitleDTO());
		return "admin/cmmssetting/lookuptable/userjobtitle/add-view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("userJobTitle") UserJobTitleDTO jobTitleDTO, Model model) throws Exception{
		save(jobTitleDTO, model); 
		return "admin/cmmssetting/lookuptable/userjobtitle/add-view";
	}

	@RequestMapping(value = "/jobTitlesave", method = RequestMethod.POST)
	public String saveOrUpdateJobTitel(@ModelAttribute("userJobTitle") UserJobTitleDTO jobTitleDTO, Model model) throws Exception{
		save(jobTitleDTO, model);
		return "admin/user/modal/userjobtitle/job-title-add-modal";
	}

	@RequestMapping(value = "/userjobTitledelete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteJobTitle(Integer id, Model model, RedirectAttributes ra) {

		UserJobTitleResult result = jobTitleService.delete(id);

		if(result.getStatus().equals(ResultStatus.ERROR)){
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, new UserJobTitleDTO());

		return "admin/user/modal/userjobtitle/job-title-select-modal";
	}

	private void save(UserJobTitleDTO jobTitleDTO, Model model) throws Exception {

		UserJobTitleResult result = jobTitleService.save(jobTitleDTO);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        } 

		setCommonData(model, jobTitleDTO);
    }

	private void setCommonData(Model model, UserJobTitleDTO jobTitleDTO) {
		model.addAttribute("userJobTitle", jobTitleDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}
}
