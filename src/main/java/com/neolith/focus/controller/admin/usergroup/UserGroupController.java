package com.neolith.focus.controller.admin.usergroup;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.neolith.focus.constants.Page;
import com.neolith.focus.constants.PagePermission;
import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.dto.admin.UserGroupDTO;
import com.neolith.focus.result.admin.UserGroupResult;
import com.neolith.focus.service.admin.api.UserGroupPageService;
import com.neolith.focus.service.admin.api.UserGroupService;
import com.neolith.focus.service.biz.api.BusinessService;

@Controller
@RequestMapping(UserGroupController.REQUEST_MAPPING_URL)
public class UserGroupController {

	public static final String REQUEST_MAPPING_URL = "/userGroups";

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private UserGroupPageService userGroupPageService;

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "admin/usergroups/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "admin/usergroups/home-view";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, new UserGroupDTO());
			return "admin/usergroups/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
			return "redirect:/userGroups/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			UserGroupDTO userGroup = userGroupService.findById(id);
			setCommonData(model, userGroup);
			return "admin/usergroups/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/userGroups/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {
		UserGroupResult result = userGroupService.delete(id);
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
		return "redirect:/userGroups/index";
	}

	@RequestMapping(value = "/pagepermission", method = RequestMethod.GET)
	public String showPagePermissionList(Model model, @RequestParam(name = "page") Page page, @RequestParam(name = "userGroupId", required = false ) Integer userGroupId) {
		model.addAttribute("permissionsList", PagePermission.getPagePermissionsByPage(page));
		model.addAttribute("pagePermissions", userGroupPageService.findPagePermissionByUserGroupAndPage(page, userGroupId));

		return "admin/usergroups/page-permission-list";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("userGroup") @Valid UserGroupDTO userGroup, Model model, RedirectAttributes ra) throws Exception {

		UserGroupResult result = userGroupService.save(userGroup);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			userGroup = userGroupService.findById(userGroup.getId());
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, userGroup);
		return "admin/usergroups/add-view";
	}

	private void setCommonData(Model model, UserGroupDTO userGroup) {
		model.addAttribute("checkBoxList", userGroupService.getMenuPermissions());
		model.addAttribute("pageList", userGroupService.findPageListByBusiness());
		model.addAttribute("userGroup", userGroup);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
	}

}