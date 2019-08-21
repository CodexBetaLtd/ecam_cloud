package com.codex.ecam.service.admin.usersite;

import java.util.Arrays;

import com.codex.ecam.dto.admin.cmmssetting.UserSiteDTO;

/**
 * Created by neo89 on 12/21/16.
 */
public class UserSiteData {

	private static UserSiteData ourInstance = new UserSiteData();

	public static UserSiteData getInstance() {
		return ourInstance;
	}

	private UserSiteData() {

	}

	public UserSiteDTO getUserSiteDTO(){
		UserSiteDTO userSiteDTO = new UserSiteDTO();
		userSiteDTO.setSiteUserId(1);
		userSiteDTO.setSiteSiteId(1);
		userSiteDTO.setSiteAssignedUserGroups(Arrays.asList(1, 2));
		return userSiteDTO;
	}



}
