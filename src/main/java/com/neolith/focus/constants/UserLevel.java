package com.neolith.focus.constants;

import com.neolith.focus.util.AuthenticationUtil;

import java.util.ArrayList;
import java.util.List;

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
