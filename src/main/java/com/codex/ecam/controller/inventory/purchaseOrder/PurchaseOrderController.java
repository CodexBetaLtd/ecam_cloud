package com.codex.ecam.controller.inventory.purchaseOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.*;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.codex.ecam.result.purchasing.PurchaseOrderResult;
import com.codex.ecam.service.admin.api.*;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.inventory.api.PurchaseOrderService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(PurchaseOrderController.REQUEST_MAPPING_URL)
public class PurchaseOrderController {

	public static final String REQUEST_MAPPING_URL = "/purchaseorder";

	@Autowired
	private PurchaseOrderService purchaseOrderService;
	@Autowired
	private AssetService assetService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BusinessService businessService;
	@Autowired
	private ChargeDepartmentService chargeDeparmentService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "inventory/purchaseorder/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return "inventory/purchaseorder/home-view";
	}

	/*********************************************************************
	 * Modal Views
	 *********************************************************************/

	@RequestMapping(value = "/itemView", method = RequestMethod.GET)
	public String getItemAddView(Model model) {
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("chargeDepartments", chargeDeparmentService.findAll());
		return "inventory/purchaseorder/modal/item-modal";
	}

	@RequestMapping(value = "/poAssetView", method = RequestMethod.GET)
	public String getAssetSelectView(Model model) {
		return "inventory/purchaseorder/modal/asset-modal";
	}

	@RequestMapping(value = "/poWorkOrderView", method = RequestMethod.GET)
	public String getWOSelectView(Model model) {
		return "inventory/purchaseorder/modal/workorder-modal";
	}

	@RequestMapping(value = "/poAdditionalCostView", method = RequestMethod.GET)
	public String getAdditionalCostAddView(Model model) {
		model.addAttribute("poAdditionalCostTypes", PurchaseOrderAdditionalCostType.getAdditionalCostTypeList());
		model.addAttribute("shippingTypes", ShippingType.getShippingTypeList());
		return "inventory/purchaseorder/modal/additional-cost-modal";
	}

	@RequestMapping(value = "/poNotificationView", method = RequestMethod.GET)
	public String getPONotificationView(Model model) {
		model.addAttribute("userList", userService.findUserList());
		return "inventory/purchaseorder/modal/notification-modal";
	}

	@RequestMapping(value = "/poDiscussionView", method = RequestMethod.GET)
	public String getPODiscussionView(Model model) {
		return "inventory/purchaseorder/modal/discussion-modal";
	}

	@RequestMapping(value = "/file-add-modal-view", method = RequestMethod.GET)
	public String getFileAddView(Model model) {
		return "inventory/purchaseorder/modal/file-add-modal";
	}

	/*********************************************************************
	 * CRUD Ops
	 *********************************************************************/


	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) {
		try {
			PurchaseOrderDTO dto = new PurchaseOrderDTO();
			setCommonData(model,dto);
			return "inventory/purchaseorder/add-view";
		} catch (Exception ex) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/purchaseorder/index";
		}
	}

	@RequestMapping(value = "/addfromrfq", method = RequestMethod.GET)
	public String generatePOFromRFQItems(Model model, RedirectAttributes ra, @ModelAttribute("rfqItemIds") final String rfqItemIds) {
		try {
			PurchaseOrderDTO dto = purchaseOrderService.createPurchaseOrderFromRFQItems(rfqItemIds);
			setCommonData(model, dto);
			return "inventory/purchaseorder/add-view";
		} catch (Exception ex) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/purchaseorder/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Integer id, Model model, RedirectAttributes ra) {
		try {
			PurchaseOrderDTO purchaseOrder = purchaseOrderService.findById(id);
			setCommonData(model, purchaseOrder);

			return "inventory/purchaseorder/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));

			return "redirect:/purchaseorder/index";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("purchaseOrder") PurchaseOrderDTO purchaseOrder, Model model) throws Exception {

		PurchaseOrderResult result;

		if ((purchaseOrder.getId() != null) && (purchaseOrder.getId() > 0)) {
			result = purchaseOrderService.update(purchaseOrder);
		} else {
			result = purchaseOrderService.save(purchaseOrder);
		}

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, purchaseOrder);
		return "inventory/purchaseorder/add-view";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {
		PurchaseOrderResult result = purchaseOrderService.delete(id);
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
		return "redirect:/purchaseorder/index";
	}

	@RequestMapping(value = "/statusChange", method = RequestMethod.GET)
	public String purchaseOrderStatusChange(Integer id, PurchaseOrderStatus status, Model model, RedirectAttributes ra) throws Exception {
		PurchaseOrderDTO purchaseOrder = null;

		if ((id != null) && (id > 0)) {
			purchaseOrder = purchaseOrderService.statusChange(id,status);
		}
		else{
			ra.addFlashAttribute("warning", "Submit for approvel");
			ra.addFlashAttribute("messageBody", "Submit for approvel");
		}

		setCommonData(model, purchaseOrder);
		return "inventory/purchaseorder/add-view";
	}
	
	@RequestMapping(value = "/download-file", method = RequestMethod.GET)
	public void  downloadFile(@RequestParam("fileId")Integer id, HttpServletResponse response) throws Exception {
		purchaseOrderService.purchaseOrderFileDownload(id,response);
	}
	
	@RequestMapping(value = "/upload-file", method = RequestMethod.POST)
	public @ResponseBody List<String>  uploadFile(@RequestParam("fileData") MultipartFile fileData, @RequestParam("fileRefId")String refId) throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(fileData.getContentType());
		list.add(purchaseOrderService.purchaseOrderFileUpload(fileData,refId));
		return list;
	}
	
	@RequestMapping(value = "/delete-file", method = RequestMethod.GET)
	public void deleteFile(Model model,@RequestParam("fileRefId")Integer refId) throws Exception {
		purchaseOrderService.purchaseOrderFileDelete(refId);
	}


	private void setCommonData(Model model, PurchaseOrderDTO purchaseOrder) throws Exception {
		model.addAttribute("purchaseOrder", purchaseOrder);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("sites", assetService.findAllSiteByLevel());
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("chargeDepartments", chargeDeparmentService.findAll());
		model.addAttribute("countries", countryService.findAll());
		model.addAttribute("currencies", currencyService.findAll());
		model.addAttribute("billingTerms", BillingTerm.getBillingTermList());
		model.addAttribute("poAdditionalCostTypes",PurchaseOrderAdditionalCostType.getAdditionalCostTypeList());
		model.addAttribute("shippingTypes", ShippingType.getShippingTypeList());
	}

}
