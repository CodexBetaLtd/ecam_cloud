package com.codex.ecam.controller.inventory.receiptOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.inventory.ReceiptOrderStatus;
import com.codex.ecam.dto.inventory.receiptOrder.ReceiptOrderDTO;
import com.codex.ecam.result.purchasing.ReceiptOrderResult;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.inventory.api.ReceiptOrderService;

import java.util.ArrayList;

@Controller
@RequestMapping(ReceiptOrderController.REQUEST_MAPPING_URL)
public class ReceiptOrderController {

	public static final String REQUEST_MAPPING_URL = "/receiptorder";

    @Autowired
    private ReceiptOrderService receiptOrderService;

    @Autowired
    private BusinessService businessService;

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

    @RequestMapping(value = "/receiptAssetView", method = RequestMethod.GET)
    public String getAssetSelectView(Model model) {
        return "inventory/receiptorder/modal/asset-modal";
    }

    @RequestMapping(value = "/receiptStockView", method = RequestMethod.GET)
    public String getStockSelectView(Model model) {
        return "inventory/receiptorder/modal/stock-modal";
    }


    /*********************************************************************
     * CRUD Ops
     *********************************************************************/

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, RedirectAttributes ra) {
        try {
            setCommonData(model, new ReceiptOrderDTO());
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
        setCommonData(model, receiptOrder);
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
        ReceiptOrderResult result = new ReceiptOrderResult(null, null);
        if ((id != null) && (id > 0)) {
            try {
                result = receiptOrderService.statusChange(id, receiptOrderStatus);
                model.addAttribute("success", new ArrayList<String>().add("Successfully updated the status"));
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
            }
        } else {
            model.addAttribute("error", new ArrayList<String>().add("Please Save the RFQ first"));
        }

        setCommonData(model, result.getDtoEntity());
        return "inventory/receiptorder/add-view";
    }

    private void setCommonData(Model model, ReceiptOrderDTO receiptOrder) {
        model.addAttribute("receiptOrder", receiptOrder);
        model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
    }

}