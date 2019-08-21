package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.UserGroupPage;
import com.neolith.focus.model.admin.UserGroupPagePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserGroupPagePermissionDao extends JpaRepository<UserGroupPagePermission, Integer> {

	@Modifying
    @Transactional
    @Query("delete from UserGroupPagePermission where userGroupPage = :groupPage")
	void deleteByUserGroupPage(@Param("groupPage") UserGroupPage groupPage);
	
	@Query("from UserGroupPagePermission where userGroupPage.userGroup.id = :userGroupId")
	List<UserGroupPagePermission> findByUserGroupId(@Param("userGroupId")Integer userGroupId);
	
}
