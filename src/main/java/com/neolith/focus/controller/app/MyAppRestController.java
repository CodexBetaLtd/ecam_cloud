package com.neolith.focus.controller.app;

import com.neolith.focus.dto.app.AppDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.app.api.AppService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(MyAppRestController.REQUEST_MAPPING_URL)
public class MyAppRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/myapp";

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

    @RequestMapping(value = "/isAppInstalled", method = RequestMethod.GET)
    public Boolean isAppInstalled(Integer appId) {
        return appService.isAppInstalled(appId);
    }
}
