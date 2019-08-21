package com.codex.ecam.controller.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.CountryDTO;
import com.codex.ecam.dto.app.AppDTO;
import com.codex.ecam.dto.web.WebUserDetailDto;
import com.codex.ecam.service.admin.api.CountryService;
import com.codex.ecam.service.app.api.AppService;
import com.codex.ecam.service.web.api.WebUserService;

@RestController
@RequestMapping(WebRestController.REQUEST_MAPPING_URL)
public class WebRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/web";

	private final Logger logger = LoggerFactory.getLogger(WebRestController.class);

	@Autowired
	private AppService appService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private WebUserService webUserService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<WebUserDetailDto> login( @RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password) {
		logger.info("Login with UserName: {} ", userName);
		try {
			if (((userName == null) || userName.isEmpty()) || ((password == null) || password.isEmpty()) ) {
				return new ResponseEntity<WebUserDetailDto>(HttpStatus.BAD_REQUEST);
			}

			return webUserService.login(userName, password);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error Occured.");
			return new ResponseEntity<WebUserDetailDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<WebUserDetailDto> getUserDetail(@PathVariable("id") int id){
		logger.info("Getting user with id: {}", id);
		try {
			WebUserDetailDto userDetail = webUserService.findByUserId(id);
			if (userDetail == null){
				logger.info("User with id {} not found", id);
				return new ResponseEntity<WebUserDetailDto>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<WebUserDetailDto>(userDetail, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error Occured.");
			return new ResponseEntity<WebUserDetailDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/users/{id}/credential", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
	public ResponseEntity<Void> updateUserCredential(@PathVariable Integer id, WebUserDetailDto userDetail ) {
		logger.info("updating user Credential : {}", userDetail);

		if ( !webUserService.isUserAvailable(id) ){
			logger.info("User with id {} not found", id);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

		return webUserService.updateUserCredential(id, userDetail);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
	public ResponseEntity<WebUserDetailDto> updateUser(@PathVariable Integer id, WebUserDetailDto userDetail ) {
		logger.info("updating user: {}", userDetail);		try {
			if ( !webUserService.isUserAvailable(id) ){
				logger.info("User with id {} not found", id);
				return new ResponseEntity<WebUserDetailDto>(HttpStatus.NOT_FOUND);
			}
			webUserService.updateUser(id, userDetail);

			return new ResponseEntity<WebUserDetailDto>(userDetail, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error Occured.");
			return new ResponseEntity<WebUserDetailDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
	public ResponseEntity<Void> addUser( WebUserDetailDto userDetail ) {
		logger.info("Creating new User: {}", userDetail);
		try {
			if ( webUserService.exists(userDetail) ){
				logger.info("User with name " + userDetail.getUserName() + " already exists");
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			webUserService.addUser(userDetail);

			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error Occured.");
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/apps", method = RequestMethod.GET)
	public ResponseEntity<List<AppDTO>> getAllApps() {
		logger.info("Getting all Apps");
		try {
			List<AppDTO> apps = appService.findAllApps();
			if ((apps == null) || apps.isEmpty()){
				logger.info("No Apps found");
				return new ResponseEntity<List<AppDTO>>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<List<AppDTO>>(apps, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error Occured.");
			return new ResponseEntity<List<AppDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/countries", method = RequestMethod.GET)
	public ResponseEntity<List<CountryDTO>> countries() {
		logger.info("Getting all Countries");
		try {
			List<CountryDTO> countries = countryService.findAllCountries();
			if ((countries == null) || countries.isEmpty()){
				logger.info("no Country found");
				return new ResponseEntity<List<CountryDTO>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<CountryDTO>>(countries, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error Occured.");
			return new ResponseEntity<List<CountryDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/mail", method = RequestMethod.GET)
	public ResponseEntity<Void> senMail(@RequestParam(value = "email") String email, @RequestParam(value = "username") String username, @RequestParam(value = "password")String password, @RequestParam(value = "fullname")String fullname){
		logger.info("Sending an email");
		try { 
			webUserService.sendMail(email, username, password, fullname);
			return new ResponseEntity<Void> (HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error Occured.");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
