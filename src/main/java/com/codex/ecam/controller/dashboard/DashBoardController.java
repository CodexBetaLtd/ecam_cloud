package com.codex.ecam.controller.dashboard;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 

@Controller
@RequestMapping(DashBoardController.REQUEST_MAPPING_URL)
public class DashBoardController {
	
	public static final String REQUEST_MAPPING_URL = "/dashboard";
	
	@RequestMapping(value = { "/asset-workorder-history-calendar-detail-view" }, method = RequestMethod.GET)
	public String getAssetWorkOrderHistoryCalendarDetailView(Model model) {
		return "dashboard/workorderhistory/asset-workorder-history-calendar-detail-modal";
	} 

	@RequestMapping(value = { "/employee-history-workorder-task-view" }, method = RequestMethod.GET)
	public String getEmployeeHistoryWorkOrderTaskView(Model model) {
		return "dashboard/employeehistory/employee-history-workorder-task-modal";
	} 
	
	@RequestMapping(value = { "/high-priority-workorder-view" }, method = RequestMethod.GET)
	public String getHighPriorityWorkOrderView(Model model) {
		return "dashboard/highpriorityworkorder/high-priority-work-order";
	} 
	@RequestMapping(value = { "/open-workorder-view" }, method = RequestMethod.GET)
	public String getOpenWorkOrderView(Model model) {
		return "dashboard/openworkorder/open-work-order";
	} 
	@RequestMapping(value = { "/low-stock-item-view" }, method = RequestMethod.GET)
	public String getLowStockItemView(Model model) {
		return "dashboard/lowstockitem/low-stock-item";
	} 
    
}
