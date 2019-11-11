package com.codex.ecam.controller.inventory.mrn;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.inventory.mrn.MRNDTO;
import com.codex.ecam.dto.inventory.mrn.MRNItemDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.inventory.api.MRNService;

@RestController
@RequestMapping(MRNRestController.REQUEST_MAPPING_URL)
public class MRNRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/mrn";

    @Autowired
    private MRNService mrnService;

    @RequestMapping(value = "/tabledata", method = RequestMethod.GET)
    public DataTablesOutput<MRNDTO> getMRNDataTable(@Valid FocusDataTablesInput input) {
        try {
            return mrnService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/approvedmrn", method = RequestMethod.GET)
    public DataTablesOutput<MRNDTO> getApprovedMRNDataTable(@Valid FocusDataTablesInput input) {
    	try {
    		return mrnService.findAllApprovedMRN(input);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    @RequestMapping(value = "/mrnitem", method = RequestMethod.GET)
    public DataTablesOutput<MRNItemDTO> getMRNItemDataTable(@Valid FocusDataTablesInput input,@Valid Integer mrnId) {
        try {
           return mrnService.getMRNItemDataTable(input,mrnId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
