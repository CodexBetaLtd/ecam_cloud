package com.neolith.focus.constants;

import java.util.ArrayList;
import java.util.List;

public enum WarrantyType {
	
	BASIC(0, "Basic"),
	EXTENDED(1, "Extended");
	
	private Integer id;
	private String name;
	
	private WarrantyType(Integer id, String name){
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
	
	public static List<WarrantyType> getWarrantyTypes(){
		List<WarrantyType> list = new ArrayList<WarrantyType>();
		list.add(BASIC);
		list.add(EXTENDED);
		return list;
 	}

}
