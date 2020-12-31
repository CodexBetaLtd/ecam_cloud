package com.codex.ecam.controller.inventory.inventoryGroup;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.inventory.inventoryGroup.InventoryGroupDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.inventory.api.InventoryGroupService;

@RestController
@RequestMapping(InventoryGroupRestControler.REQUEST_MAPPING_URL)
public class InventoryGroupRestControler {

    public static final String REQUEST_MAPPING_URL = "restapi/inventorygroup";

    @Autowired
    private InventoryGroupService inventoryGroupService;

    @RequestMapping(value = "/getDataTable", method = RequestMethod.GET)
    public DataTablesOutput<InventoryGroupDTO> getInventoryDataTable(@Valid FocusDataTablesInput input) {
        try {
            return inventoryGroupService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
