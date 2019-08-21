package com.codex.ecam.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.codex.ecam.dao.admin.UserSiteDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.security.api.SecurityContextAccessor;
import com.codex.ecam.util.AuthenticationUtil;

@Component
public class SecurityContextAccessorImpl implements SecurityContextAccessor {

	@Autowired
	private AuthenticationTrustResolver authenticationTrustResolver;

	@Autowired
	private UserSiteDao userSiteDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}

	@Override
	public void setUserSite(Integer siteId) {
		AuthenticationUtil.setLoginUserSite(userSiteDao.findBySiteAndLoginUser(siteId, AuthenticationUtil.getAuthenticatedUser().getId()));
	}

	@Override
	public void setUserBusiness(Integer businessId) {
		AuthenticationUtil.setLoginUserBusiness(businessDao.findById(businessId));
	}

}
