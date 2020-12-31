package com.codex.ecam.dao.admin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.UserToken;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface UserTokenDao extends FocusDataTableRepository<UserToken, Integer>{

	UserToken findById(Integer id);

    @Query("from UserToken where resetPasswordToken=:token")
    UserToken findByToken(@Param("token") String token);

}
