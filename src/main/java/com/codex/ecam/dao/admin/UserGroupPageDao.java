package com.codex.ecam.dao.admin;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.constants.Page;
import com.codex.ecam.model.admin.UserGroup;
import com.codex.ecam.model.admin.UserGroupPage;

@Repository
public interface UserGroupPageDao  extends JpaRepository<UserGroupPage, Integer> {

	UserGroupPage findByUserGroupAndPage(UserGroup domain, Page page);

	Set<UserGroupPage> findByUserGroup(UserGroup domain);

	@Query("from UserGroupPage where userGroup.id = :userGroupId and page = :page")
	UserGroupPage findByUserGroupIdAndPage(@Param("userGroupId") Integer userGroupId, @Param("page") Page page);

	@Query("from UserGroupPage where userGroup.id = :userGroupId")
	List<UserGroupPage> findByUserGroupId(@Param("userGroupId") Integer userGroupId);

}
