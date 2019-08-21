package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.UserGroupMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupMenuDao  extends JpaRepository<UserGroupMenu, Integer> {

	@Query("from UserGroupPage where userGroup.id = :userGroupId")
	List<UserGroupMenu> findByUserGroupId(@Param("userGroupId") Integer userGroupId);

}
