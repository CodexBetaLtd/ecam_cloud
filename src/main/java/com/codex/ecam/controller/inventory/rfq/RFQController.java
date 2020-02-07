package com.codex.ecam.controller.inventory.rfq;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.codex.ecam.constants.BillingTerm;
import com.codex.ecam.constants.PurchaseOrderAdditionalCostType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.ShippingType;
import com.codex.ecam.constants.inventory.RFQStatus;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.codex.ecam.dto.inventory.rfq.RFQDTO;
import com.codex.ecam.result.inventory.MRNResult;
import com.codex.ecam.result.purchasing.RFQResult;
import com.codex.ecam.service.admin.api.AccountService;
import com.codex.ecam.service.admin.api.ChargeDepartmentService;
import com.codex.ecam.service.admin.api.CountryService;
import com.codex.ecam.service.admin.api.CurrencyService;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.biz.api.SupplierService;
import com.codex.ecam.service.inventory.api.RFQService;

@Controller
@RequestMapping(RFQController.REQUEST_MAPPING_URL)
public class RFQController {

	public static final String REQUEST_MAPPING_URL = "/rfq";

	@Autowired
	private RFQService rfqService;
	
	@Autowired
	private AssetService assetService;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private CurrencyService currencyService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private SupplierService supplierService;

	@Autowired
	private ChargeDepartmentService chargeDeparmentService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "inventory/rfq/home-view";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        return "inventory/rfq/home-view";
    }

	@RequestMapping(value = "/item-add-modal-view", method = RequestMethod.GET)
	public String getItemAddView(Model model) {
		return "inventory/rfq/modal/item-modal";
	}
	
	@RequestMapping(value = "/file-add-modal-view", method = RequestMethod.GET)
	public String getFileAddView(Model model) {
		return "inventory/rfq/modal/file-add-modal";
	}

	@RequestMapping(value = "/notification-add-modal-view", method = RequestMethod.GET)
	public String notificationAddView(Model model) { 
		return "inventory/rfq/modal/notification-add-modal";
	}

	@RequestMapping(value = "/asset-select-modal-view", method = RequestMethod.GET)
	public String getAssetSelectView(Model model) {
		return "inventory/rfq/modal/asset-modal";
	}
	
	@RequestMapping(value = "/user-select-modal-view", method = RequestMethod.GET)
	public String getUserSelectView(Model model) {
		return "inventory/rfq/modal/user-select-modal";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model,  new RFQDTO());
			return "inventory/rfq/add-view";
		} catch (Exception ex) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
            return "redirect:/rfq/index";
        }
    }

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Integer id, Model model, RedirectAttributes ra) {
		try {
			RFQDTO rfq = rfqService.findById(id);
			setCommonData(model, rfq);
			return "inventory/rfq/add-view";
		} catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
            return "redirect:/rfq/index";
        }
    }

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("rfq") RFQDTO rfq, Model model) throws Exception {

		RFQResult result = rfqService.save(rfq);
		
		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }
        setCommonData(model, result.getDtoEntity());
        return "inventory/rfq/add-view";
    }


	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {
		RFQResult result = rfqService.delete(id);
		if (result.getStatus().equals(ResultStatus.ERROR)) {
            ra.addFlashAttribute("error", result.getErrorList());
        } else {
            ra.addFlashAttribute("success", result.getMsgList());
        }
        return "redirect:/rfq/index";
    }

	@RequestMapping(value = "/status-change", method = RequestMethod.GET)
	public String rfqStatusChange(Integer id, RFQStatus status, Model model, RedirectAttributes ra)throws Exception {
		RFQResult result=rfqService.statusChange(id, status);
		if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }

		setCommonData(model, result.getDtoEntity());
		return "inventory/rfq/add-view";
	}
	
	@RequestMapping(value = "/upload-file", method = RequestMethod.POST)
	public @ResponseBody List<String>  uploadFile(@RequestParam("fileData") MultipartFile fileData, @RequestParam("fileRefId")String refId) throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(fileData.getContentType());
		list.add(rfqService.rfqFileUpload(fileData,refId));
		return list;
	}

	@RequestMapping(value = "/download-file", method = RequestMethod.GET)
	public void  downloadFile(@RequestParam("fileId")Integer id, HttpServletResponse response) throws Exception {
		rfqService.rfqFileDownload(id,response);
	}
	
	@RequestMapping(value = "/delete-file", method = RequestMethod.GET)
	public void deleteFile(Model model,@RequestParam("fileRefId")Integer refId) throws Exception {
		rfqService.rfqFileDelete(refId);
	}
	@RequestMapping(value = "/addfrompo", method = RequestMethod.GET)
	public String generatePOFromRFQItems(Model model, RedirectAttributes ra, @ModelAttribute("poItemIds") final String poItemIds) {
		try {
			RFQDTO dto = rfqService.createRFQFromPoItems(poItemIds);
			setCommonData(model, dto);
			return "inventory/rfq/add-view";
		} catch (Exception ex) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
            return "redirect:/rfq/index";
        }
    }
	
	@RequestMapping(value = "/generateRFQFromMrn", method = RequestMethod.GET)
	public @ResponseBody MRNResult generateRFQFromMrn(String ids, Integer mrnId) throws Exception {
		MRNResult result = null;
		if ((mrnId != null) && (mrnId > 0)) { 
			result = rfqService.generateRFQFromMrn(ids, mrnId); 
		}
		return result;
	}

	private void setCommonData(Model model, RFQDTO rfq) throws Exception {
		model.addAttribute("rfq", rfq);
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("suppliers", supplierService.findAllSupplierByUserLevel());
		model.addAttribute("sites", assetService.findAllSiteByLevel());
		model.addAttribute("countries", countryService.findAll());
		model.addAttribute("currencies", currencyService.findAll());
		model.addAttribute("shippingTypes", ShippingType.getShippingTypeList());
	}



	/**********************************************************
	 * Purchase Order Generation
	 *********************************************************/
	@RequestMapping(value = "/saveWithPurchaseOrder", method = RequestMethod.POST)
	public String saveOrUpdatesWithPurchaseOrder(@ModelAttribute("rfq") RFQDTO rfq, Model model, RedirectAttributes ra) {
		try {
			PurchaseOrderDTO dto = null;
			dto = rfqService.saveWithPurchaseOrder(rfq);
			model.addAttribute("purchaseOrder", dto);
			model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
			model.addAttribute("suppliers", supplierService.findAllSupplierByUserLevel());
			model.addAttribute("sites", assetService.findAllSiteByLevel());
			model.addAttribute("accounts", accountService.findAll());
			model.addAttribute("chargeDepartments", chargeDeparmentService.findAll());
			model.addAttribute("countries", countryService.findAll());
			model.addAttribute("currencies", currencyService.findAll());
			model.addAttribute("billingTerms", BillingTerm.getBillingTermList());
			model.addAttribute("poAdditionalCostTypes", PurchaseOrderAdditionalCostType.getAdditionalCostTypeList());
			model.addAttribute("shippingTypes", ShippingType.getShippingTypeList());
			return "inventory/purchaseorder/add-view";
		} catch (Exception ex) {
			ra.addFlashAttribute("error", "Error While Loading Initial Data.");
			return "inventory/rfq/add-view";
		}
	}



}
