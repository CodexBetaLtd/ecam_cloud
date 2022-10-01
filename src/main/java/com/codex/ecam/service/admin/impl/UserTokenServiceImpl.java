package com.codex.ecam.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.admin.UserTokenDao;
import com.codex.ecam.dto.admin.UserTokenDTO;
import com.codex.ecam.dto.admin.usergroup.UserGroupDTO;
import com.codex.ecam.mappers.admin.UserTokenMapper;
import com.codex.ecam.model.admin.UserToken;
import com.codex.ecam.service.admin.api.UserTokenService;

@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
    private UserTokenDao userTokenDao;

    @Autowired
    private UserDao userDao;

    @Override
    public DataTablesOutput<UserGroupDTO> findAll(DataTablesInput input) throws Exception {
        return null;
    }

    @Override
    public UserTokenDTO findById(Integer id) throws Exception {
        UserToken domain = userTokenDao.findById(id);
        if (domain != null) {
            return UserTokenMapper.getInstance().domainToDto(domain);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        userTokenDao.delete(id);

    }

    @Override
    public Integer upate(UserTokenDTO userTokenDTO) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Integer save(UserTokenDTO dto) throws Exception {
        UserToken domain = new UserToken();
        UserTokenMapper.getInstance().dtoToDomain(dto, domain);
        setUser(dto, domain);
        userTokenDao.save(domain);

        return domain.getId();
    }

    @Override
    public UserTokenDTO findbytoken(String token) throws Exception {
        UserToken domain = userTokenDao.findByToken(token);
        if (domain != null) {
            return UserTokenMapper.getInstance().domainToDto(domain);
        }
        return null;
    }

    private void setUser(UserTokenDTO dto, UserToken domain) {
        if (dto.getUserId() != null && dto.getUserId() > 0) {
            domain.setUser(userDao.findOne(dto.getUserId()));
        }
    }

}
