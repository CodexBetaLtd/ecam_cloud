package com.codex.ecam.controller.inventory.purchaseOrder;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderItemDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.inventory.api.PurchaseOrderService;

import javax.validation.Valid;

@RestController
@RequestMapping(PurchaseOrderRestController.REQUEST_MAPPING_URL)
public class PurchaseOrderRestController {
	
	public static final String REQUEST_MAPPING_URL = "restapi/purchaseorder";
	
	@Autowired
    private PurchaseOrderService purchaseOrderService;

	@RequestMapping(value = "/tableData", method = RequestMethod.GET)
	public DataTablesOutput<PurchaseOrderDTO> findAllPurchaseorder(@Valid FocusDataTablesInput dataTablesInput) throws Exception {
		try {
			return purchaseOrderService.findAll(dataTablesInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
    @RequestMapping(value = "/tableApprovedItemData", method = RequestMethod.GET)
    public DataTablesOutput<PurchaseOrderItemDTO> findAllPurchaseorderItem(@Valid FocusDataTablesInput dataTablesInput) throws Exception {
        try {
        	return purchaseOrderService.findAllApproved(dataTablesInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }

}
