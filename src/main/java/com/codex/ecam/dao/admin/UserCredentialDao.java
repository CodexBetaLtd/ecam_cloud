package com.codex.ecam.dao.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.UserCredential;

@Repository
public interface UserCredentialDao extends JpaRepository<UserCredential, Integer> {

    UserCredential findByUserName(String userName);

    @Query(value = "select  *  from tbl_user_credentials where tbl_user_credentials. user_id=:userId", nativeQuery = true)
    UserCredential findByUserId(@Param("userId") Integer userId);

    UserCredential findById(Integer id);

}
