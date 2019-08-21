package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.UserSiteGroup;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserSiteGroupDao extends FocusDataTableRepository<UserSiteGroup, Integer>{

	String SQL_NATIVE_DELETE_USER_SITE_GROUP_LIST = "DELETE tbl_user_site_group tbl_usg where tbl_usg.id in (:idList)";
    String FIND_USER_GROUPS_ID = "SELECT userGroup.id FROM UserSiteGroup userSiteGroup JOIN userSiteGroup.userGroup userGroup JOIN userSiteGroup.userSite userSite where userSite.id=:userSiteId";

	@Query(value=SQL_NATIVE_DELETE_USER_SITE_GROUP_LIST,nativeQuery=true)
	void deleteUserSiteGroupList(@Param("idList") List<Integer> idList);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(FIND_USER_GROUPS_ID)
	List<Integer> findUserSiteGroupIds(@Param("userSiteId") Integer userSiteId);

}
