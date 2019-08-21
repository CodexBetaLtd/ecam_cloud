package com.neolith.focus.controller.inventory.purchaseOrder;

import com.neolith.focus.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.inventory.api.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(PurchaseOrderRestController.REQUEST_MAPPING_URL)
public class PurchaseOrderRestController {
	
	public static final String REQUEST_MAPPING_URL = "restapi/purchaseorder";
	
	@Autowired
    private PurchaseOrderService purchaseOrderService;

    @RequestMapping(value = "/tableData", method = RequestMethod.GET)
    public DataTablesOutput<PurchaseOrderDTO> findAllWorkOrder(@Valid FocusDataTablesInput dataTablesInput) throws Exception {
        try {
        	return purchaseOrderService.findAll(dataTablesInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }

}
