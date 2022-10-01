package com.codex.ecam.controller.inventory.mrnReturn;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.inventory.aod.AODItemDTO;
import com.codex.ecam.dto.inventory.mrnReturn.MRNReturnDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.inventory.api.MRNReturnService;

@RestController
@RequestMapping(MRNReturnRestController.REQUEST_MAPPING_URL)
public class MRNReturnRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/mrnReturn";

    @Autowired
    private MRNReturnService mrnReturnService;

    @RequestMapping(value = "/tabledata", method = RequestMethod.GET)
    public DataTablesOutput<MRNReturnDTO> getMRNDataTable(@Valid FocusDataTablesInput input) {
        try {
            return mrnReturnService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/approvedmrn", method = RequestMethod.GET)
    public DataTablesOutput<AODItemDTO> getAODItemDataTable(@Valid FocusDataTablesInput input, @Valid Integer id) {
        try {
          // return mrnReturnService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
