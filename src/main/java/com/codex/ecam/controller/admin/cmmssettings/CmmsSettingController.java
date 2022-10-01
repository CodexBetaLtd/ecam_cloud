package com.codex.ecam.controller.admin.cmmssettings;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.admin.cmmssetting.UserSiteDTO;
import com.codex.ecam.result.admin.UserSiteResult;
import com.codex.ecam.service.admin.api.UserSiteService;
import com.codex.ecam.service.biz.api.BusinessService;

@Controller
@RequestMapping(CmmsSettingController.REQUEST_MAPPING_URL)
public class CmmsSettingController {

	public static final String REQUEST_MAPPING_URL = "/cmmssettings";

	@Autowired
	private UserSiteService userSiteService;

	@Autowired
	private BusinessService businessService;

	//todo: check availability
	@RequestMapping(value = "/assetcategory", method = RequestMethod.GET)
	public String assetCategory(Model model){
		model.addAttribute("success", new ArrayList<String>().add("success"));
		return "asset/assetcategory/home-view";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String cmmsSetting(Model model){
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		return "/admin/cmmssetting/cmms-settings";
	}


	//todo: check availability (Needed Or Not)
	@RequestMapping(value = "/site/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("userSiteDTO") @Valid UserSiteDTO userSiteDTO, Model model) {
		try {

			UserSiteResult result = userSiteService.save(userSiteDTO);

			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList());
			} else {
				userSiteDTO = userSiteService.findById(userSiteDTO.getSiteId());
				model.addAttribute("success", result.getMsgList());
			}

			return "/admin/cmmssetting/cmms-settings";
		} catch (Exception e) {
			model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
			return "admin/cmmssetting/site/userSiteModalBody";
		}

	}

	//todo: check availability (Needed Or Not)
	@RequestMapping(value = "/site/delete", method = RequestMethod.GET)
	public String deleteUserSite(Integer id, Model model) {
		try {
			userSiteService.deleteUserSiteById(id);
		} catch (Exception e) {
			model.addAttribute("error", new ArrayList<String>().add(e.getMessage()));
		}
		return "/admin/cmmssetting/cmms-settings";

	}



}
