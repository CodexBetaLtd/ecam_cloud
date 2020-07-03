package com.codex.ecam.controller.admin.tax;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.cmmssetting.tax.TaxDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.TaxService;


@RestController
@RequestMapping(TaxRestController.REQUEST_MAPPING_URL)
public class TaxRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/tax";
    
    @Autowired
    public TaxService taxService;
   
    @RequestMapping(value = {"/tabledata"}, method = RequestMethod.GET)
    public DataTablesOutput<TaxDTO> getAssetCategory(@Valid FocusDataTablesInput input) throws Exception {
        return taxService.findAll(input);
    }


}
