package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum Page {

	USERS_VIEW(1, SubMenu.USERS, "Users View Page"),
	USERS_ADD(2, SubMenu.USERS, "User Add Page"),
	USER_GROUPS_VIEW(3, SubMenu.USER_GROUPS, "User Groups View Page"),
	USER_GROUPS_ADD(4, SubMenu.USER_GROUPS, "User Groups Add Page"),

	BUSINESS_ADD(5, SubMenu.BUSINESS, "Business Add Page"),
	BUSINESS_VIEW(6, SubMenu.BUSINESS, "Business View Page"),

	SUPPLIER_BUSINESS_ADD(7, SubMenu.SUPPLIER, "Supplier Business Add Page"),
	SUPPLIER_BUSINESS_VIEW(8, SubMenu.SUPPLIER, "Supplier Business View Page"),

	NOTIFICATION_ADD(9, SubMenu.NEW_MAIL, "Notification Add Page"),
	NOTIFICTION_INBOX_VIEW(10, SubMenu.INBOX, "Notification Inbox View Page"),
	NOTIFICTION_INBOX_ADD(11, SubMenu.INBOX, "Notification Inbox View Page"),
	NOTIFICTION_OUTBOX_VIEW(12, SubMenu.OUTBOX, "Notification Outbox View Page"),
	NOTIFICTION_OUTBOX_ADD(13, SubMenu.OUTBOX, "Notification Outbox View Page"),
	NOTIFICTION_TRASH_VIEW(14, SubMenu.THRASH, "Notification Trash View Page"),

	ASSET_CATEGORY_VIEW(15, SubMenu.CMMS_SETTINGS, "Asset Category View Page"),
	ASSET_CATEGORY_ADD(16, SubMenu.CMMS_SETTINGS, "Asset Category Add Page"),

	TAX_VIEW(55, SubMenu.CMMS_SETTINGS, "Tax Category View Page"),
	TAX_ADD(56, SubMenu.CMMS_SETTINGS, "Tax Category Add Page"),

	ASSET_VIEW(17, SubMenu.ALL_ASSETS, "Asset View Page"),
	ASSET_ADD(18, SubMenu.ALL_ASSETS, "Asset  Add Page"),

	BOM_GROUP_VIEW(19, SubMenu.BOM_GROUP, "BOM Group View Page"),
	BOM_GROUP_ADD(20, SubMenu.BOM_GROUP, "BOM Group Add Page"),

	PART_VIEW(21, SubMenu.PART, "Part View Page"),
	PART_ADD(22, SubMenu.PART, "Part Add Page"),

	PROJECT_VIEW(23, SubMenu.PROJECT, "Project View Page"),
	PROJECT_ADD(24, SubMenu.PROJECT, "Project Add Page"),

	SCHEDULED_MAINTENANCE_ADD(25, SubMenu.SCHEDULED_MAINTENANCE, "Scheduled Maintenance Add Page"),
	SCHEDULED_MAINTENANCE_VIEW(26, SubMenu.SCHEDULED_MAINTENANCE, "Scheduled Maintenance View Page"),

	TASK_ADD(27, SubMenu.TASK_GROUP, "Task Add Page"),
	TASK_VIEW(28, SubMenu.TASK_GROUP, "Task View Page"),

	WORK_ORDER_ADD(29, SubMenu.WORK_ORDER, "Work Order Add Page"),
	WORK_ORDER_VIEW(30, SubMenu.WORK_ORDER, "Work Order View Page"),

	PURCHASE_ORDER_ADD(31, SubMenu.PURCHASE_ORDER, "Purchase Order Add Page"),
	PURCHASE_ORDER_VIEW(32, SubMenu.PURCHASE_ORDER, "Purchase Order View Page"),

	RECEIPT_ORDER_ADD(33, SubMenu.RECEIPT_ORDER, "Receipt Order Add Page"),
	RECEIPT_ORDER_VIEW(34, SubMenu.RECEIPT_ORDER, "Receipt Order View Page"),

	RFQ_ADD(35, SubMenu.RFQ, "RFQ Add Page"),
	RFQ_VIEW(36, SubMenu.RFQ, "RFQ View Page"),
	RFQStatus(37, SubMenu.RFQ, "RFQ Add Page"),

	USER_PROFILE_ADD(38, SubMenu.USER_PROFILE, "User Profile Add Page"),
	USER_PROFILE_VIEW(39, SubMenu.USER_PROFILE, "User Profile View Page"),

	APP_ADD(40, SubMenu.APP, "App Add Page"),
	APP_VIEW(41, SubMenu.APP, "App View Page"),

	AOD_ADD(42, SubMenu.AOD, "AOD Add Page"),
	AOD_VIEW(43, SubMenu.AOD, "AOD View Page"),
	AOD_RETURN_ADD(44, SubMenu.AOD_RETURN, "AOD Return Add Page"),
	AOD_RETURN_VIEW(45, SubMenu.AOD_RETURN, "AOD Return View Page"),


	STOCK_ADD(46, SubMenu.STOCK, "Stock Add Page"),
	STOCK_VIEW(47, SubMenu.STOCK, "Stock View Page"),
	STOCK_ADJUSTMENT_ADD(48, SubMenu.STOCK_ADJUSTMENT, "Stock Add Page"),
	STOCK_ADJUSTMENT_VIEW(49, SubMenu.STOCK_ADJUSTMENT, "Stock View Page"),

	WEARHOUSE_ADD(50,SubMenu.WEARHOUSE, "Warehouse Add Page"),
	WEARHOUSE_VIEW(51, SubMenu.WEARHOUSE,"Warehouse View Page"),

	DASHBOARD_VIEW(52, SubMenu.WIDGET,"Dashboard View"),

	MRN_ADD(53,SubMenu.MRN, "MRN Add Page"),
	MRN_VIEW(54, SubMenu.MRN,"MRN View Page");

	/**************************
	 * LAST ID = 56
	 ************************/
	private Integer id;
	private String name;
	private SubMenu subMenu;

	private Page(Integer id, SubMenu subMenu, String name) {
		setId(id);
		setName(name);
		setSubMenu(subMenu);
	}

	public static List<Page> getPageList(){
		final List<Page> list = new ArrayList<Page>();

		list.add(USERS_VIEW);
		list.add(USERS_ADD);
		list.add(USER_GROUPS_VIEW);
		list.add(USER_GROUPS_ADD);
		list.add(BUSINESS_ADD);
		list.add(BUSINESS_VIEW);
		list.add(SUPPLIER_BUSINESS_ADD);
		list.add(SUPPLIER_BUSINESS_VIEW);
		list.add(NOTIFICATION_ADD);
		list.add(NOTIFICTION_INBOX_VIEW);
		list.add(NOTIFICTION_INBOX_ADD);
		list.add(NOTIFICTION_OUTBOX_VIEW);
		list.add(NOTIFICTION_OUTBOX_ADD);
		list.add(NOTIFICTION_TRASH_VIEW);
		list.add(ASSET_CATEGORY_VIEW);
		list.add(ASSET_CATEGORY_ADD);
		list.add(TAX_VIEW);
		list.add(TAX_ADD);
		//		list.add(ASSET_VIEW);
		//		list.add(ASSET_ADD);
		list.add(BOM_GROUP_VIEW);
		list.add(BOM_GROUP_ADD);
		list.add(PART_VIEW);
		list.add(PART_ADD);
		list.add(PROJECT_VIEW);
		list.add(PROJECT_ADD);
		list.add(SCHEDULED_MAINTENANCE_VIEW);
		list.add(SCHEDULED_MAINTENANCE_ADD);
		list.add(TASK_ADD);
		list.add(TASK_VIEW);
		list.add(WORK_ORDER_ADD);
		list.add(WORK_ORDER_VIEW);
		//		list.add(PURCHASE_ORDER_ADD);
		//		list.add(PURCHASE_ORDER_VIEW);
		//		list.add(RECEIPT_ORDER_ADD);
		//		list.add(RECEIPT_ORDER_VIEW);
		//		list.add(RFQ_ADD);
		//		list.add(RFQ_VIEW);
		//		list.add(USER_PROFILE_ADD);
		//		list.add(USER_PROFILE_VIEW);
		list.add(APP_ADD);
		list.add(APP_VIEW);
		list.add(DASHBOARD_VIEW);
		//		list.add(AOD_ADD);
		//		list.add(AOD_VIEW);
		//		list.add(AOD_RETURN_ADD);
		//		list.add(AOD_RETURN_VIEW);
		//		list.add(STOCK_ADD);
		//		list.add(STOCK_VIEW);
		//		list.add(STOCK_ADJUSTMENT_ADD);
		//		list.add(STOCK_ADJUSTMENT_VIEW);
		//		list.add(ASSET_FACILITIES_VIEW);
		//		list.add(ASSET_FACILITIES_ADD);
		//		list.add(ASSET_EQUIPMENTS_VIEW);
		//		list.add(ASSET_EQUIPMENTS_ADD);

		return list;
	}

	public static Page getPageById(Integer pageId){
		final Page[] arry = Page.values();
		for (final Page element : arry) {
			if (element.getId().equals(pageId)) {
				return element;
			}
		}

		return null;
	}

	public static List<Page> findPageBySubMenu(SubMenu subMenu){
		final List<Page> list = new ArrayList<Page>();

		for (final Page element : Page.values()) {
			if (element.getSubMenu().equals(subMenu)) {
				list.add(element);
			}
		}

		return list;
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

	public SubMenu getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(SubMenu subMenu) {
		this.subMenu = subMenu;
	}

}
