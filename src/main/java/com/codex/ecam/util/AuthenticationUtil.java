package com.codex.ecam.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.codex.ecam.constants.SubMenu;
import com.codex.ecam.constants.UserLevel;
import com.codex.ecam.constants.Widgets;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.mappers.asset.AssetMapper;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.admin.UserGroup;
import com.codex.ecam.model.admin.UserGroupMenu;
import com.codex.ecam.model.admin.UserGroupMenuSubMenu;
import com.codex.ecam.model.admin.UserGroupPage;
import com.codex.ecam.model.admin.UserGroupPagePermission;
import com.codex.ecam.model.admin.UserSite;
import com.codex.ecam.model.admin.UserSiteGroup;
import com.codex.ecam.model.app.AppMenu;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.biz.business.BusinessApp;
import com.codex.ecam.model.biz.business.BusinessWiget;
import com.codex.ecam.security.CurrentUser;

public class AuthenticationUtil {

	public final static String PAGE_PERMISSION_PREFIX = "PAGE_PERMISSION_";

	public final static String SUB_MENU_PREFIX = "SUB_MENU_";

	public final static String MENU_PREFIX = "MENU_";
	
	public static User TRIGGER_USER = null;

	public static CurrentUser getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if ((authentication == null) || !authentication.isAuthenticated()|| authentication.getPrincipal().equals("anonymousUser")) {
			return null;
		}

		return (CurrentUser) authentication.getPrincipal();
	}

	public static User getAuthenticatedUser() {
		return getCurrentUser().getUserObj();
	}

	public static UserSite getLoginSite() {
		return getCurrentUser().getSite();
	}

	public static Business getLoginUserBusiness() {
		return getCurrentUser().getBusiness();
	}

	public static void setLoginUserBusiness(Business business) {
		getCurrentUser().setBusiness(business);
		setBusinessUserAuthority(business);
	}

	private static void setBusinessUserAuthority(Business business) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("SYSTEM_LEVEL"));
		for ( BusinessApp bApp  :  business.getBusinessApps()) {
			for ( AppMenu  aMenu : bApp.getApp().getAppMenus() ) {
				authorities.add( new SimpleGrantedAuthority(MENU_PREFIX + aMenu.getMenu().getId()) );
				for (SubMenu subMenu : SubMenu.getSubMenuByMenu(aMenu.getMenu())) {
					authorities.add( new SimpleGrantedAuthority(SUB_MENU_PREFIX + subMenu.getId()) );
				}
			}			
			setBusinessuserWigetPermision(bApp,authorities);
		}
		replaceCurrentAuthorities(authorities);
	}

	public static String getUserName() {
		return getCurrentUser().getUserName();
	}

	public static void setLoginUserSite(UserSite site) {
		getCurrentUser().setSite(site);
		getCurrentUser().setBusiness(site.getSite().getBusiness());
		setGeneralUserAuthority(site.getUserSiteGroups());
	}

	private static void setGeneralUserAuthority(Set<UserSiteGroup> userSiteGroups) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("GENERAL_LEVEL"));
		for(UserSiteGroup siteGroup : userSiteGroups){
			setMenuPermission(siteGroup.getUserGroup(), authorities);
			setPagePermission(siteGroup.getUserGroup(), authorities);
		}
		replaceCurrentAuthorities(authorities);
	}


	private static void setBusinessuserWigetPermision(BusinessApp bApp,Set<GrantedAuthority> authorities){
		for(BusinessWiget businessWiget:bApp.getBusinessWigets()){
				authorities.add( new SimpleGrantedAuthority(Widgets.getWigetById(businessWiget.getAppWiget().getWidgets().getId()).toString()) );
		}
	}
	public static Boolean isAuthUserAdminLevel() {
		return getAuthenticatedUser().getUserLevel().equals(UserLevel.ADMIN_LEVEL);
	}

	public static Boolean isAuthUserSystemLevel() {
		return getAuthenticatedUser().getUserLevel().equals(UserLevel.SYSTEM_LEVEL);
	}

	public static Boolean isAuthUserGeneralLevel() {
		return getAuthenticatedUser().getUserLevel().equals(UserLevel.GENERAL_LEVEL);
	}

	public static List<AssetDTO> getUserSiteList() {
		Set<UserSite> userSiteList = getAuthenticatedUser().getUserSites();
		List<AssetDTO> siteList = new ArrayList<AssetDTO>(userSiteList.size());
		for(UserSite userSite : userSiteList){
			try {
				siteList.add(AssetMapper.getInstance().domainToDto(userSite.getSite()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return siteList;
	}

	private static void replaceCurrentAuthorities(Set<GrantedAuthority> authorities) {
		Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
		Authentication auth = new UsernamePasswordAuthenticationToken(getCurrentUser(), credentials, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private static void setPagePermission(UserGroup userGroup, Set<GrantedAuthority> authorities) {
		for (UserGroupPage groupPage : userGroup.getPageList()) {
			//			authorities.add( new SimpleGrantedAuthority(groupPage.getPage().toString()));
			for (UserGroupPagePermission pagePermission : groupPage.getPermissionList()) {
				authorities.add(new SimpleGrantedAuthority(PAGE_PERMISSION_PREFIX + pagePermission.getPagePermission().getId()));
			}
		}
	}

	private static void setMenuPermission(UserGroup userGroup, Set<GrantedAuthority> authorities) {
		for (UserGroupMenu groupMenu : userGroup.getMenuList()) {
			authorities.add( new SimpleGrantedAuthority(MENU_PREFIX + groupMenu.getMenu().getId()) );
			for (UserGroupMenuSubMenu subMenu : groupMenu.getSubMenuList()) {
				authorities.add( new SimpleGrantedAuthority(SUB_MENU_PREFIX + subMenu.getSubMenu().getId()) );
			}
		}
	}

	public static void setTriggerUser (User user){
		TRIGGER_USER = user;
	}


}
