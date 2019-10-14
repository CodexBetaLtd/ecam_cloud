package com.codex.ecam.controller.app;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.app.AppDTO;
import com.codex.ecam.dto.app.MenuDTO;
import com.codex.ecam.dto.app.WigetDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.app.api.AppService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(AppRestController.REQUEST_MAPPING_URL)
public class AppRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/app";

    @Autowired
    private AppService appService;

    @RequestMapping(value = "/tabledata", method = RequestMethod.GET)
    public DataTablesOutput<AppDTO> getApps(@Valid FocusDataTablesInput input) {
        try {
            return appService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public List<MenuDTO> getMenus() {
    	try {
    		return appService.findAllMenus();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    @RequestMapping(value = "/wigets", method = RequestMethod.GET)
    public List<WigetDTO> getWigets() {
        try {
            return appService.findAllWigets();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
