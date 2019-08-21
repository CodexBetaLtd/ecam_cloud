package com.codex.ecam.controller.asset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codex.ecam.validation.asset.AssetCustomValidator;

@Controller
@RequestMapping(AssetValidationController.REQUEST_MAPPING_URL)
public class AssetValidationController {

    public static final String REQUEST_MAPPING_URL = "/validate/asset";

    @Autowired
    private AssetCustomValidator validator;

    @RequestMapping(value = "/assetCode", method = RequestMethod.GET)
    public @ResponseBody Boolean assetCodeValidate(@RequestParam(value = "id") Integer id, @RequestParam(value = "code") String code) {
        return validator.validateDuplicateAssetCode(id, code);
    }

    @RequestMapping(value = "/assetName", method = RequestMethod.GET)
    public @ResponseBody Boolean assetNameValidate(@RequestParam(value = "id") Integer id, @RequestParam(value = "name") String name) {
        return validator.validateDuplicateAssetName(id, name);
    }

}