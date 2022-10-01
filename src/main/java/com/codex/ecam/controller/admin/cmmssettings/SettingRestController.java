package com.codex.ecam.controller.admin.cmmssettings;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.dto.admin.cmmssetting.UserSiteDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.UserService;
import com.codex.ecam.service.admin.api.UserSiteService;

import javax.validation.Valid;


@RestController
@RequestMapping(SettingRestController.REQUEST_MAPPING_URL)
public class SettingRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/setting";

    @Autowired
    private UserSiteService userSiteService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/userSiteTable", method = {RequestMethod.GET})
    public DataTablesOutput<UserSiteDTO> getUserList(@Valid FocusDataTablesInput dataTablesInput) {
        try {
            return userSiteService.findAll(dataTablesInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataTablesOutput<UserSiteDTO>();
    }

    @RequestMapping(value = "/siteUserTable", method = {RequestMethod.GET})
    public DataTablesOutput<UserDTO> getSiteUserList(@Valid FocusDataTablesInput dataTablesInput, Integer id) {
        try {
            return userService.findUsersBySite(dataTablesInput, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataTablesOutput<UserDTO>();
    }
}
