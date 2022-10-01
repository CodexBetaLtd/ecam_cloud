package com.codex.ecam.controller.inventory.bom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.inventory.bom.BOMGroupPartDTO;
import com.codex.ecam.service.inventory.api.BOMGroupPartService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(BOMGroupPartRestController.REQUEST_MAPPING_URL)
public class BOMGroupPartRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/bomgrouppart";

    @Autowired
    private BOMGroupPartService bomGroupPartService;

    @RequestMapping(value = "/partsbybomgroupid", method = RequestMethod.GET)
    public List<BOMGroupPartDTO> findBOMGroupPartByGroupId(@ModelAttribute("bomId") @Valid Integer bomId) {
        try {
            return bomGroupPartService.findByBomGroupId(bomId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
