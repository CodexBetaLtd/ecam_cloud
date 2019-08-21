package com.codex.ecam.controller.inventory.aodReturn;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.inventory.aodReturn.AODReturnDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.inventory.api.AODReturnService;

import javax.validation.Valid;

@RestController
@RequestMapping(AODReturnRestController.REQUEST_MAPPING_URL)
public class AODReturnRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/aodReturn";

    @Autowired
    private AODReturnService aodReturnService;

    @RequestMapping(value = "/getAODReturnList", method = RequestMethod.GET)
    public DataTablesOutput<AODReturnDTO> getAODReturnList(@Valid FocusDataTablesInput input) {
        try {
            return aodReturnService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
