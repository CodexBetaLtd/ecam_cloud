package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.UserToken;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenDao extends FocusDataTableRepository<UserToken, Integer>{

	UserToken findById(Integer id);

    @Query("from UserToken where resetPasswordToken=:token")
    UserToken findByToken(@Param("token") String token);

}
