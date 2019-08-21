package com.codex.ecam.service.userprofile.api;

import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.result.userprofile.UserProfileResult;

public interface UserProfileService {

    UserProfileResult update(UserDTO userDTO, MultipartFile file); 

    User findEntityById(Integer id);
}
