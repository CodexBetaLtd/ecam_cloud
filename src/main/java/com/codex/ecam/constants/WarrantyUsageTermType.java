package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum WarrantyUsageTermType {
	
	DATE(0, "Date"),
	METER_READING(1, "Meter Reading"),
	PRODUCTION_TIME(1, "Production Time");
	
	private Integer id;
	private String name;
	
	private WarrantyUsageTermType(Integer id, String name){
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
	
	public static List<WarrantyUsageTermType> getWarrantyUsageTermTypes(){
		List<WarrantyUsageTermType> list = new ArrayList<WarrantyUsageTermType>();
		list.add(DATE);
		list.add(METER_READING);
		list.add(PRODUCTION_TIME);
		return list;
 	}

}
