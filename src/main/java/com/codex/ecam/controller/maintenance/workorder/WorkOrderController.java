package com.codex.ecam.controller.maintenance.workorder;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.TaskType;
import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.dto.admin.AccountDTO;
import com.codex.ecam.dto.admin.ChargeDepartmentDTO;
import com.codex.ecam.dto.admin.MaintenanceTypeDTO;
import com.codex.ecam.dto.admin.PriorityDTO;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderMeterReadingDTO;
import com.codex.ecam.result.RestResult;
import com.codex.ecam.result.inventory.AODResult;
import com.codex.ecam.result.maintenance.WorkOrderResult;
import com.codex.ecam.service.admin.api.AccountService;
import com.codex.ecam.service.admin.api.ChargeDepartmentService;
import com.codex.ecam.service.admin.api.MaintenanceTypeService;
import com.codex.ecam.service.admin.api.MeterReadingUnitService;
import com.codex.ecam.service.admin.api.PriorityService;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.maintenance.api.MiscellaneousExpenseService;
import com.codex.ecam.service.maintenance.api.WorkOrderService;

@Controller
@RequestMapping(WorkOrderController.REQUEST_MAPPING_URL)
public class WorkOrderController {

	public static final String REQUEST_MAPPING_URL = "/workorder";

	@Autowired
	private WorkOrderService workOrderService;

	@Autowired
	private MaintenanceTypeService maintenanceTypeService;

	@Autowired
	private PriorityService priorityService;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private AssetService assetService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ChargeDepartmentService chargeDeparmentService;

	@Autowired
	private MeterReadingUnitService meterReadingUnitService;

	@Autowired
	private MiscellaneousExpenseService miscellaneousExpenseService;

	@Autowired
	Environment environment;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "maintenance/workorder/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "maintenance/workorder/home-view";
	}

	/*********************************************************************
	 * Work Order Modal Views
	 *********************************************************************/

	@RequestMapping(value = "/project-select-modal-view", method = RequestMethod.GET)
	public String getProjectTableView(Model model) {
		return "maintenance/modals/project-modal";
	}

	@RequestMapping(value = "/asset-add-modal-view", method = RequestMethod.GET)
	public String getAssetView(Model model) {
		return "maintenance/modals/asset-modal";
	}

	@RequestMapping(value = "/user-select-modal-view", method = RequestMethod.GET)
	public String getUserView(Model model) {
		return "maintenance/workorder/modals/user-modal";
	}

	@RequestMapping(value = "/part-add-model-view", method = RequestMethod.GET)
	public String getPartView(Model model) {
		return "maintenance/workorder/modals/part-add-modal";
	}

	@RequestMapping(value = "/stock-select-model-view", method = RequestMethod.GET)
	public String getStockView(Model model) {
		return "maintenance/workorder/modals/stock-select-modal";
	}

	@RequestMapping(value = "/task-add-modal-view", method = RequestMethod.GET)
	public String getTaskView(Model model) {
		model.addAttribute("taskTypeList", TaskType.getTaskTypes());
		return "maintenance/workorder/modals/task-add-modal";
	}

	@RequestMapping(value = "/task-group-add-modal-view", method = RequestMethod.GET)
	public String getWOTaskGroupModalView(Model model) {
		return "maintenance/workorder/modals/task-group-add-modal";
	}

	@RequestMapping(value = "/task-group-select-modal-view", method = RequestMethod.GET)
	public String getWOTaskGroupSelectModalView(Model model) {
		return "maintenance/workorder/modals/task-group-select-modal";
	}

	@RequestMapping(value = "/notification-add-modal-view", method = RequestMethod.GET)
	public String getNotificationView(Model model) {
		return "maintenance/workorder/modals/notification-add-modal";
	}

	@RequestMapping(value = "/mis-cost-add-modal-view", method = RequestMethod.GET)
	public String getMisCostView(Model model) {
		model.addAttribute("miscellaneousExpenseTypeList", miscellaneousExpenseService.findAll());
		return "maintenance/workorder/modals/miscellaneous-cost-add-modal";
	}

	@RequestMapping(value = "/meter-reading-add-modal-view", method = RequestMethod.GET)
	public String getMeterReadingView(Model model) {
		model.addAttribute("woMeterReadings", new WorkOrderMeterReadingDTO());
		return "maintenance/workorder/modals/meter-reading-add-modal";
	}

	@RequestMapping(value = "/meter-reading-edit-modal-view", method = RequestMethod.GET)
	public String getMeterReadingEditView(Model model, Integer id) throws Exception {
		model.addAttribute("woMeterReadings", assetService.findAssetMeterReadingByAssetId(id));
		return "maintenance/workorder/modals/meter-reading-add-modal";
	}

	@RequestMapping(value = "/meter-reading-value-add-modal-view", method = RequestMethod.GET)
	public String getMeterReadingValueView(Model model) {
		model.addAttribute("woMeterReadings", new WorkOrderMeterReadingDTO());
		return "maintenance/workorder/modals/meter-reading-value-add-modal";
	}

	@RequestMapping(value = "/filemodelview", method = RequestMethod.GET)
	public String getFileTableView(Model model) {
		return "maintenance/workorder/modals/file-add-modal";
	}

	@RequestMapping(value = "/wonoteaddmodalview", method = RequestMethod.GET)
	public String getWoNoteAddModalView(Model model) {
		return "maintenance/workorder/modals/note-add-modal";
	}

	@RequestMapping(value = "/statuschangeview", method = RequestMethod.GET)
	public String getStatusChangeView(Model model) {
		return "maintenance/workorder/modals/status-change-note-add-modal";
	}

	/*********************************************************************
	 * Work Order CRUD
	 *********************************************************************/

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, new WorkOrderDTO());
			return "maintenance/workorder/add-view";
		} catch (final Exception ex) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/workorder/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Integer id, Model model, RedirectAttributes ra) {
		try {
			final WorkOrderDTO workOrder = workOrderService.findById(id);
			setCommonData(model, workOrder);

			return "maintenance/workorder/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));

			return "redirect:/workorder/index";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("workOrder") WorkOrderDTO workOrder, Model model) throws Exception {

		final WorkOrderResult result = workOrderService.save(workOrder);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			workOrder = workOrderService.findById(workOrder.getId());
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, workOrder);

		return "maintenance/workorder/add-view";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {

		final WorkOrderResult result = workOrderService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}

		return "redirect:/workorder/index";
	}

	@RequestMapping(value = "/statusChange", method = RequestMethod.POST)
	public String workOrderRequestStatusChange(Integer id, WorkOrderStatus status, String date, String note, Model model) throws Exception {
		final WorkOrderResult result = workOrderService.statusChange(id, status, date, note);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, workOrderService.findById(id));
		return "maintenance/workorder/add-view";
	}

	@RequestMapping(value = "/getsites", method = RequestMethod.GET)
	public @ResponseBody List<AssetDTO> selectParent(Integer id, Model model) throws Exception {
		final List<AssetDTO> assetDTOs = assetService.findSiteByBusinessId(id, AssetCategoryType.LOCATIONS_OR_FACILITIES);
		return assetDTOs;
	}

	@RequestMapping(value = "/code-by-business", method = RequestMethod.GET)
	public @ResponseBody RestResult<String> codeByBusiness(Integer businessId) {
		return workOrderService.findCurrentWorkOrderCode(businessId);
	}

	@RequestMapping(value = "/maintenance-type-by-business/{id}", method = RequestMethod.GET)
	public @ResponseBody List<MaintenanceTypeDTO> maintenanceTypeByBusiness(@PathVariable("id")Integer id, Model model) {
		return  maintenanceTypeService.findAllByBusiness(id);
	}

	@RequestMapping(value = "/priorities-by-business/{id}", method = RequestMethod.GET)
	public @ResponseBody List<PriorityDTO> prioritiesByBusiness(@PathVariable("id")Integer id, Model model) {
		return  priorityService.findAllByBusiness(id);
	}

	@RequestMapping(value = "/accounts-by-business/{id}", method = RequestMethod.GET)
	public @ResponseBody List<AccountDTO> accountsByBusiness(@PathVariable("id")Integer id, Model model) {
		return accountService.findAllByBusiness(id);
	}

	@RequestMapping(value = "/departments-by-business/{id}", method = RequestMethod.GET)
	public @ResponseBody List<ChargeDepartmentDTO> departmentsTypeByBusiness(@PathVariable("id")Integer id, Model model) {
		return  chargeDeparmentService.findAllByBusiness(id);
	}

	@RequestMapping(value = "/status-change", method = RequestMethod.GET)
	public String receiptOrderStatusChange(Integer id, WorkOrderStatus workOrderStatus,String date, String note, Model model) throws Exception {
		if (id != null && id > 0) {
			try {
				final WorkOrderResult result = workOrderService.statusChange(id, workOrderStatus, date, note);
				if (result.getStatus().equals(ResultStatus.ERROR)) {
					model.addAttribute("error", result.getErrorList());
				} else {
					model.addAttribute("success", result.getMsgList());
				}
			} catch (final Exception e) {
				model.addAttribute("error", "Error Occurred. Please Try again.");
			}
		} else {
			model.addAttribute("error", "Please Save the Work Order First");
		}

		setCommonData(model, workOrderService.findById(id));

		return "maintenance/workorder/add-view";
	}

	@RequestMapping(value = "/upload-file", method = RequestMethod.POST)
	public @ResponseBody List<String>  uploadFile(@RequestParam("fileData") MultipartFile fileData, @RequestParam("fileRefId")String refId) throws Exception {
		final List<String> list = new ArrayList<String>();
		list.add(fileData.getContentType());
		list.add(workOrderService.workorderFileUpload(fileData, refId));
		return list;
	}

	@RequestMapping(value = "/download-file", method = RequestMethod.GET)
	public void downloadFile(@RequestParam("fileId")Integer id, HttpServletResponse response) throws Exception {
		workOrderService.workorderFileDownload(id, response);
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final WorkOrderResult result = workOrderService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "WorkOrder already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "maintenance/workorder/home-view";
	}

	/*********************************************************************
	 * Common Data
	 *********************************************************************/
	private void setCommonData(Model model, WorkOrderDTO workOrder) {
		model.addAttribute("workOrder", workOrder);
		model.addAttribute("maintenanceTypes", maintenanceTypeService.findAllByBusiness(workOrder.getBusinessId()));
		model.addAttribute("priorities", priorityService.findAllByBusiness(workOrder.getBusinessId()));
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("sites", assetService.findSiteByBusinessId(workOrder.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
		model.addAttribute("accounts", accountService.findAllByBusiness(workOrder.getBusinessId()));
		model.addAttribute("chargeDepartments", chargeDeparmentService.findAllByBusiness(workOrder.getBusinessId()));
		model.addAttribute("meterReadingUnits", meterReadingUnitService.findAllMeterReadings());
		model.addAttribute("workOrderStatuses", WorkOrderStatus.getWorkOrderStatusList());
	}
}

