package com.codex.ecam.service.userprofile.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.mappers.admin.UserMapper;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.result.userprofile.UserProfileResult;
import com.codex.ecam.service.userprofile.api.UserProfileService;
import com.codex.ecam.util.aws.AmazonS3ObjectUtil;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	Environment environment;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AmazonS3ObjectUtil amazonS3ObjectUtil;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserProfileResult update(UserDTO dto, MultipartFile file) {
		final UserProfileResult result = new UserProfileResult(null, dto);
		if (!file.isEmpty()) {
			dto.setImagePath(saveFile(file, dto.getId()));
		}
		try {
			final User domain = findEntityById(dto.getId());
			result.setDomainEntity(domain);
			if (dto.getChangePassword() == true) {
				changePassword(result);
			}
			saveOrUpdate(result);
			result.addToMessageList("Profile Updated Successfully.");
		} catch (final Exception e) {
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}

	private void changePassword(UserProfileResult result) {

		if (!passwordEncoder.matches(result.getDtoEntity().getCurrentPassword(),
				result.getDomainEntity().getUserCredential().getPassword())) {
			result.setResultStatusError();
			result.addToErrorList("Current password not match!");
		} else {
			result.getDomainEntity().getUserCredential().setPassword(passwordEncoder.encode(result.getDtoEntity().getNewPassword()));
		}
	}

	private void saveOrUpdate(UserProfileResult result) throws Exception {
		try {
			UserMapper.getInstance().dtoToBasicDomain(result.getDtoEntity(), result.getDomainEntity());
			userDao.save(result.getDomainEntity());
			result.updateDtoIdAndVersion();
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Account Already updated. Please Reload account.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
	}

	public String saveFile(MultipartFile file, Integer id) {
		try {
			return saveImageS3Bucket(id, file);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String saveImageS3Bucket(Integer id, MultipartFile image) throws IOException {

		// s3 key for storage
		//		final String key = amazonS3Util.getCommonUploadKey() + amazonS3Util.getAssetImageUploadKey() + dto.getId()
		//				+ File.separator + getFileName(image);
		final String key = environment.getProperty("upload.location.s3")
				+ environment.getProperty("upload.location.avatar.s3") + id + "/" + setFileName(image, id);

		amazonS3ObjectUtil.uploadS3Object(key, image);

		return key;
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

	@Override
	public User findEntityById(Integer id) {
		return userDao.findById(id);
	}
}
