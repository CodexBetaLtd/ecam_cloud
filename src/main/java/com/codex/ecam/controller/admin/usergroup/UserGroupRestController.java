package com.codex.ecam.controller.admin.usergroup;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.PermisonTreeDTO;
import com.codex.ecam.dto.admin.usergroup.UserGroupDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.UserGroupService;

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
    
    @RequestMapping(value = "/getMenuPermmision", method = RequestMethod.GET)
    public List<PermisonTreeDTO>  findAllPerMenus(Integer id) {
    	try {
    		return userGroupService.getMenuPermissionsAll(id);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    @RequestMapping(value = "/getAllMenu", method = RequestMethod.GET)
    public List<PermisonTreeDTO>  findAllMenus() {
    	try {
    		return userGroupService.getMenuAll();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    @RequestMapping(value = "/getAllSubMenuById", method = RequestMethod.GET)
    public List<PermisonTreeDTO>  findAllSubMenus(Integer id) {
    	try {
    		return userGroupService.getSubMenuAll(id);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    @RequestMapping(value = "/getAllPageById", method = RequestMethod.GET)
    public List<PermisonTreeDTO>  findAllPage(Integer id) {
    	try {
    		return userGroupService.getPageAll(id);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    @RequestMapping(value = "/getAllPagePermissionById", method = RequestMethod.GET)
    public List<PermisonTreeDTO>  findAllPermissionPage(Integer id) {
        try {
            return userGroupService.getPagePermistionAll(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
