package com.codex.ecam.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codex.ecam.service.app.api.AppService;

import java.util.ArrayList;

@Controller
@RequestMapping(MyAppController.REQUEST_MAPPING_URL)
public class MyAppController {

	public static final String REQUEST_MAPPING_URL = "/myapp";

	@Autowired
	private AppService appService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "myapp/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "myapp/home-view";
	}

	@RequestMapping(value = "/install-confirmation-view", method = RequestMethod.GET)
	public String installConfirmationView(Model model, Integer appId) {
		model.addAttribute("relatedApps", appService.findRelatedApps(appId));
		return "myapp/modals/install-confirmation-modal";
	}

	@RequestMapping(value = "/uninstall-confirmation-view", method = RequestMethod.GET)
	public String uninstallConfirmationView(Model model, Integer appId) {
		model.addAttribute("affectedApps", appService.findAffectedApps(appId));
		return "myapp/modals/uninstall-confirmation-modal";
	}

	@RequestMapping(value = "/install", method = RequestMethod.GET)
	@ResponseBody
	public Boolean installApp(Integer appId, Model model) throws Exception {
		return appService.installApp(appId);
	}

	@RequestMapping(value = "/uninstall", method = RequestMethod.GET)
	@ResponseBody
	public Boolean uninstallApp(Integer appId, Model model) throws Exception {
		return appService.uninstallApp(appId);
	}

}
