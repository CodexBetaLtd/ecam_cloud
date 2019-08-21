package com.codex.ecam.controller.admin.usergroup;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.UserGroupDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.UserGroupService;

import javax.validation.Valid;

@RestController
@RequestMapping(UserGroupRestController.REQUEST_MAPPING_URL)
public class UserGroupRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/userGroups";

    @Autowired
    private UserGroupService userGroupService;


    @RequestMapping(value = "/tabledata", method = RequestMethod.GET)
    public DataTablesOutput<UserGroupDTO> findAllUserGroups(@Valid FocusDataTablesInput input) {
        try {
            return userGroupService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
