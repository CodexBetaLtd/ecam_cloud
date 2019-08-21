package com.neolith.focus.controller.inventory.inventoryGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neolith.focus.validation.inventory.part.InventoryGroupCustomValidator;

@Controller
@RequestMapping(InventoryGroupValidationController.REQUEST_MAPPING_URL)
public class InventoryGroupValidationController {

    public static final String REQUEST_MAPPING_URL = "/validate/inventorygroup";

    @Autowired
    private InventoryGroupCustomValidator validator;

    @RequestMapping(value = "/inventoryGroupCode", method = RequestMethod.GET)
    public
    @ResponseBody
    Boolean assetCodeValidate(@RequestParam(value = "id") Integer id, @RequestParam(value = "inventoryGroupNo") String code) {
        return validator.validateDuplicateCode(id, code);
    }

    @RequestMapping(value = "/inventoryGroupName", method = RequestMethod.GET)
    public
    @ResponseBody
    Boolean assetNameValidate(@RequestParam(value = "id") Integer id, @RequestParam(value = "name") String name) {
        return validator.validateDuplicateName(id, name);
    }

}