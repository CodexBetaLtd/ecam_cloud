package com.codex.ecam.controller.maintenance.scheduledmaintenance;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;  

@Controller
@RequestMapping(ScheduledMaintenanceTriggerController.REQUEST_MAPPING_URL)
public class ScheduledMaintenanceTriggerController {

    public static final String REQUEST_MAPPING_URL = "/scheduled-maintenance-trigger";

    @RequestMapping(value = "/outdated", method = RequestMethod.GET)
    public String getOutDatedView(Model model,String from, String to) {
    	model.addAttribute("fromDate", from);
    	model.addAttribute("toDate", to); 
    	model.addAttribute("title", "Out Dated "); 
    	return "maintenance/scheduledmaintenance/filter-result-view";
    }
    
    @RequestMapping(value = "/currentweek", method = RequestMethod.GET)
    public String getThisWeekView(Model model,String from, String to) {
    	model.addAttribute("fromDate", from);
    	model.addAttribute("toDate", to); 
    	model.addAttribute("title", "Current Week "); 
    	return "maintenance/scheduledmaintenance/filter-result-view";
    }
    
    @RequestMapping(value = "/nextweek", method = RequestMethod.GET)
    public String getNextWeekView(Model model,String from, String to) {
    	model.addAttribute("fromDate", from);
    	model.addAttribute("toDate", to); 
    	model.addAttribute("title", "Next Week "); 
    	return "maintenance/scheduledmaintenance/filter-result-view";
    } 
    
    @RequestMapping(value = "/nextmonth", method = RequestMethod.GET)
    public String getNextMonthView(Model model,String from, String to) {
    	model.addAttribute("fromDate", from);
    	model.addAttribute("toDate", to); 
    	model.addAttribute("title", "Next Month "); 
    	return "maintenance/scheduledmaintenance/filter-result-view";
    }

}
