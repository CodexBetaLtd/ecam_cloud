package com.neolith.focus.controller.inventory.aodReturn;

import com.neolith.focus.dto.inventory.aodReturn.AODReturnDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.inventory.api.AODReturnService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
