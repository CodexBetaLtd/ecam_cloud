package com.codex.ecam.service.login.api;

import org.springframework.ui.Model;

import com.codex.ecam.dto.admin.UserCredentialDTO;
import com.codex.ecam.result.LoginResult;

public interface LoginService {

	LoginResult sendResetEmail(String email);

	LoginResult resetPassword(String token, Model modal);

	LoginResult updatePassword(UserCredentialDTO credentialDTO);

	LoginResult requestPasswordReset(String userName);

}
