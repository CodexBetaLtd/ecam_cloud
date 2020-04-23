package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum SubMenu {

	USERS(0, Menu.SETTINGS, "Users"),
	USER_GROUPS(1, Menu.SETTINGS, "User Groups"),
	BUSINESS(2, Menu.SETTINGS, "Business"),
	CMMS_SETTINGS(3, Menu.SETTINGS, "CMMS Settings"),

	APP(4, Menu.APP, "Apps"),
	MY_APP(5, Menu.APP, "My Apps"),

	PART(6, Menu.BIZ, "Parts"),
	SUPPLIER(7, Menu.BIZ, "Supplier Business"),
	CUSTOMER(8, Menu.BIZ, "Customer"),

	ALL_ASSETS(9, Menu.ASSETS, "All Assets"),
	FACILITIES(10, Menu.ASSETS, "Facilities"),
	EQUIPMENTS(11, Menu.ASSETS, "Equipments"),
	TOOLS(12, Menu.ASSETS, "Tools"),

	//	QUOTATION(13, Menu.SALE, "Quotation"),

	NEW_MAIL(14, Menu.NOTIFICATIONS, "New Mail"),
	INBOX(15, Menu.NOTIFICATIONS, "Inbox"),
	OUTBOX(16, Menu.NOTIFICATIONS, "OutBox"),
	THRASH(17, Menu.NOTIFICATIONS, "Thrash"),

	WORK_ORDER(18, Menu.MAINTENANCE, "Work Order"),
	PROJECT(19, Menu.MAINTENANCE, "Project"),
	SCHEDULED_MAINTENANCE(20, Menu.MAINTENANCE, "Scheduled Maintenance"),
	BOM_GROUP(21, Menu.MAINTENANCE, "BOM Group"),
	TASK_GROUP(22, Menu.MAINTENANCE, "TASK Group"),

	RFQ(23, Menu.INVENTORY, "RFQ"),
	PURCHASE_ORDER(24, Menu.INVENTORY, "Purchase Order"),
	RECEIPT_ORDER(25, Menu.INVENTORY, "Receipt Order"),
	AOD(26, Menu.INVENTORY, "AOD"),
	AOD_RETURN(27, Menu.INVENTORY, "AOD Return"),
	STOCK(28, Menu.INVENTORY, "Stock"),
	STOCK_ADJUSTMENT(29, Menu.INVENTORY, "Stock Adjustment"),

	USER_PROFILE(30, Menu.SETTINGS, "User Profile"),
	
	WEARHOUSE(31, Menu.BIZ, "Warehouse"),
	
	WIDGET(32, Menu.DASHBOARD, "Dashboard"),
	
	MRN(33, Menu.INVENTORY, "MRN"),
	
	GRN_REPORT(34, Menu.REPORT, "GRN Report"),
	AOD_REPORT(35, Menu.REPORT, "AOD Report"),
	AOD_RETURN_REPORT(36, Menu.REPORT, "AOD Return Report"),
	STOCK_AGE_REPORT(37, Menu.REPORT, "Stock Age Report"),
	PART_REPORT(38, Menu.REPORT, "Expense Report");


	private Integer id;
	private String name;
	private Menu menu;

	private SubMenu(Integer id, Menu menu ,String name){
		setId(id);
		setName(name);
		setMenu(menu);
	}



	public static List<SubMenu> getSubMenuByMenu(Menu menu) {
		List<SubMenu> list = new ArrayList<SubMenu>();

		SubMenu[] arry = SubMenu.values();
		for (SubMenu element : arry) {
			if (element.getMenu().equals(menu)) {
				list.add(element);
			}
		}

		return list;
	}
	
	
	public static SubMenu getSubMenuById(Integer id) {
		for (SubMenu subMenu : values()) {
			if (subMenu.getId().equals(id)) {
				return subMenu;
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

	public Menu getMenu() {
		return menu;
	}

	private void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

}
