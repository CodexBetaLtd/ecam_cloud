package com.neolith.focus.security.api;

public interface SecurityContextAccessor {

    boolean isCurrentAuthenticationAnonymous();

	void setUserSite(Integer siteId);

	void setUserBusiness(Integer businessId);

}
