package com.neolith.focus.controller.inventory.aod;

import com.neolith.focus.dto.inventory.aod.AODDTO;
import com.neolith.focus.dto.inventory.aod.AODItemDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.inventory.api.AODService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(AODRestController.REQUEST_MAPPING_URL)
public class AODRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/aod";

    @Autowired
    private AODService aodService;

    @RequestMapping(value = "/tabledata", method = RequestMethod.GET)
    public DataTablesOutput<AODDTO> getAODDataTable(@Valid FocusDataTablesInput input) {
        try {
            return aodService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/getAODItemDataTable", method = RequestMethod.GET)
    public DataTablesOutput<AODItemDTO> getAODItemDataTable(@Valid FocusDataTablesInput input, @Valid Integer id) {
        try {
            return aodService.findAll(input, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
