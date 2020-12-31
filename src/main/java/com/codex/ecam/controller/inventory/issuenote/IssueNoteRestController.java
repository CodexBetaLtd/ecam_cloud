package com.codex.ecam.controller.inventory.issuenote;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.inventory.aod.AODDTO;
import com.codex.ecam.dto.inventory.aod.AODItemDTO;
import com.codex.ecam.dto.inventory.issuenote.IssueNoteDTO;
import com.codex.ecam.dto.inventory.issuenote.IssueNoteItemDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.inventory.api.AODService;
import com.codex.ecam.service.inventory.api.IssueNoteService;

import javax.validation.Valid;

@RestController
@RequestMapping(IssueNoteRestController.REQUEST_MAPPING_URL)
public class IssueNoteRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/issuenote";

    @Autowired
    private IssueNoteService issueNoteService;
    
    @RequestMapping(value = "/tabledata", method = RequestMethod.GET)
    public DataTablesOutput<IssueNoteDTO> getIssuenoteDataTable(@Valid FocusDataTablesInput input) {
    	try {
    		return issueNoteService.findAll(input);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    @RequestMapping(value = "/tableissuenoteitem", method = RequestMethod.GET)
    public DataTablesOutput<IssueNoteItemDTO> getIssueNoteItemDataTable(@Valid FocusDataTablesInput input) {
        try {
            return issueNoteService.getIssuenoteItemDataTable(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
