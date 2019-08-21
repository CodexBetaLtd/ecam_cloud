package com.neolith.focus.service.userprofile.api;

import com.neolith.focus.dto.admin.UserDTO;
import com.neolith.focus.model.admin.User;
import com.neolith.focus.result.userprofile.UserProfileResult;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {

    UserProfileResult update(UserDTO userDTO, MultipartFile file); 

    User findEntityById(Integer id);
}
