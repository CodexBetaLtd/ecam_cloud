package com.codex.ecam.controller.inventory.rfq;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.inventory.rfq.RFQDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.inventory.api.RFQService;

import javax.validation.Valid;

@RestController
@RequestMapping(RFQRestController.REQUEST_MAPPING_URL)
public class RFQRestController {
	public static final String REQUEST_MAPPING_URL = "restapi/rfq";
	
	@Autowired
    private RFQService rfqService;

    @RequestMapping(value = "/tableData", method = RequestMethod.GET)
    public DataTablesOutput<RFQDTO> findAllRFQ(@Valid FocusDataTablesInput dataTablesInput){
        try {
        	return rfqService.findAll(dataTablesInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }


}
