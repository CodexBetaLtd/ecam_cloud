package com.neolith.focus.service.web.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.constants.UserLevel;
import com.neolith.focus.dao.admin.CountryDao;
import com.neolith.focus.dao.admin.UserCredentialDao;
import com.neolith.focus.dao.admin.UserDao;
import com.neolith.focus.dao.app.AppDao;
import com.neolith.focus.dao.biz.BusinessDao;
import com.neolith.focus.dto.web.WebUserDetailDto;
import com.neolith.focus.model.admin.User;
import com.neolith.focus.model.admin.UserCredential;
import com.neolith.focus.model.app.App;
import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.model.biz.business.BusinessApp;
import com.neolith.focus.params.VelocityMail;
import com.neolith.focus.service.web.api.WebUserService;
import com.neolith.focus.util.AuthenticationUtil;
import com.neolith.focus.util.VelocityEmailSender;

@Service
public class WebUserServiceImpl implements WebUserService {

	final static Logger logger = LoggerFactory.getLogger(WebUserServiceImpl.class);

	@Autowired
	private UserCredentialDao userCredentialDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CountryDao countryDao;

	@Autowired
	private AppDao appDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private VelocityEmailSender velocityEmailSender;

	@Override
	public ResponseEntity<WebUserDetailDto> login(String userName, String password) throws Exception {

		logger.info("Trying to login UserName : " + userName + " and Password : " + password);
		UserCredential userCredential = userCredentialDao.findByUserName(userName);

		if ( (userCredential != null)  ) {
			if ( passwordEncoder.matches(password, userCredential.getPassword()) ) {
				logger.info("Successfully logged in for user : " + userName);
				return new ResponseEntity<WebUserDetailDto>(getWebUserDetail(userCredential), HttpStatus.OK);
			} else {
				logger.info("Password you entered is not correct.");
				return new ResponseEntity<WebUserDetailDto>(HttpStatus.UNAUTHORIZED);
			}
		} else {
			logger.info("Cannot found a user for given username.");
			return new ResponseEntity<WebUserDetailDto>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public boolean exists(WebUserDetailDto userDetail) throws Exception {
		UserCredential userCredential = userCredentialDao.findByUserName(userDetail.getUserName());
		return userCredential != null ? true : false ;
	}

	@Override
	public WebUserDetailDto findByUserId(Integer userId) throws Exception {
		UserCredential userCredential = userCredentialDao.findByUserId(userId);
		if ( (userCredential != null)  ) {
			return getWebUserDetail(userCredential);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addUser(WebUserDetailDto userDetail) throws Exception {
		Integer businessId = createBusiness(userDetail);
		User user = createUser(userDetail, businessDao.findOne(businessId));
		AuthenticationUtil.setTriggerUser(user);
		userDao.save(user);
		userDetail.setUserId(user.getId());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseEntity<Void> updateUserCredential(Integer userId, WebUserDetailDto userDetail) {
		try {
			User user = userDao.findById(userId);
			if ( passwordEncoder.matches(userDetail.getPrePassword(), user.getUserCredential().getPassword()) ) {
				setUserCredentialData(user.getUserCredential(), userDetail, user);
				AuthenticationUtil.setTriggerUser(user);
				userDao.save(user);

				return new ResponseEntity<Void>(HttpStatus.OK);
			} else {

				return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error Occured.");

			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateUser(Integer userId, WebUserDetailDto userDetail) throws Exception {
		User user = userDao.findById(userId);
		Business business = user.getBusiness();
		setUserData(user, userDetail, business);
		setBusinessData(business, userDetail);
		setBusinessAppData(business);
		user.setBusiness(business);
		AuthenticationUtil.setTriggerUser(user);

		userDao.save(user);
	}

	@Override
	public boolean isUserAvailable(Integer id) {
		if ( (id != null) && (id > 0)) {
			return userDao.exists(id);
		}
		return false;
	}

	private WebUserDetailDto getWebUserDetail(UserCredential userCredential) {
		WebUserDetailDto data = new WebUserDetailDto();
		User user = userCredential.getUser();
		data.setFullName(user.getFullName());
		data.setEmail(user.getEmailAddress());
		data.setPhone(user.getTelephone1());
		data.setUserId(user.getId());
		data.setUserName(userCredential.getUserName());
		data.setVersion(user.getVersion());

		if ( user.getBusiness() != null ) {
			data.setCompanyName(user.getBusiness().getName());
			data.setCountryId(user.getBusiness().getCountry().getId());
			data.setCountryName(user.getBusiness().getCountry().getName());
			data.setApps(createAppList(user.getBusiness().getBusinessApps()));
		}

		return data;
	}

	private List<Integer> createAppList(Set<BusinessApp> businessApps) {
		List<Integer> apps = new ArrayList<>();
		for ( BusinessApp app : businessApps ) {
			apps.add(app.getApp().getId());
		}

		return apps;
	}

	private User createUser(WebUserDetailDto userDetail, Business business) {
		User user =  new User();
		setUserData(user, userDetail, business);
		user.setUserCredential(createUserCredential(user, userDetail));

		return user;
	}

	private UserCredential createUserCredential(User user, WebUserDetailDto userDetail) {
		UserCredential userCredential = new UserCredential();
		setUserCredentialData(userCredential, userDetail, user);

		return userCredential;
	}

	private synchronized Integer createBusiness(WebUserDetailDto userDetail) {
		Business business = new Business();
		setBusinessData(business, userDetail);
		setBusinessAppData(business);
		businessDao.save(business);

		return business.getId();
	}

	private void setBusinessAppData(Business business) {
		Set<BusinessApp> apps = new HashSet<>();
		List<App> currentApps = (List<App>) appDao.findAll();

		BusinessApp bApp;

		for (App app : currentApps ) {
			bApp = createBusinessApp(business, app);
			apps.add(bApp);
		}

		business.setBusinessApps(apps);
	}

	//	private void setBusinessAppData(User user, WebUserDetailDto userDetail, Business business) {
	//		Set<BusinessApp> apps = new HashSet<>();
	//
	//		if ((userDetail.getApps() != null) && (userDetail.getApps().size() > 0)) {
	//
	//			Set<BusinessApp> currentApps = business.getBusinessApps();
	//
	//			BusinessApp app;
	//
	//			for (Integer appId : userDetail.getApps()) {
	//
	//				if ((currentApps != null) && (currentApps.size() > 0)) {
	//
	//					Optional<BusinessApp> optionalApp = currentApps.stream().filter((x) -> x.getApp().getId() == appId).findAny();
	//
	//					if (optionalApp.isPresent()) {
	//						app = optionalApp.get();
	//					} else {
	//						app = createBusinessApp(business, appId);
	//					}
	//				} else {
	//					app = createBusinessApp(business, appId);
	//				}
	//
	//				apps.add(app);
	//			}
	//		}
	//
	//		business.setBusinessApps(apps);
	//	}

	private void setUserCredentialData(UserCredential userCredential, WebUserDetailDto userDetail, User user) {
		userCredential.setUser(user);
		userCredential.setPassword(passwordEncoder.encode(userDetail.getPassword()));
		userCredential.setUserName(userDetail.getUserName());
		userCredential.setIsDeleted(false);
	}

	private void setBusinessData(Business business, WebUserDetailDto userDetail) {
		business.setName(userDetail.getCompanyName());
		business.setCountry(countryDao.findById(userDetail.getCountryId()));
		business.setIsDeleted(false);
	}

	private void setUserData(User user, WebUserDetailDto userDetail, Business business) {
		user.setId(userDetail.getUserId());
		user.setFullName(userDetail.getFullName());
		user.setActive(true);
		user.setBusiness(business);
		user.setEmailAddress(userDetail.getEmail());
		user.setTelephone1(userDetail.getPhone());
		user.setUserLevel(UserLevel.SYSTEM_LEVEL);
		user.setIsDeleted(false);
		user.setVersion(userDetail.getVersion());
	}

	private BusinessApp createBusinessApp(Business business, App app) {
		BusinessApp bApp = new BusinessApp();
		bApp.setApp(app);
		bApp.setBusiness(business);
		bApp.setIsDeleted(false);
		return bApp;
	}

	@Override
	public void sendMail(String email, String userName, String password, String fullname) throws Exception {
		velocityEmailSender.sendEmail(createRegistrationSuccessEmail(email, userName, password, fullname));
	}

	private VelocityMail createRegistrationSuccessEmail(String email, String userName, String password, String fullname) {
		VelocityMail velocityMail = new VelocityMail();
		velocityMail.getModel().put("fullname", fullname);
		velocityMail.getModel().put("username", userName);
		velocityMail.getModel().put("password", password);
		velocityMail.setSubject("Registration Successful");
		velocityMail.setTo(email);
		velocityMail.setVmTemplate("userregistrationsuccess");
		return velocityMail;
	}

}
