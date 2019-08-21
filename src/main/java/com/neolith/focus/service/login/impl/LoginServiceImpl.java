package com.neolith.focus.service.login.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.neolith.focus.dto.admin.UserCredentialDTO;
import com.neolith.focus.dto.admin.UserTokenDTO;
import com.neolith.focus.model.admin.User;
import com.neolith.focus.result.LoginResult;
import com.neolith.focus.service.admin.api.UserCredentialService;
import com.neolith.focus.service.admin.api.UserService;
import com.neolith.focus.service.admin.api.UserTokenService;
import com.neolith.focus.service.login.api.LoginService;
import com.neolith.focus.util.VelocityEmailSender;

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

    @Value("${common.url}")
    private String url;

    @Autowired
    private VelocityEmailSender velocityEmailSender;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LoginResult sendResetEmail(String email) {

        LoginResult result = new LoginResult();

        if (email != null && !email.isEmpty()) {

            try {
                User user = userService.findByEmail(email);

                if (user != null) {

                    UserTokenDTO tokenDTO = new UserTokenDTO();
                    String tokenStr = createUserToken(user, tokenDTO);
                    userTokenService.save(tokenDTO);
                    String appUrl = environment.getRequiredProperty("common.url") + "/resetPassword/add/userId="+user.getId()+",token=";
                    velocityEmailSender.sendEmail(tokenStr, user, appUrl);
        			result.setResultStatusSuccess();
        			result.addToMessageList("Password reset url has sent into your mail.Please check for password reset");
                    } else {
                	result.setResultStatusError();
                    result.addToErrorList("E mail Address is not registered.");
                }


            } catch (Exception e) {

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

        LoginResult result = new LoginResult();

        try {
            UserTokenDTO userToken = userTokenService.findbytoken(token);

            if (userToken == null) {

                result.setResultStatusError();
                result.addToErrorList("Password reset token not vaild.");

            } else if (userToken.getResetPasswordExpires() == new Date()) {

                result.setResultStatusError();
                result.addToErrorList("Password reset token expired.");

            } else {

                UserCredentialDTO credentialDTO = userCredentialService.findByUserId(userToken.getUserId());
                userTokenService.delete(userToken.getId());
                model.addAttribute("credentialDTO", credentialDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setResultStatusError();
            result.addToErrorList("Error Occured While resetting password.");
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LoginResult updatePassword(UserCredentialDTO credentialDTO) {

        LoginResult result = new LoginResult();

        try {
            userCredentialService.update(credentialDTO);
            result.setResultStatusSuccess();
            result.addToMessageList("Password successfully updated");
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultStatusError();
            result.addToErrorList("Error Occured While updating password.");
        }

        return result;
    }

    private String createUserToken(User user, UserTokenDTO tokenDTO) {
        // create token
        String token = UUID.randomUUID().toString();

        // create token expire date
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, 1);
        date = c.getTime();

        // save token
        tokenDTO.setResetPasswordToken(token);
        tokenDTO.setResetPasswordExpires(date);
        tokenDTO.setUserId(user.getId());
        return token;
    }

}
