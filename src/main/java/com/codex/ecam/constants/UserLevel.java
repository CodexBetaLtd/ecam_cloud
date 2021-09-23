package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.util.AuthenticationUtil;

public enum UserLevel {

	ADMIN_LEVEL(0, "Cloud admin"),
	SYSTEM_LEVEL(1, "Business admin"),
	GENERAL_LEVEL(2, "Business user");

	private Integer id;
	private String name;

	private UserLevel(Integer id, String name){
		setId(id);
		setName(name);
	}

	public Integer getId() {
		return id;
	}

	private void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public static List<UserLevel> getUserTypeList(){
		final List<UserLevel> list = new ArrayList<UserLevel>();
		if(AuthenticationUtil.isAuthUserAdminLevel()){
			list.add(ADMIN_LEVEL);
		}
		list.add(SYSTEM_LEVEL);
		list.add(GENERAL_LEVEL);
		return list;
	}


}
