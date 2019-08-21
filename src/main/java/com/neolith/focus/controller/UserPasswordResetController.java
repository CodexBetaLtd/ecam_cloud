package com.neolith.focus.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.dto.admin.UserCredentialDTO;
import com.neolith.focus.result.LoginResult;
import com.neolith.focus.service.admin.api.UserCredentialService;
import com.neolith.focus.service.login.api.LoginService;

@Controller
@RequestMapping(UserPasswordResetController.REQUEST_MAPPING_URL)
public class UserPasswordResetController {

    public static final String REQUEST_MAPPING_URL = "/resetPassword";

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UserCredentialService userCredentialService;

    @RequestMapping(value = "/")
    public String index(String email, Model model) throws Exception {
        return "/send-reset-email";
    }

    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public String sendResetMail(String email, Model model, RedirectAttributes ra) throws Exception {

        LoginResult result = loginService.sendResetEmail(email);
        
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
			return "send-reset-email";
		} else {
			model.addAttribute("success",result.getMsgList());
			return "/login";
		}
    }

    @RequestMapping(value = "/add/userId={userId},token={token}", method = RequestMethod.GET)
    public String resetPassword(@PathVariable("token") String token,@PathVariable("userId") Integer userId, Model model,RedirectAttributes ra) throws Exception {

        LoginResult result = loginService.resetPassword(token, model);
        model.addAttribute("credentialDTO", userCredentialService.findByUserId(userId));
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
			return "/login";
		} else {
			model.addAttribute("success",result.getMsgList());
			return "/reset-password";
		}
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(@ModelAttribute("credentialDTO") @Valid UserCredentialDTO credentialDTO, Model model,RedirectAttributes ra) throws Exception {

        LoginResult result = loginService.updatePassword(credentialDTO);
        
		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
			return "reset-password";
		} else {
			model.addAttribute("success",result.getMsgList());
			return "/login";
		}

    }
    
     

}
