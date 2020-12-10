package com.codex.ecam.controller.maintenance.exworkorder;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.dto.maintenance.exworkorder.ExWorkOrderDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.UserGroupService;
import com.codex.ecam.service.admin.api.UserSiteService;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.maintenance.api.ExWorkOrderService;


@RestController
@RequestMapping(ExWorkOrderRestController.REQUEST_MAPPING_URL)
public class ExWorkOrderRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/exworkorder";

    @Autowired
    public UserGroupService userGroupService;
    
    @Autowired
    public ExWorkOrderService exWorkOrderService;
    
    @Autowired
    public UserSiteService userSiteService;


	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
    public DataTablesOutput<ExWorkOrderDTO> findall(@Valid FocusDataTablesInput input) {
        try {
        	return exWorkOrderService.findAll(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
    }


}
