package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.User;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends FocusDataTableRepository<User, Integer> {

	User findById(Integer userId);

    @Query("from User where  emailAddress=:emailAddress")
    User findByEmail(@Param("emailAddress") String emailAddress);

	@Query("SELECT u.imagePath FROM User u WHERE u.id = :id")
	String getUserAvatarPath(@Param("id") Integer id);
}
