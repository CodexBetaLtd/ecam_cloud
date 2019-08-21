package com.codex.ecam.controller.maintenance.scheduledmaintenance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.MeterReadingLogicType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.TaskType;
import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.constants.util.Months;
import com.codex.ecam.dto.admin.AccountDTO;
import com.codex.ecam.dto.admin.ChargeDepartmentDTO;
import com.codex.ecam.dto.admin.MaintenanceTypeDTO;
import com.codex.ecam.dto.admin.PriorityDTO;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.asset.AssetEventTypeAssetDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingDTO;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceDTO;
import com.codex.ecam.result.RestResult;
import com.codex.ecam.result.maintenance.ScheduledMaintenanceResult;
import com.codex.ecam.result.maintenance.WorkOrderResult;
import com.codex.ecam.service.admin.api.AccountService;
import com.codex.ecam.service.admin.api.ChargeDepartmentService;
import com.codex.ecam.service.admin.api.MaintenanceTypeService;
import com.codex.ecam.service.admin.api.PriorityService;
import com.codex.ecam.service.asset.api.AssetEventService;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.maintenance.api.ScheduledMaintenanceService;
import com.codex.ecam.service.maintenance.api.ScheduledMaintenanceTriggerService;

@Controller
@RequestMapping(ScheduledMaintenanceController.REQUEST_MAPPING_URL)
public class ScheduledMaintenanceController {

	public static final String REQUEST_MAPPING_URL = "/scheduledmaintenance";

	@Autowired
	private ScheduledMaintenanceService scheduledMaintenanceService;

	@Autowired
	private ScheduledMaintenanceTriggerService scheduledMaintenanceTriggerService;

	@Autowired
	private MaintenanceTypeService maintenanceTypeService;

	@Autowired
	private PriorityService priorityService;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private AssetService assetService;

	@Autowired
	private AssetEventService assetEventService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ChargeDepartmentService chargeDepartmentService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "maintenance/scheduledmaintenance/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "maintenance/scheduledmaintenance/home-view";
	}

	/*********************************************************************
	 * Scheduled Maintenance Modal Views
	 *********************************************************************/

	@RequestMapping(value = "/projectselectmodalview", method = RequestMethod.GET)
	public String getProjectTableView(Model model) {
		return "maintenance/modals/project-modal";
	}

	@RequestMapping(value = "/triggermodalview", method = RequestMethod.GET)
	public String getScheduleAddView(Model model) {
		model.addAttribute("months", Months.getMonths());
		model.addAttribute("meterReadingLogicTypes", MeterReadingLogicType.getMeterReadingLogics());
		return "maintenance/scheduledmaintenance/modal/trigger-add-modal";
	}

	@RequestMapping(value = "/assetselectmodalview", method = RequestMethod.GET)
	public String getAssetTableView(Model model) {
		return "maintenance/modals/asset-select-modal";
	}

	@RequestMapping(value = "/partmodelview", method = RequestMethod.GET)
	public String getPartAddView(Model model) {
		return "maintenance/scheduledmaintenance/modal/part-add-modal";
	}

	@RequestMapping(value = "/stockselectmodelview", method = RequestMethod.GET)
	public String getStockTableView(Model model) {
		return "maintenance/scheduledmaintenance/modal/stock-select-modal";
	}

	@RequestMapping(value = "/userselectmodalview", method = RequestMethod.GET)
	public String getUserTableView(Model model) {
		return "maintenance/modals/user-modal";
	}

	@RequestMapping(value = "/smTaskGroupView", method = RequestMethod.GET)
	public String getTaskGroupView(Model model) {
		return "maintenance/scheduledmaintenance/modal/sm-task-group-add";
	}

	@RequestMapping(value = "/smTaskGroupDataTableView", method = RequestMethod.GET)
	public String getTaskGroupDataTableView(Model model) {
		return "maintenance/scheduledmaintenance/modal/sm-task-group-datatable";
	}

	@RequestMapping(value = "/smTaskView", method = RequestMethod.GET)
	public String getTaskView(Model model) {
		model.addAttribute("taskTypeList", TaskType.getTaskTypes());
		return "maintenance/scheduledmaintenance/modal/sm-task-modal";
	}

	@RequestMapping(value = "/smNotificationView", method = RequestMethod.GET)
	public String getNotificationView(Model model) {
		return "maintenance/scheduledmaintenance/modal/notification-modal";
	}

	@RequestMapping(value = "/smUserView", method = RequestMethod.GET)
	public String getUserView(Model model) {
		return "maintenance/scheduledmaintenance/modal/user-modal";
	}

	@RequestMapping(value = "/filemodelview", method = RequestMethod.GET)
	public String getFileTableView(Model model) {
		return "maintenance/scheduledmaintenance/modal/file-add-modal";
	} 
    
	/*********************************************************************
	 * CRUD Ops
	 *********************************************************************/

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, new ScheduledMaintenanceDTO());
			return "maintenance/scheduledmaintenance/add-view";
		} catch (Exception ex) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
			return "redirect:/scheduledmaintenance/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Integer id, Model model, RedirectAttributes ra) {
		try {
			ScheduledMaintenanceDTO scheduleMaintenance = scheduledMaintenanceService.findById(id);
			setCommonData(model, scheduleMaintenance);
			return "maintenance/scheduledmaintenance/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/scheduledmaintenance/index";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("scheduleMaintenance") @Valid ScheduledMaintenanceDTO scheduleMaintenance, Model model, RedirectAttributes ra) throws Exception {

		ScheduledMaintenanceResult result = scheduledMaintenanceService.save(scheduleMaintenance);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			scheduleMaintenance = scheduledMaintenanceService.findById(scheduleMaintenance.getId());
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, scheduleMaintenance);

		return "maintenance/scheduledmaintenance/add-view";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {

		ScheduledMaintenanceResult result = scheduledMaintenanceService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}

		return "redirect:/scheduledmaintenance/index";
	}

	@RequestMapping(value = "/manual-trigger", method = RequestMethod.GET)
	public @ResponseBody WorkOrderResult manualTrigger(String ids, Integer smId) {
		return scheduledMaintenanceTriggerService.manualTrigger(ids, smId);
	}

	@RequestMapping(value = "/code-by-business", method = RequestMethod.GET)
	public @ResponseBody RestResult<String> codeByBusiness(Integer businessId) {
		return scheduledMaintenanceService.findCurrentScheduledMaintenanceCode(businessId);
	}

	/*********************************************************************
	 * Util Ops
	 *********************************************************************/

	@RequestMapping(value = "/sitesbybusiness", method = RequestMethod.GET)
	public @ResponseBody List<AssetDTO> selectParent(Integer id) throws Exception {
		List<AssetDTO> assets = assetService.findSiteByBusinessId(id, AssetCategoryType.LOCATIONS_OR_FACILITIES);
		return assets;
	}

	@RequestMapping(value = "/assetmeterreadingsbyasset/{assetId}", method = RequestMethod.GET)
	public @ResponseBody List<AssetMeterReadingDTO> selectAssetMeterReadings(@PathVariable("assetId") Integer assetId) throws Exception {
		List<AssetMeterReadingDTO> assetMeterReadings = assetService.findByMeterReadingByAssetId(assetId);
		return assetMeterReadings;
	}

	@RequestMapping(value = "/asseteventtypeassetsbyasset/{assetId}", method = RequestMethod.GET)
	public @ResponseBody List<AssetEventTypeAssetDTO> selectAssetEvent(@PathVariable("assetId") Integer assetId) throws Exception {
		List<AssetEventTypeAssetDTO> assetEventTypeAssets = assetEventService.findAssetEventTypeAssetByAssetId(assetId);
		return assetEventTypeAssets;
	} 
	
	@RequestMapping(value = "/maintenance-type-by-business/{id}", method = RequestMethod.GET)
	public @ResponseBody List<MaintenanceTypeDTO> maintenanceTypeByBusiness(@PathVariable("id")Integer id, Model model) {
		return  maintenanceTypeService.findAllByBusiness(id);
	}

	@RequestMapping(value = "/priorities-by-business/{id}", method = RequestMethod.GET)
	public @ResponseBody List<PriorityDTO> prioritiesTypeByBusiness(@PathVariable("id")Integer id, Model model) {
		return  priorityService.findAllByBusiness(id);
	} 

	@RequestMapping(value = "/accounts-by-business/{id}", method = RequestMethod.GET)
	public @ResponseBody List<AccountDTO> accountsByBusiness(@PathVariable("id")Integer id, Model model) {
		return accountService.findAllByBusiness(id);
	}

	@RequestMapping(value = "/departments-by-business/{id}", method = RequestMethod.GET)
	public @ResponseBody List<ChargeDepartmentDTO> departmentsTypeByBusiness(@PathVariable("id")Integer id, Model model) {
		return  chargeDepartmentService.findAllByBusiness(id);
	}
	
	@RequestMapping(value = "/upload-file", method = RequestMethod.POST)
	public @ResponseBody List<String>  uploadFile(@RequestParam("fileData") MultipartFile fileData,@RequestParam("fileRefId")String refId) throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(fileData.getContentType());
		list.add(scheduledMaintenanceService.scheduledMaintenanceFileUpload(fileData, refId));
		return list;
	}

	@RequestMapping(value = "/download-file", method = RequestMethod.GET)
	public void downloadFile(@RequestParam("fileId")Integer id, HttpServletResponse response) throws Exception {
		scheduledMaintenanceService.scheduledMaintenanceFileDownload(id,response);
	}

	private void setCommonData(Model model, ScheduledMaintenanceDTO scheduledMaintenance) throws Exception {
		model.addAttribute("scheduledMaintenance", scheduledMaintenance);
		model.addAttribute("maintainanceTypes", maintenanceTypeService.findAllByBusiness(scheduledMaintenance.getBusinessId()));
		model.addAttribute("priorities", priorityService.findAllByBusiness(scheduledMaintenance.getBusinessId()));
		model.addAttribute("sites", assetService.findSiteByBusinessId(scheduledMaintenance.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("accounts", accountService.findAllByBusiness(scheduledMaintenance.getBusinessId()));
		model.addAttribute("chargeDepartments", chargeDepartmentService.findAllByBusiness(scheduledMaintenance.getBusinessId()));
		model.addAttribute("workOrderStatuses", WorkOrderStatus.getWorkOrderStatusList());
	}

}
