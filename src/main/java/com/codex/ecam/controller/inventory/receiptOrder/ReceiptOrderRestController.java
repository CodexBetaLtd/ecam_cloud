package com.codex.ecam.controller.inventory.receiptOrder;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.inventory.receiptOrder.ReceiptOrderDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.inventory.api.ReceiptOrderService;

import javax.validation.Valid;

@RestController
@RequestMapping(ReceiptOrderRestController.REQUEST_MAPPING_URL)
public class ReceiptOrderRestController {
	
	public static final String REQUEST_MAPPING_URL = "restapi/receiptorder";
	
	@Autowired
    private ReceiptOrderService receiptOrderService;

    @RequestMapping(value = "/getReceiptOrderData", method = RequestMethod.GET)
    public DataTablesOutput<ReceiptOrderDTO> findAllReceipts(@Valid FocusDataTablesInput input) throws Exception {
        try {
        	return receiptOrderService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }

}
