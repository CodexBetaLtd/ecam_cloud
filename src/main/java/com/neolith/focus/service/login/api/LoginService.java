package com.neolith.focus.service.login.api;

import com.neolith.focus.dto.admin.UserCredentialDTO;
import com.neolith.focus.result.LoginResult;
import org.springframework.ui.Model;

public interface LoginService {

    LoginResult sendResetEmail(String email);

    LoginResult resetPassword(String token, Model modal);

    LoginResult updatePassword(UserCredentialDTO credentialDTO);

}
