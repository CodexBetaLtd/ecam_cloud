package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum Menu {

	DASHBOARD(0, "Dashboard"),
	SETTINGS(1, "Settings"),
	ASSETS(2, "Assets"),
	MAINTENANCE(3, "Maintenance"),
	INVENTORY(4, "Inventory"),
	SALE(5, "Sale"),
	BIZ(6, "Biz"),
	APP(7, "App"),
	NOTIFICATIONS(8, "Notifications"),
	REPORT(9,"Report");

	private Integer id;
	private String name;

	private Menu(Integer id, String name){
		setId(id);
		setName(name);
	}

	public static List<Menu> getMenus() {
		List<Menu> list = new ArrayList<Menu>();
		list.add(Menu.DASHBOARD);
		list.add(Menu.SETTINGS);
		list.add(Menu.ASSETS);
		list.add(Menu.NOTIFICATIONS);
		list.add(Menu.MAINTENANCE);
		list.add(Menu.SALE);
		list.add(Menu.BIZ);
		list.add(Menu.INVENTORY);
		list.add(Menu.APP);
		return list;
	}

	public static Menu getMenuById(Integer id) {
		for (Menu menu : values()) {
			if (menu.getId().equals(id)) {
				return menu;
			}
		}
		return null;
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

}
