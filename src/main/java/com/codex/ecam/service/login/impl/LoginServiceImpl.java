package com.codex.ecam.service.login.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.codex.ecam.constants.NotificationType;
import com.codex.ecam.constants.UserLevel;
import com.codex.ecam.dao.admin.UserCredentialDao;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.biz.NotificationDao;
import com.codex.ecam.dto.admin.UserCredentialDTO;
import com.codex.ecam.dto.admin.UserTokenDTO;
import com.codex.ecam.dto.biz.notification.NotificationDTO;
import com.codex.ecam.mappers.admin.NotificationMapper;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.admin.UserCredential;
import com.codex.ecam.model.biz.notification.Notification;
import com.codex.ecam.result.LoginResult;
import com.codex.ecam.service.admin.api.UserCredentialService;
import com.codex.ecam.service.admin.api.UserService;
import com.codex.ecam.service.admin.api.UserTokenService;
import com.codex.ecam.service.login.api.LoginService;
import com.codex.ecam.service.maintenance.api.EmailAndNotificationSender;
import com.codex.ecam.service.notification.api.NotificationService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.VelocityEmailSender;

@Service
@PropertySource(value = {"classpath:common.properties"})
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserService userService;

	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserTokenService userTokenService;

	@Autowired
	private Environment environment;

	@Autowired
	private NotificationDao notificationDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserCredentialDao userCredentialDao;

	@Value("${common.url}")
	private String url;

	@Autowired
	private VelocityEmailSender velocityEmailSender;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public LoginResult sendResetEmail(String email) {

		final LoginResult result = new LoginResult();

		if (email != null && !email.isEmpty()) {

			try {
				final User user = userService.findByEmail(email);

				if (user != null) {

					final UserTokenDTO tokenDTO = new UserTokenDTO();
					final String tokenStr = createUserToken(user, tokenDTO);
					userTokenService.save(tokenDTO);
					final String appUrl = environment.getRequiredProperty("common.url") + "/resetPassword/add/userId="+user.getId()+",token=";
					velocityEmailSender.sendEmail(tokenStr, user, appUrl);
					result.setResultStatusSuccess();
					result.addToMessageList("Password reset url has sent into your mail.Please check for password reset");
				} else {
					result.setResultStatusError();
					result.addToErrorList("E mail Address is not registered.");
				}


			} catch (final Exception e) {

				e.printStackTrace();
				result.setResultStatusError();
				result.addToErrorList("Error Occured While Sending Email. Please Try again.");
			}

		} else {
			result.setResultStatusError();
			result.addToErrorList("Please Enter Valid Email address.");
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public LoginResult resetPassword(String token, Model model) {

		final LoginResult result = new LoginResult();

		try {
			final UserTokenDTO userToken = userTokenService.findbytoken(token);

			if (userToken == null) {

				result.setResultStatusError();
				result.addToErrorList("Password reset token not vaild.");

			} else if (userToken.getResetPasswordExpires() == new Date()) {

				result.setResultStatusError();
				result.addToErrorList("Password reset token expired.");

			} else {

				final UserCredentialDTO credentialDTO = userCredentialService.findByUserId(userToken.getUserId());
				userTokenService.delete(userToken.getId());
				model.addAttribute("credentialDTO", credentialDTO);
			}

		} catch (final Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("Error Occured While resetting password.");
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public LoginResult updatePassword(UserCredentialDTO credentialDTO) {

		final LoginResult result = new LoginResult();

		try {
			userCredentialService.update(credentialDTO);
			result.setResultStatusSuccess();
			result.addToMessageList("Password successfully updated");
		} catch (final Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("Error Occured While updating password.");
		}

		return result;
	}

	private String createUserToken(User user, UserTokenDTO tokenDTO) {
		// create token
		final String token = UUID.randomUUID().toString();

		// create token expire date
		Date date = new Date();
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, 1);
		date = c.getTime();

		// save token
		tokenDTO.setResetPasswordToken(token);
		tokenDTO.setResetPasswordExpires(date);
		tokenDTO.setUserId(user.getId());
		return token;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public LoginResult requestPasswordReset(String userName) {
		final LoginResult result = new LoginResult();

		try {
			final UserCredential uc = userCredentialDao.findByUserName(userName);
			if (uc != null) {
				final List<User> admins = getAdminUsers(uc);
				if (admins != null && admins.size() > 0) {
					sendNotificationToAdmins(uc.getUser(), admins);
					result.setResultStatusSuccess();
					result.addToMessageList("A request has been sent to your admin to reset your password. Please contact your admin.");

				} else {
					result.setResultStatusError();
					result.addToErrorList("Error occcured!");
				}
			} else {
				result.setResultStatusError();
				result.addToErrorList("Couldn't find account related username.");
			}

		} catch (final Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("Error Occured While requesting password reset.");
		}

		return result;
	}

	private void sendNotificationToAdmins(User user, List<User> admins) {
		AuthenticationUtil.setTriggerUser(user);
		for (final User adminUser : admins) {
			if (!user.getId().equals(adminUser.getId())) {
				final Notification domain = new Notification();
				domain.setSubject("Request for password reset");
				domain.setContent("Please take the neccessor action to reset password user of \'" + user.getFullName()
				+ "\' in \'" + user.getBusiness().getName() + "\' business");
				domain.setSystemMessage(true);
				domain.setNotificationType(NotificationType.INBOX_NOTIFICATION);
				domain.setSystemMessage(Boolean.TRUE);
				domain.setIsOpen(Boolean.FALSE);
				domain.setIsPopup(Boolean.FALSE);
				domain.setIsTrashed(Boolean.FALSE);
				domain.setIsDeleted(Boolean.FALSE);

				if (user.getBusiness() != null) {
					domain.setBusiness(user.getBusiness());
				}

				domain.setReceiver(userDao.findOne(adminUser.getId()));

				notificationDao.save(domain);
			}

		}
		AuthenticationUtil.setTriggerUser(null);
	}



	private List<User> getAdminUsers(UserCredential uc) {
		final Specification<User> specification = (root, query, cb)->{
			return cb.and(
					cb.equal(root.get("business").get("id"), uc.getUser().getBusiness().getId()),
					cb.equal(root.get("userLevel"), UserLevel.SYSTEM_LEVEL)
					);
		};
		final List<User> admins = userDao.findAll(specification);
		return admins;
	}

}
