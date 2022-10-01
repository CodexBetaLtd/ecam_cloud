package com.codex.ecam.security;

import org.springframework.security.core.GrantedAuthority;

import com.codex.ecam.dto.admin.UserCredentialDTO;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.admin.UserSite;
import com.codex.ecam.model.biz.business.Business;

import java.util.Collection;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    
    private static final long serialVersionUID = -2385326206773475679L;
	
	private Integer userId;
    private String userName;
    private User userObj;
    private UserSite site;
    private Business business;

    public CurrentUser(UserCredentialDTO user, Collection<GrantedAuthority> authorities, User obj) {
        super(user.getUserName(), user.getPassword(), authorities);
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.userObj = obj;
    }

	public Integer getUserId() {
		return userId;
	}

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	public User getUserObj() {
		return userObj;
	}

	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserSite getSite() {
		return site;
	}

	public void setSite(UserSite site) {
		this.site = site;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

}
