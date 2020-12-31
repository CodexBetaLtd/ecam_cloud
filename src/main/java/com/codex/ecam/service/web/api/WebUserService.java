package com.codex.ecam.service.web.api;

import org.springframework.http.ResponseEntity;

import com.codex.ecam.dto.web.WebUserDetailDto;

public interface WebUserService {

	ResponseEntity<WebUserDetailDto> login(String userName, String password) throws Exception;

	void addUser(WebUserDetailDto userDetail) throws Exception;

	boolean exists(WebUserDetailDto userDetail) throws Exception;

	WebUserDetailDto findByUserId(Integer id) throws Exception;

	void updateUser(Integer userId, WebUserDetailDto userDetail) throws Exception;

	boolean isUserAvailable(Integer id);

	ResponseEntity<Void> updateUserCredential(Integer userId, WebUserDetailDto userDetail);

	void sendMail(String email, String username, String password,String fullname) throws Exception;

}
