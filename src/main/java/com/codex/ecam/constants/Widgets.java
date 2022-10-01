package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum Widgets {
	
	ASSET_WIGET(0, "Asset Wiget"),
	MAINTAINACE_WIGET(1, "Maintainace Wiget"),
	INVENTORY_WIGET(2, "Inventory Wiget"),
	NOTIFICATION_WIGET(3, "Notification Wiget"),
	BIZ_WIGET(4, "Biz Wiget"),
	SETTINGS_WIGET(5, "Setting Wiget");
	
	private Integer id;
	private String name;
	
	private Widgets(Integer id, String name){
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
	
    public static List<Widgets> getWigetList(){
    	List<Widgets> list = new ArrayList<Widgets>();
    	list.add(ASSET_WIGET);
    	list.add(MAINTAINACE_WIGET);
    	list.add(INVENTORY_WIGET);
    	list.add(NOTIFICATION_WIGET);
    	list.add(BIZ_WIGET);
    	list.add(SETTINGS_WIGET);
    	return list;
    }
    
	public static Widgets getWigetById(Integer id) {
		for (Widgets widget : values()) {
			if (widget.getId().equals(id)) {
				return widget;
			}
		}
		return null;
	}


}
