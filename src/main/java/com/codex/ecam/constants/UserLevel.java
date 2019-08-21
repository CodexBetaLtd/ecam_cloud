package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.util.AuthenticationUtil;

public enum UserLevel {
	
	ADMIN_LEVEL(0, "Admin Level"),
	SYSTEM_LEVEL(1, "System Level"),
	GENERAL_LEVEL(2, "General Level");
	
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
		List<UserLevel> list = new ArrayList<UserLevel>();
		if(AuthenticationUtil.isAuthUserAdminLevel()){
			list.add(ADMIN_LEVEL);
		}
		list.add(SYSTEM_LEVEL);
		list.add(GENERAL_LEVEL);
		return list;
 	}


}
