package com.codex.ecam.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.UserCredentialDao;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dto.admin.UserCredentialDTO;
import com.codex.ecam.mappers.admin.UserCredentialMapper;
import com.codex.ecam.model.admin.UserCredential;
import com.codex.ecam.service.admin.api.UserCredentialService;

@Service
public class UserCredentialServiceImpl implements UserCredentialService {

	@Autowired
	private UserCredentialDao userCredentialDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserCredentialDTO findByUserName(String username) {
		UserCredential userCredential = userCredentialDao.findByUserName(username);
		if (userCredential != null) {
			try {
				return UserCredentialMapper.getInstance().domainToDto(userCredential);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public UserCredentialDTO findByUserId(Integer useId) {
		UserCredential userCredential = userCredentialDao.findByUserId(useId);
		if (userCredential != null) {
			try {
				return UserCredentialMapper.getInstance().domainToDto(userCredential);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(UserCredentialDTO dto) throws Exception {
		UserCredential domain = userCredentialDao.findByUserId(dto.getUserId());
		saveOrUpdate(dto, domain);

	}

	private void saveOrUpdate(UserCredentialDTO dto, UserCredential domain) throws Exception {
		UserCredentialMapper.getInstance().dtoToDomain(dto, domain);
		domain.setPassword(passwordEncoder.encode(dto.getPassword()));
		setUser(dto, domain);
		userCredentialDao.save(domain);
	}

	private void setUser(UserCredentialDTO dto, UserCredential domain) {
		if ((dto.getUserId() != null) && (dto.getUserId() > 0)) {
			domain.setUser(userDao.findById(dto.getUserId()));
		}
	}

	@Override
	public Integer save(UserCredentialDTO dto) throws Exception {
		UserCredential domain = new UserCredential();
		saveOrUpdate(dto, domain);
		return domain.getId();
	}

	@Override
	public void delete(Integer id) {
		userCredentialDao.delete(id);
	}

}
