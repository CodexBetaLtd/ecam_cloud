package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.UserGroupMenuSubMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserGroupMenuSubMenuDao extends JpaRepository<UserGroupMenuSubMenu, Integer> {
	
	@Modifying
	@Query("delete from UserGroupMenuSubMenu ugmsm where ugmsm.userGroupMenu.id IN :ids")
	@Transactional
	void deleteByUserGroups(@Param("ids") List<Integer> ids);

	@Query("from UserGroupMenuSubMenu where userGroupMenu.userGroup.id = :userGroupId")
	List<UserGroupMenuSubMenu> findByUserGroupId(@Param("userGroupId")Integer userGroupId);

}
