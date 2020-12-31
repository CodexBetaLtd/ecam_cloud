package com.codex.ecam.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.app.AppDTO;
import com.codex.ecam.result.app.AppResult;
import com.codex.ecam.service.app.api.AppService;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping(AppController.REQUEST_MAPPING_URL)
public class AppController {

	public static final String REQUEST_MAPPING_URL = "/app";

	@Autowired
	private AppService appService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "app/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "app/home-view";
	}

	@RequestMapping(value = "/menu-select-view", method = RequestMethod.GET)
	public String menuSelectView(Model model) {
		return "app/modals/menu-select-modal";
	}

	@RequestMapping(value = "/app-select-view", method = RequestMethod.GET)
	public String appSelectView(Model model) {
		return "app/modals/app-select-modal";
	}
	
	@RequestMapping(value = "/wiget-select-view", method = RequestMethod.GET)
	public String wigetSelectView(Model model) {
		return "app/modals/wiget-select-modal";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, new AppDTO());
			return "app/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
			return "redirect:/app/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			AppDTO app = appService.findById(id);
			setCommonData(model, app);
			return "app/add-view";
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/app/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {

		AppResult result = appService.delete(id);
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
		return "redirect:/app/index";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("app") @Valid AppDTO app, Model model) throws Exception {

		AppResult result = appService.save(app);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			app = appService.findById(app.getId());
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, app);
		return "app/add-view";
	}

	private void setCommonData(Model model, AppDTO app) {
		model.addAttribute("app", app);
	}

}
