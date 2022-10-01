package com.codex.ecam.controller.inventory.purchaseOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.codex.ecam.result.RestResult;
import com.codex.ecam.result.inventory.MRNResult;
import com.codex.ecam.result.purchasing.PurchaseOrderResult;
import com.codex.ecam.result.purchasing.RFQResult;
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

	@RequestMapping(value = "/poTaxView", method = RequestMethod.GET)
	public String getTaxSelectView(Model model) {
		return "inventory/purchaseorder/modal/tax-select-modal";
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

	@RequestMapping(value = "/view/modal/departments", method = RequestMethod.GET)
	public String getDepartmentModalView(Model model, @RequestParam(name = "title", defaultValue = "Department(s)")String title) {
		model.addAttribute("title", title);
		return "general/table/departments";
	}

	@RequestMapping(value = "/view/modal/suppliers", method = RequestMethod.GET)
	public String getSupplierModalView(Model model, @RequestParam(name = "title", defaultValue = "Supplier(s)")String title) {
		model.addAttribute("title", title);
		return "general/table/suppliers";
	}

	@RequestMapping(value = "/view/modal/accounts", method = RequestMethod.GET)
	public String getAccountsModalView(Model model, @RequestParam(name = "title", defaultValue = "Account(s)")String title) {
		model.addAttribute("title", title);
		return "general/table/accounts";
	}

	@RequestMapping(value = "/view/modal/currencies", method = RequestMethod.GET)
	public String getCurrencyModalView(Model model, @RequestParam(name = "title", defaultValue = "Currency(s)")String title) {
		model.addAttribute("title", title);
		return "general/table/currencies";
	}

	@RequestMapping(value = "/view/modal/countries", method = RequestMethod.GET)
	public String getCountryModalView(Model model, @RequestParam(name = "title", defaultValue = "Country(s)")String title) {
		model.addAttribute("title", title);
		return "general/table/countries";
	}

	@RequestMapping(value = "/view/modal/sites", method = RequestMethod.GET)
	public String getSiteModalView(Model model, @RequestParam(name = "title", defaultValue = "Site(s)")String title) {
		model.addAttribute("title", title);
		return "general/table/sites";
	}

	/*********************************************************************
	 * CRUD Ops
	 *********************************************************************/

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model,  purchaseOrderService.createNewPurchaseorder().getDtoEntity());

			return "inventory/purchaseorder/add-view";
		} catch (final Exception ex) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/purchaseorder/index";
		}
	}

	@RequestMapping(value = "/addfromrfq", method = RequestMethod.GET)
	public @ResponseBody RFQResult generatePOFromRFQItems(Model model, RedirectAttributes ra, @ModelAttribute("rfqItemIds") final String rfqItemIds,@ModelAttribute("supplierIds") final String supplierIds) {
		RFQResult rfqResult=null;
		if (rfqItemIds != null && supplierIds != null) {
			rfqResult  = purchaseOrderService.createPurchaseOrderFromRFQItems(rfqItemIds,supplierIds);

		}

		return rfqResult;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Integer id, Model model, RedirectAttributes ra) {
		try {
			final PurchaseOrderDTO purchaseOrder = purchaseOrderService.findById(id);
			setCommonData(model, purchaseOrder);

			return "inventory/purchaseorder/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));

			return "redirect:/purchaseorder/index";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("purchaseOrder") PurchaseOrderDTO purchaseOrder, Model model) throws Exception {

		PurchaseOrderResult result;

		if (purchaseOrder.getId() != null && purchaseOrder.getId() > 0) {
			result = purchaseOrderService.update(purchaseOrder);
		} else {
			result = purchaseOrderService.save(purchaseOrder);
		}

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}
		setCommonData(model, result.getDtoEntity());
		return "inventory/purchaseorder/add-view";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {
		final PurchaseOrderResult result = purchaseOrderService.delete(id);
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
		return "redirect:/purchaseorder/index";
	}

	@RequestMapping(value = "/code-by-business", method = RequestMethod.GET)
	public @ResponseBody RestResult<String> codeByBusiness(Integer businessId) {
		final RestResult<String> result = new RestResult<>();
		result.setData(purchaseOrderService.getNextCode(businessId).toString());

		return result ;
	}


	@RequestMapping(value = "/statusChange", method = RequestMethod.GET)
	public String purchaseOrderStatusChange(Integer id, PurchaseOrderStatus status, Model model, RedirectAttributes ra) throws Exception {
		final PurchaseOrderResult result= purchaseOrderService.statusChange(id, status);
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, result.getDtoEntity());
		return "inventory/purchaseorder/add-view";
	}

	@RequestMapping(value = "/download-file", method = RequestMethod.GET)
	public void  downloadFile(@RequestParam("fileId")Integer id, HttpServletResponse response) throws Exception {
		purchaseOrderService.purchaseOrderFileDownload(id,response);
	}

	@RequestMapping(value = "/upload-file", method = RequestMethod.POST)
	public @ResponseBody List<String>  uploadFile(@RequestParam("fileData") MultipartFile fileData, @RequestParam("fileRefId")String refId) throws Exception {
		final List<String> list = new ArrayList<String>();
		list.add(fileData.getContentType());
		list.add(purchaseOrderService.purchaseOrderFileUpload(fileData,refId));
		return list;
	}

	@RequestMapping(value = "/delete-file", method = RequestMethod.GET)
	public void deleteFile(Model model,@RequestParam("fileRefId")Integer refId) throws Exception {
		purchaseOrderService.purchaseOrderFileDelete(refId);
	}

	@RequestMapping(value = "/generatePoFromMrn", method = RequestMethod.GET)
	public @ResponseBody MRNResult generateAodFromMrn(String ids, Integer mrnId) throws Exception {
		MRNResult result = null;
		if (mrnId != null && mrnId > 0) {
			result = purchaseOrderService.generatePoFromMrn(ids, mrnId);
		}
		return result;
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final PurchaseOrderResult result = purchaseOrderService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "Purchase Order already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/user/home-view";
	}

	private void setCommonData(Model model, PurchaseOrderDTO purchaseOrder) throws Exception {
		model.addAttribute("purchaseOrder", purchaseOrder);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("sites", assetService.findAllSiteByLevel());
		model.addAttribute("billingTerms", BillingTerm.getBillingTermList());
		model.addAttribute("poAdditionalCostTypes",PurchaseOrderAdditionalCostType.getAdditionalCostTypeList());
		model.addAttribute("shippingTypes", ShippingType.getShippingTypeList());
	}

}
