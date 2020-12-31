package com.codex.ecam.dao.admin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.User;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface UserDao extends FocusDataTableRepository<User, Integer> {

	User findById(Integer userId);

    @Query("from User where  emailAddress=:emailAddress")
    User findByEmail(@Param("emailAddress") String emailAddress);

	@Query("SELECT u.imagePath FROM User u WHERE u.id = :id")
	String getUserAvatarPath(@Param("id") Integer id);
}
