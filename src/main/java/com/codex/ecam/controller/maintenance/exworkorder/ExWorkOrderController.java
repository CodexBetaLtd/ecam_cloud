package com.codex.ecam.controller.maintenance.exworkorder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.dto.admin.ChargeDepartmentDTO;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.maintenance.exworkorder.ExWorkOrderDTO;
import com.codex.ecam.result.inventory.AODResult;
import com.codex.ecam.result.maintenance.ExWorkOrderResult;
import com.codex.ecam.service.admin.api.ChargeDepartmentService;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.maintenance.api.ExWorkOrderService;

@Controller
@RequestMapping(ExWorkOrderController.REQUEST_MAPPING_URL)
public class ExWorkOrderController {

	public static final String REQUEST_MAPPING_URL = "/exworkorder";

	@Autowired
	private ExWorkOrderService exworkOrderService;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private AssetService assetService;

	@Autowired
	private ChargeDepartmentService chargeDeparmentService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "maintenance/exworkorder/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "maintenance/exworkorder/home-view";
	}

	/*********************************************************************
	 * Work Order Modal Views
	 *********************************************************************/

	@RequestMapping(value = "/asset-select-modal-view", method = RequestMethod.GET)
	public String getAssetView(Model model) {
		return "maintenance/exworkorder/modals/asset-modal";
	}

	@RequestMapping(value = "/user-select-modal-view", method = RequestMethod.GET)
	public String getUserView(Model model) {
		return "maintenance/exworkorder/modals/user-modal";
	}

	@RequestMapping(value = "/supplier-select-modal-view", method = RequestMethod.GET)
	public String getSupplierView(Model model) {
		return "maintenance/exworkorder/modals/supplier-modal";
	}

	/*********************************************************************
	 * Work Order CRUD
	 *********************************************************************/

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, new ExWorkOrderDTO());
			return "maintenance/exworkorder/add-view";
		} catch (final Exception ex) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/exworkorder/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Integer id, Model model, RedirectAttributes ra) {
		try {
			final ExWorkOrderDTO workOrder = exworkOrderService.findById(id);
			setCommonData(model, workOrder);

			return "maintenance/exworkorder/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));

			return "redirect:/exworkorder/index";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("workOrder") ExWorkOrderDTO workOrder, Model model) throws Exception {

		final ExWorkOrderResult result = exworkOrderService.save(workOrder);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			workOrder = exworkOrderService.findById(workOrder.getId());
			setCommonData(model, new ExWorkOrderDTO());
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, workOrder);

		return "maintenance/exworkorder/add-view";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {

		final ExWorkOrderResult result = exworkOrderService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}

		return "redirect:/exworkorder/index";
	}

	@RequestMapping(value = "/statusChange", method = RequestMethod.POST)
	public String workOrderRequestStatusChange(Integer id, WorkOrderStatus status, String date, String note, Model model) throws Exception {
		final ExWorkOrderResult result = exworkOrderService.statusChange(id, status, date, note);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, exworkOrderService.findById(id));
		return "maintenance/exworkorder/add-view";
	}

	@RequestMapping(value = "/getsites", method = RequestMethod.GET)
	public @ResponseBody List<AssetDTO> selectParent(Integer id, Model model) throws Exception {
		final List<AssetDTO> assetDTOs = assetService.findSiteByBusinessId(id, AssetCategoryType.LOCATIONS_OR_FACILITIES);
		return assetDTOs;
	}

	//	@RequestMapping(value = "/code-by-business", method = RequestMethod.GET)
	//	public @ResponseBody RestResult<String> codeByBusiness(Integer businessId) {
	//		return workOrderService.findCurrentWorkOrderCode(businessId);
	//	}


	@RequestMapping(value = "/departments-by-business/{id}", method = RequestMethod.GET)
	public @ResponseBody List<ChargeDepartmentDTO> departmentsTypeByBusiness(@PathVariable("id")Integer id, Model model) {
		return  chargeDeparmentService.findAllByBusiness(id);
	}

	@RequestMapping(value = "/status-change", method = RequestMethod.GET)
	public String receiptOrderStatusChange(Integer id, WorkOrderStatus workOrderStatus,String date, String note, Model model) throws Exception {
		if (id != null && id > 0) {
			try {
				final ExWorkOrderResult result = exworkOrderService.statusChange(id, workOrderStatus, date, note);
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

		setCommonData(model, exworkOrderService.findById(id));

		return "maintenance/exworkorder/add-view";
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final ExWorkOrderResult result = exworkOrderService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Ex workOrder already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "maintenance/exworkorder/home-view";
	}


	/*********************************************************************
	 * Common Data
	 *********************************************************************/
	private void setCommonData(Model model, ExWorkOrderDTO exWorkOrderDTO) {
		model.addAttribute("exworkOrder", exWorkOrderDTO);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		try {
			model.addAttribute("sites", assetService.findAllSiteByBusiness(exWorkOrderDTO.getBusinessId()));
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("workOrderStatuses", WorkOrderStatus.getWorkOrderStatusList());
	}
}

