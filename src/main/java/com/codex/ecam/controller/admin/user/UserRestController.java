package com.codex.ecam.controller.admin.user; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.CertificationDTO;
import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.CertificationService;
import com.codex.ecam.service.admin.api.UserService;
import com.codex.ecam.util.FileDownloadUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 

@RestController
@RequestMapping(UserRestController.REQUEST_MAPPING_URL)
public class UserRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/users";

	private final String USER_DEFAULT_IMAGE = "/resources/images/user-default.png";

	@Autowired
	private UserService userService;

	@Autowired
	private CertificationService certificationService;


	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	public List<UserDTO> getUserList() {
		try {
			return userService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

/*
    @RequestMapping(value = "/tabledata", method = {RequestMethod.GET})
    public DataTablesOutput<UserDTO> users(@Valid DataTablesInput input) throws Exception {
        return userService.findAll(input);
    }
*/

    @RequestMapping(value = "/getuserlist", method = {RequestMethod.GET})
    public DataTablesOutput<UserDTO> getUserListTableData(@Valid FocusDataTablesInput input) throws Exception {
        return userService.findAll(input);
    }

	@RequestMapping(value = "/usersbygroupid", method = {RequestMethod.GET})
	public DataTablesOutput<UserDTO> getUserListByGroupId(@Valid FocusDataTablesInput dataTablesInput, Integer id) {
		try {
			return userService.findAllUsersByGroup(dataTablesInput, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DataTablesOutput<UserDTO>();
	}

	@RequestMapping(value = "/usersbybusinessid", method = {RequestMethod.GET})
	public DataTablesOutput<UserDTO> getUsersByBusinessId(@Valid FocusDataTablesInput dataTablesInput, Integer id) {
		try {
			return userService.findAllByBusiness(dataTablesInput, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DataTablesOutput<UserDTO>();
	}

	@RequestMapping(value = "/businessUserList", method = {RequestMethod.GET})
	public DataTablesOutput<UserDTO> getUsersByBusiness(@Valid FocusDataTablesInput dataTablesInput) {
		try {
			return userService.findAllUsersByLoggedUserBusiness(dataTablesInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DataTablesOutput<UserDTO>();
	}

	@RequestMapping(value = "/siteusers", method = {RequestMethod.GET})
	public DataTablesOutput<UserDTO> getUsersBySite(@Valid FocusDataTablesInput dataTablesInput, Integer id) {
		try {
			return userService.findUsersBySite(dataTablesInput, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DataTablesOutput<UserDTO>();
	}

    @RequestMapping(value = "/getCertificationType", method = {RequestMethod.GET})
	public DataTablesOutput<CertificationDTO>  getCertificationType(@Valid FocusDataTablesInput dataTablesInput) {
		try {
			return certificationService.findAll(dataTablesInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DataTablesOutput<CertificationDTO>();
	}
    
    @RequestMapping(value = "/my-avatar", method = {RequestMethod.GET})
    public @ResponseBody byte[] getUserAvatar(Integer id, HttpServletRequest request) throws IOException {
		try {
			return userService.getUserAvatar(id, request);
		} catch (Exception e) {
			return FileDownloadUtil.getByteInputStream( request.getServletContext().getRealPath("").concat(USER_DEFAULT_IMAGE));
		} 
    }

}
