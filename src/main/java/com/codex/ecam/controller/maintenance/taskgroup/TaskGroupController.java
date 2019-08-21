package com.codex.ecam.controller.maintenance.taskgroup;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.TaskType;
import com.codex.ecam.dto.maintenance.task.TaskGroupDTO;
import com.codex.ecam.result.maintenance.TaskGroupResult;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.maintenance.api.TaskGroupService;

@Controller
@RequestMapping(TaskGroupController.REQUEST_MAPPING_URL)
public class TaskGroupController {

	public static final String REQUEST_MAPPING_URL = "/taskGroup";

	@Autowired
	private TaskGroupService taskGroupService;
	@Autowired
	private BusinessService businessService;
	@Autowired
	private AssetService assetService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "maintenance/taskgroup/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "maintenance/taskgroup/home-view";
	}

	@RequestMapping(value = "/task-add-modal-view", method = RequestMethod.GET)
	public String getTaskView(Model model) {
		model.addAttribute("taskTypes", TaskType.getTaskTypes());
		return "maintenance/taskgroup/modals/task-add-modal";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) {
		try {
			TaskGroupResult result = taskGroupService.newTaskGroup();
			setCommonData(model, result.getDtoEntity());
			return "maintenance/taskgroup/add-view";
		} catch (Exception ex) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/taskGroup/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Integer id, Model model, RedirectAttributes ra) {
		try {
			TaskGroupResult result = taskGroupService.findById(id);
			setCommonData(model, result.getDtoEntity());
			return "maintenance/taskgroup/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error Occurred. Please Try again."));
			return "redirect:/taskGroup/index";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("taskGroup") @Valid TaskGroupDTO dto, Model model, RedirectAttributes ra) {

		TaskGroupResult result = taskGroupService.save(dto);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, result.getDtoEntity());
		return "maintenance/taskgroup/add-view";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {

		TaskGroupResult result = taskGroupService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}

		return "redirect:/taskGroup/index";
	}

	private void setCommonData(Model model, TaskGroupDTO dto) {
		model.addAttribute("taskGroup", dto);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("sites", assetService.findSiteByBusinessId(dto.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
	}

}
