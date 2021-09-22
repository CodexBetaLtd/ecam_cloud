package com.codex.ecam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codex.ecam.security.api.SecurityContextAccessor;
import com.codex.ecam.service.app.api.AppService;
import com.codex.ecam.service.dashboard.api.WorkOrderComparisonService;
import com.codex.ecam.service.inventory.api.StockService;
import com.codex.ecam.service.maintenance.api.ScheduledMaintenanceTriggerCountService;
import com.codex.ecam.service.maintenance.api.WorkOrderService;
import com.codex.ecam.util.AuthenticationUtil;

@Controller
public class HomeController {

	@Autowired
	private SecurityContextAccessor securityContextAccessor;

	@Autowired
	private ScheduledMaintenanceTriggerCountService smTriggerCountService;

	@Autowired
	private WorkOrderComparisonService woComparisonService;

	@Autowired
	private WorkOrderService workoderService;

	@Autowired
	private StockService stockService;

	@Autowired
	private AppService appService;


	@RequestMapping(value = { "/session-expire-redirect"}, method = RequestMethod.GET)
	public String sessionExpiredRedirect() {
		return "/session-expire-redirect";
	}

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {
		if( AuthenticationUtil.isAuthUserGeneralLevel() ){
			model.addAttribute("siteList", AuthenticationUtil.getUserSiteList());
			return "/site-select";
		} else if ( AuthenticationUtil.isAuthUserSystemLevel() ) {
			AuthenticationUtil.setLoginUserBusiness(AuthenticationUtil.getAuthenticatedUser().getBusiness());
		}
		setCommonData(model);
		return "dashboard/index";
	}

	@RequestMapping(value = { "/dashboard" }, method = RequestMethod.GET)
	public String dashboard(Model model) {
		setCommonData(model);
		return "dashboard/index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		if (securityContextAccessor.isCurrentAuthenticationAnonymous()) {
			return "/login";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/set-user-site", method = RequestMethod.POST)
	public String setUserSite(@ModelAttribute("siteId") Integer siteId, Model model) {
		securityContextAccessor.setUserSite(siteId);
		setCommonData(model);
		return "dashboard/index";
	}

	@RequestMapping(value = "/set-user-business", method = RequestMethod.POST)
	public String setUserBusiness(@ModelAttribute("businessId") Integer businessId, Model model) {
		securityContextAccessor.setUserBusiness(businessId);
		setCommonData(model);
		return "dashboard/index";
	}

	private void setCommonData(Model model) {
		try {
			model.addAttribute("scheduleMaintenenceCount", smTriggerCountService.getCount());
			model.addAttribute("woComparison", woComparisonService.getWoComparisonChartData());
			model.addAttribute("openWorkOrderCount", workoderService.findAllOpenWorkOderCount());
			model.addAttribute("mttr", 20);
			model.addAttribute("lowStockItem", stockService.findMinimumStock());
			model.addAttribute("workOderRequestCount", 71);
			model.addAttribute("highPriorityWorkOrder", workoderService.findAllHighPriorityWorkOderCount());
			model.addAttribute("businessWigets", appService.findAllWigetByUserLevel());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
