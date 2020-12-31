package com.codex.ecam.controller.inventory.receiptOrder;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.inventory.ReceiptOrderStatus;
import com.codex.ecam.constants.inventory.ReceiptOrderType;
import com.codex.ecam.dto.inventory.receiptOrder.ReceiptOrderDTO;
import com.codex.ecam.result.RestResult;
import com.codex.ecam.result.purchasing.ReceiptOrderResult;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.biz.api.SupplierService;
import com.codex.ecam.service.inventory.api.ReceiptOrderService;

@Controller
@RequestMapping(ReceiptOrderController.REQUEST_MAPPING_URL)
public class ReceiptOrderController {

	public static final String REQUEST_MAPPING_URL = "/receiptorder";

    @Autowired
    private ReceiptOrderService receiptOrderService;

    @Autowired
    private BusinessService businessService;
    
    @Autowired
    private SupplierService supplierService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "inventory/receiptorder/home-view";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        return "inventory/receiptorder/home-view";
    }


    /*********************************************************************
     * Modal Views
     *********************************************************************/

    @RequestMapping(value = "/receiptItemView", method = RequestMethod.GET)
    public String getItemAddView(Model model) {
    	return "inventory/receiptorder/modal/item-modal";
    }
    
    @RequestMapping(value = "/receiptRefurbishItemView", method = RequestMethod.GET)
    public String getRefurbishItemAddView(Model model) {
        return "inventory/receiptorder/modal/refurbish-item-modal";
    }

    @RequestMapping(value = "/receiptAssetView", method = RequestMethod.GET)
    public String getAssetSelectView(Model model) {
    	return "inventory/receiptorder/modal/asset-modal";
    }
    
    @RequestMapping(value = "/receiptRefubishAssetView", method = RequestMethod.GET)
    public String getRefurbishAssetSelectView(Model model) {
        return "inventory/receiptorder/modal/asset-modal";
    }

    @RequestMapping(value = "/receiptStockView", method = RequestMethod.GET)
    public String getStockSelectView(Model model) {
    	return "inventory/receiptorder/modal/stock-modal";
    }
    @RequestMapping(value = "/receiptPurchaseorderItemView", method = RequestMethod.GET)
    public String getPurchaseOrderSelectView(Model model) {
        return "inventory/receiptorder/modal/purchaseorder-item-modal";
    }

	@RequestMapping(value = "/code-by-business", method = RequestMethod.GET)
	public @ResponseBody RestResult<String> codeByBusiness(Integer businessId) {
		RestResult<String> result = new RestResult<>();
		result.setData(receiptOrderService.getNextCode(businessId).toString());

		return result ;
	}

    /*********************************************************************
     * CRUD Ops
     *********************************************************************/

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, RedirectAttributes ra) {
        try {
            setCommonData(model, receiptOrderService.createNewReceiptOrder().getDtoEntity());
            return "inventory/receiptorder/add-view";
        } catch (Exception ex) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
            return "redirect:/receiptorder/index";
        }
    }

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Integer id, Model model, RedirectAttributes ra) {
        ReceiptOrderResult result = new ReceiptOrderResult(null, null);
        try {
            result = receiptOrderService.findById(id);
            setCommonData(model, result.getDtoEntity());

            return "inventory/receiptorder/add-view";
        } catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));

            return "redirect:/receiptorder/index";
        }
    }

	@RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveOrUpdate(@ModelAttribute("receiptOrder") ReceiptOrderDTO receiptOrder, Model model) {

        ReceiptOrderResult result;

        if ((receiptOrder.getId() != null) && (receiptOrder.getId() > 0)) {
            result = receiptOrderService.update(receiptOrder);
        } else {
            result = receiptOrderService.save(receiptOrder);
        }

        if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }
        setCommonData(model, result.getDtoEntity());
        return "inventory/receiptorder/add-view";
    }

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Integer id, Model model, RedirectAttributes ra) {
        ReceiptOrderResult result = receiptOrderService.delete(id);
        if (result.getStatus().equals(ResultStatus.ERROR)) {
            ra.addFlashAttribute("error", result.getErrorList());
        } else {
            ra.addFlashAttribute("success", result.getMsgList());
        }
        return "redirect:/receiptorder/index";
    }

    @RequestMapping(value = "/statusChange", method = RequestMethod.GET)
    public String receiptOrderStatusChange(Integer id, ReceiptOrderStatus receiptOrderStatus, Model model, RedirectAttributes ra) {
       // ReceiptOrderResult result = null;
    	ReceiptOrderResult result = receiptOrderService.statusChange(id, receiptOrderStatus);    
        if (result.getStatus().equals(ResultStatus.ERROR)) {
            ra.addFlashAttribute("error", result.getErrorList());
        } else {
            ra.addFlashAttribute("success", result.getMsgList());
        }

        setCommonData(model, result.getDtoEntity());
        return "inventory/receiptorder/add-view";
    }

	@RequestMapping(value = "/generateGrnFrom", method = RequestMethod.GET)
	public @ResponseBody ReceiptOrderResult generateGrnFromPo(String ids, Integer poId) throws Exception {
		ReceiptOrderResult result = null;
		if ((poId != null) && (poId > 0)) { 
			result = receiptOrderService.generateGrnFromPo(ids, poId); 
		}
		return result;
	}
	
    private void setCommonData(Model model, ReceiptOrderDTO receiptOrder) {
    	model.addAttribute("receiptOrder", receiptOrder);
        model.addAttribute("types", ReceiptOrderType.getAllReceiptOrderType());
        model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
        model.addAttribute("suppliers", supplierService.findAllSupplierByUserLevel());
    }

}
