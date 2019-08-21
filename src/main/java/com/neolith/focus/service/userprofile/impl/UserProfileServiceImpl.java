package com.neolith.focus.service.userprofile.impl;

import com.neolith.focus.dao.admin.UserDao;
import com.neolith.focus.dto.admin.UserDTO;
import com.neolith.focus.mappers.admin.UserMapper;
import com.neolith.focus.model.admin.User;
import com.neolith.focus.result.userprofile.UserProfileResult;
import com.neolith.focus.service.userprofile.api.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    Environment environment;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao; 

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserProfileResult update(UserDTO dto, MultipartFile file) {
        UserProfileResult result = new UserProfileResult(null, dto);
        if (!file.isEmpty()) {
            dto.setImagePath(saveFile(file, dto.getId()));
        }
        try {
            User domain = findEntityById(dto.getId());
            result.setDomainEntity(domain);
            if (dto.getChangePassword() == true) {
                changePassword(result);
            }
            saveOrUpdate(result);
            result.addToMessageList("Profile Updated Successfully.");
        } catch (Exception e) {
            result.setResultStatusError();
            result.addToErrorList(e.getMessage());
        }
        return result;
    }

    private void changePassword(UserProfileResult result) {

        if (!passwordEncoder.matches(result.getDtoEntity().getCurrentPassword(), result.getDomainEntity().getUserCredential().getPassword())) {
            result.setResultStatusError();
            result.addToErrorList("Current password not match!");
        } else {
            setPassword(result.getDtoEntity(), result.getDomainEntity());
        }
    }

    private void setPassword(UserDTO dto, User domain) {
        if (dto.getId() != null && dto.getId() > 0) {
            domain.getUserCredential().setPassword(passwordEncoder.encode(dto.getNewPassword()));
        }
    }

    private void saveOrUpdate(UserProfileResult result) throws Exception {
        try {
            UserMapper.getInstance().dtoToBasicDomain(result.getDtoEntity(), result.getDomainEntity());
            userDao.save(result.getDomainEntity());
            result.updateDtoIdAndVersion();
        } catch (ObjectOptimisticLockingFailureException e) {
            result.setResultStatusError();
            result.addToErrorList("Account Already updated. Please Reload account.");
        } catch (Exception ex) {
            result.setResultStatusError();
            result.addToErrorList(ex.getMessage());
        }
    }

    public String saveFile(MultipartFile file, Integer id) {
        try {
            return saveToDisk(file, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String setFileName(MultipartFile file, Integer id) {
        String fileName = file.getName();
        if (file.getContentType().equals("image/png")) {
            fileName = id + "." + "png";
        } else {
            fileName = id + "." + "jpeg";
        }
        return fileName;
    }

    private String saveToDisk(MultipartFile file, Integer id) throws Exception {
        String uploadLocation = environment.getProperty("upload.location");
        String uploadFolder = environment.getProperty("upload.folder");
        String pathName = "/" + id + "/";
        String fileName = setFileName(file, id);

        File dir = new File(uploadLocation + uploadFolder + File.separator + pathName);
        if (!dir.exists())
            dir.mkdirs();

        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

        byte[] bytes = file.getBytes();
        stream.write(bytes);
        stream.close();

        return uploadFolder + pathName + fileName;
    }

    @Override
    public User findEntityById(Integer id) {
        return userDao.findById(id);
    }
}
