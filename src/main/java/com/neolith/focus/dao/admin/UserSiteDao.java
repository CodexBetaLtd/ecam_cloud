package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.UserSite;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserSiteDao  extends FocusDataTableRepository<UserSite, Integer> {

    String FIND_USER_SITE_ASSETS = "SELECT site.id FROM UserSite userSite JOIN userSite.site site";
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(FIND_USER_SITE_ASSETS)
    List<Integer> findUserSiteAssets();

    @Query("from UserSite where site.id = :siteId and user.id = :userId")
	UserSite findBySiteAndLoginUser(@Param("siteId") Integer siteId, @Param("userId") Integer userId);

    
}
