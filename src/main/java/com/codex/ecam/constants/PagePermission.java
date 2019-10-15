package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum PagePermission {

	HAS_SAVE_BUTTON_USER_GROUP_ADD(1, Page.USER_GROUPS_ADD, "Has 'Save' Button On The 'User Group Add' Page"),
	HAS_NEW_BUTTON_USER_GROUP_ADD(2, Page.USER_GROUPS_ADD, "Has 'New' Button On The 'User Group Add' Page"),
	HAS_DELETE_BUTTON_USER_GROUP_ADD(3, Page.USER_GROUPS_ADD, "Has 'Delete' Button On The 'User Group Add' Page"),
	HAS_CANCEL_BUTTON_USER_GROUP_ADD(4, Page.USER_GROUPS_ADD, "Has 'Cancel' Button On The 'User Group Add' Page"),

	HAS_SAVE_BUTTON_USER_GROUP_VIEW(5, Page.USER_GROUPS_VIEW, "Has 'Save' Button On The 'User Group View' Page"),
	HAS_NEW_BUTTON_USER_GROUP_VIEW(6, Page.USER_GROUPS_VIEW, "Has 'New' Button On The 'User Group View' Page"),
	HAS_DELETE_BUTTON_USER_GROUP_VIEW(7, Page.USER_GROUPS_VIEW, "Has 'Delete' Button On The 'User Group View' Page"),
	HAS_CANCEL_BUTTON_USER_GROUP_VIEW(8, Page.USER_GROUPS_VIEW, "Has 'Cancel' Button On The 'User Group View' Page"),
	HAS_EDIT_FUNCTION_USER_GROUP_VIEW(9, Page.USER_GROUPS_VIEW, "Has 'Edit' Button On The 'User Group View' Page"),

	HAS_SAVE_BUTTON_BUSINESS_ADD(10, Page.BUSINESS_ADD, "Has 'Save' Button On The 'Business Add' Page"),
	HAS_NEW_BUTTON_BUSINESS_ADD(11, Page.BUSINESS_ADD, "Has 'New' Button On The 'Business Add' Page"),
	HAS_DELETE_BUTTON_BUSINESS_ADD(12, Page.BUSINESS_ADD, "Has 'Delete' Button On The 'Business Add' Page"),
	HAS_CANCEL_BUTTON_BUSINESS_ADD(13, Page.BUSINESS_ADD, "Has 'Cancel' Button On The 'Business Add' Page"),

	HAS_SAVE_BUTTON_BUSINESS_VIEW(14, Page.BUSINESS_VIEW, "Has 'Save' Button On The 'Business View' Page"),
	HAS_NEW_BUTTON_BUSINESS_VIEW(15, Page.BUSINESS_VIEW, "Has 'New' Button On The 'Business View' Page"),
	HAS_DELETE_BUTTON_BUSINESS_VIEW(16, Page.BUSINESS_VIEW, "Has 'Delete' Button On The 'Business View' Page"),
	HAS_CANCEL_BUTTON_BUSINESS_VIEW(17, Page.BUSINESS_VIEW, "Has 'Cancel' Button On The 'Business View' Page"),
	HAS_EDIT_FUNCTION_BUSINESS_VIEW(18, Page.BUSINESS_VIEW, "Has 'Edit' Button On The 'Business View' Page"),

	HAS_SAVE_BUTTON_SUPPLIER_BUSINESS_ADD(19, Page.SUPPLIER_BUSINESS_ADD, "Has 'Save' Button On The 'Supplier Business Add' Page"),
	HAS_NEW_BUTTON_SUPPLIER_BUSINESS_ADD(20, Page.SUPPLIER_BUSINESS_ADD, "Has 'New' Button On The 'Supplier Business Add' Page"),
	HAS_DELETE_BUTTON_SUPPLIER_BUSINESS_ADD(21, Page.SUPPLIER_BUSINESS_ADD, "Has 'Delete' Button On The 'Supplier Business Add' Page"),
	HAS_CANCEL_BUTTON_SUPPLIER_BUSINESS_ADD(22, Page.SUPPLIER_BUSINESS_ADD, "Has 'Cancel' Button On The 'Supplier Business Add' Page"),

	HAS_SAVE_BUTTON_SUPPLIER_BUSINESS_VIEW(23, Page.SUPPLIER_BUSINESS_VIEW, "Has 'Save' Button On The 'Supplier Business View' Page"),
	HAS_NEW_BUTTON_SUPPLIER_BUSINESS_VIEW(24, Page.SUPPLIER_BUSINESS_VIEW, "Has 'New' Button On The 'Supplier Business View' Page"),
	HAS_DELETE_BUTTON_SUPPLIER_BUSINESS_VIEW(25, Page.SUPPLIER_BUSINESS_VIEW, "Has 'Delete' Button On The 'Supplier Business View' Page"),
	HAS_CANCEL_BUTTON_SUPPLIER_BUSINESS_VIEW(26, Page.SUPPLIER_BUSINESS_VIEW, "Has 'Cancel' Button On The 'SupplierBusiness View' Page"),
	HAS_EDIT_FUNCTION_SUPPLIER_BUSINESS_VIEW(27, Page.SUPPLIER_BUSINESS_VIEW, "Has 'Edit' Button On The 'Supplier Business View' Page"),

	HAS_SAVE_BUTTON_USERS_ADD(28, Page.USERS_ADD, "Has 'Save' Button On The 'User Add' Page"),
	HAS_NEW_BUTTON_USERS_ADD(29, Page.USERS_ADD, "Has 'New' Button On The 'User Add' Page"),
	HAS_DELETE_BUTTON_USERS_ADD(30, Page.USERS_ADD, "Has 'Delete' Button On The 'User Add' Page"),
	HAS_CANCEL_BUTTON_USERS_ADD(31, Page.USERS_ADD, "Has 'Cancel' Button On The 'User Add' Page"),

	HAS_SAVE_BUTTON_USERS_VIEW(32, Page.USERS_VIEW, "Has 'Save' Button On The 'User View' Page"),
	HAS_NEW_BUTTON_USERS_VIEW(33, Page.USERS_VIEW, "Has 'New' Button On The 'User View' Page"),
	HAS_DELETE_BUTTON_USERS_VIEW(34, Page.USERS_VIEW, "Has 'Delete' Button On The 'User View' Page"),
	HAS_CANCEL_BUTTON_USERS_VIEW(35, Page.USERS_VIEW, "Has 'Cancel' Button On The 'User View' Page"),
	HAS_EDIT_FUNCTION_USERS_VIEW(36, Page.USERS_VIEW, "Has 'Edit' Button On The 'User View' Page"),

	HAS_SAVE_BUTTON_NOTIFICTION_ADD(37, Page.NOTIFICATION_ADD, "Has 'Save' Button On The 'Notification Add ' Page"),
	HAS_NEW_BUTTON_NOTIFICTION_ADD(38, Page.NOTIFICATION_ADD, "Has 'New' Button On The 'Notification Add ' Page"),
	HAS_DELETE_BUTTON_NOTIFICTION_ADD(39, Page.NOTIFICATION_ADD, "Has 'Delete' Button On The 'Notification Add ' Page"),
	HAS_CANCEL_BUTTON_NOTIFICTION_ADD(40, Page.NOTIFICATION_ADD, "Has 'Cancel' Button On The 'Notification Add ' Page"),

	HAS_SAVE_BUTTON_NOTIFICTION_INBOX_VIEW(41, Page.NOTIFICTION_INBOX_VIEW, "Has 'Save' Button On The 'Notification Inbox View' Page"),
	HAS_NEW_BUTTON_NOTIFICTION_INBOX_VIEW(42, Page.NOTIFICTION_INBOX_VIEW, "Has 'New' Button On The 'Notification Inbox View' Page"),
	HAS_DELETE_BUTTON_NOTIFICTION_INBOX_VIEW(43, Page.NOTIFICTION_INBOX_VIEW, "Has 'Delete' Button On The 'Notification Inbox View' Page"),
	HAS_CANCEL_BUTTON_NOTIFICTION_INBOX_VIEW(44, Page.NOTIFICTION_INBOX_VIEW, "Has 'Cancel' Button On The 'Notification Inbox View' Page"),


	HAS_SAVE_BUTTON_NOTIFICTION_OUTBOX_VIEW(45, Page.NOTIFICTION_OUTBOX_VIEW, "Has 'Save' Button On The 'Notification Outbox View' Page"),
	HAS_NEW_BUTTON_NOTIFICTION_OUTBOX_VIEW(46, Page.NOTIFICTION_OUTBOX_VIEW, "Has 'New' Button On The 'Notification Outbox View' Page"),
	HAS_DELETE_BUTTON_NOTIFICTION_OUTBOX_VIEW(47, Page.NOTIFICTION_OUTBOX_VIEW, "Has 'Delete' Button On The 'Notification Outbox View' Page"),
	HAS_CANCEL_BUTTON_NOTIFICTION_OUTBOX_VIEW(48, Page.NOTIFICTION_OUTBOX_VIEW, "Has 'Cancel' Button On The 'Notification Outbox View' Page"),

	HAS_SAVE_BUTTON_NOTIFICTION_TRASH_VIEW(49, Page.NOTIFICTION_TRASH_VIEW, "Has 'Save' Button On The 'Notification Trash View' Page"),
	HAS_NEW_BUTTON_NOTIFICTION_TRASH_VIEW(50, Page.NOTIFICTION_TRASH_VIEW, "Has 'New' Button On The 'Notification Trash View' Page"),
	HAS_DELETE_BUTTON_NOTIFICTION_TRASH_VIEW(51, Page.NOTIFICTION_TRASH_VIEW, "Has 'Delete' Button On The 'Notification Trash View' Page"),
	HAS_CANCEL_BUTTON_NOTIFICTION_TRASH_VIEW(52, Page.NOTIFICTION_TRASH_VIEW, "Has 'Cancel' Button On The 'Notification Trash View' Page"),

	HAS_SAVE_BUTTON_ASSET_CATEGORY_ADD(53, Page.ASSET_CATEGORY_ADD, "Has 'Save' Button On The 'Asset Category Add ' Page"),
	HAS_NEW_BUTTON_ASSET_CATEGORY_ADD(54, Page.ASSET_CATEGORY_ADD, "Has 'New' Button On The 'Asset Category Add' Page"),
	HAS_DELETE_BUTTON_ASSET_CATEGORY_ADD(55, Page.ASSET_CATEGORY_ADD, "Has 'Delete' Button On The 'Asset Category Add ' Page"),
	HAS_CANCEL_BUTTON_ASSET_CATEGORY_ADD(56, Page.ASSET_CATEGORY_ADD, "Has 'Cancel' Button On The 'Asset Category Add ' Page"),

	HAS_SAVE_BUTTON_ASSET_CATEGORY_VIEW(57, Page.ASSET_CATEGORY_VIEW, "Has 'Save' Button On The 'Asset Category  View' Page"),
	HAS_NEW_BUTTON_ASSET_CATEGORY_VIEW(58, Page.ASSET_CATEGORY_VIEW, "Has 'New' Button On The 'Asset Category  View' Page"),
	HAS_DELETE_BUTTON_ASSET_CATEGORY_VIEW(59, Page.ASSET_CATEGORY_VIEW, "Has 'Delete' Button On The 'Asset Category  View' Page"),
	HAS_CANCEL_BUTTON_ASSET_CATEGORY_VIEW(60, Page.ASSET_CATEGORY_VIEW, "Has 'Cancel' Button On The 'Asset Category  View' Page"),
	HAS_EDIT_BUTTON_ASSET_CATEGORY_VIEW(61, Page.ASSET_CATEGORY_VIEW, "Has 'Edit' Button On The 'Asset Category  View' Page"),

	HAS_SAVE_BUTTON_ASSET_ADD(62, Page.ASSET_ADD, "Has 'Save' Button On The 'Asset  Add' Page"),
	HAS_NEW_BUTTON_FACILITY_ADD(63, Page.ASSET_ADD, "Has 'New' Button On The 'Facility  Add' Page"),
	HAS_NEW_BUTTON_MACHINE_ADD(64, Page.ASSET_ADD, "Has 'New' Button On The 'Machine  Add' Page"),
	HAS_NEW_BUTTON_TOOL_ADD(65, Page.ASSET_ADD, "Has 'New' Button On The 'Tool  Add' Page"),
	HAS_DELETE_BUTTON_ASSET_ADD(66, Page.ASSET_ADD, "Has 'Delete' Button On The 'Asset  Add' Page"),
	HAS_CANCEL_BUTTON_ASSET_ADD(67, Page.ASSET_ADD, "Has 'Cancel' Button On The 'Asset  Add' Page"),

	HAS_SAVE_BUTTON_ASSET_VIEW(68, Page.ASSET_VIEW, "Has 'Save' Button On The 'Asset View' Page"),
	HAS_NEW_BUTTON_FACILITY_VIEW(69, Page.ASSET_VIEW, "Has 'New' Button On The 'Facility View' Page"),
	HAS_NEW_BUTTON_MACHINE_VIEW(70, Page.ASSET_VIEW, "Has 'New' Button On The 'Machine  View' Page"),
	HAS_NEW_BUTTON_TOOL_VIEW(71, Page.ASSET_VIEW, "Has 'New' Button On The 'Tool View' Page"),
	HAS_DELETE_BUTTON_ASSET_VIEW(72, Page.ASSET_VIEW, "Has 'Delete' Button On The 'Asset View' Page"),
	HAS_CANCEL_BUTTON_ASSET_VIEW(73, Page.ASSET_VIEW, "Has 'Cancel' Button On The 'Asset View' Page"),
	HAS_EDIT_BUTTON_ASSET_VIEW_VIEW(74, Page.ASSET_VIEW, "Has 'Edit' Button On The 'Asset View' Page"),


	HAS_SAVE_BUTTON_BOM_GROUP_ADD(75, Page.BOM_GROUP_ADD, "Has 'Save' Button On The 'BOM Add' Page"),
	HAS_NEW_BUTTON_BOM_GROUP_ADD(76, Page.BOM_GROUP_ADD, "Has 'New' Button On The 'BOM Add' Page"),
	HAS_DELETE_BUTTON_BOM_GROUP_ADD(77, Page.BOM_GROUP_ADD, "Has 'Delete' Button On The 'BOM Add' Page"),
	HAS_CANCEL_BUTTON_BOM_GROUP_ADD(78, Page.BOM_GROUP_ADD, "Has 'Cancel' Button On The 'BOM Add' Page"),

	HAS_SAVE_BUTTON_BOM_GROUP_VIEW(79, Page.BOM_GROUP_VIEW, "Has 'Save' Button On The 'BOM View' Page"),
	HAS_NEW_BUTTON_BOM_GROUP_VIEW(80, Page.BOM_GROUP_VIEW, "Has 'New' Button On The 'BOM View' Page"),
	HAS_DELETE_BUTTON_BOM_GROUP_VIEW(81, Page.BOM_GROUP_VIEW, "Has 'Delete' Button On The 'BOM View' Page"),
	HAS_CANCEL_BUTTON_BOM_GROUP_VIEW(82, Page.BOM_GROUP_VIEW, "Has 'Cancel' Button On The 'BOM View' Page"),
	HAS_EDIT_BUTTON_BOM_GROUP_VIEW(83, Page.BOM_GROUP_VIEW, "Has 'Edit' Button On The 'BOM View' Page"),

	HAS_SAVE_BUTTON_PART_ADD(84, Page.PART_ADD, "Has 'Save' Button On The 'Part Add' Page"),
	HAS_NEW_BUTTON_PART_ADD(85, Page.PART_ADD, "Has 'New' Button On The 'Part Add' Page"),
	HAS_DELETE_BUTTON_PART_ADD(86, Page.PART_ADD, "Has 'Delete' Button On The 'Part Add' Page"),
	HAS_CANCEL_BUTTON_PART_ADD(87, Page.PART_ADD, "Has 'Cancel' Button On The 'Part Add' Page"),

	HAS_SAVE_BUTTON_PART_VIEW(88, Page.PART_VIEW, "Has 'Save' Button On The 'Part View' Page"),
	HAS_NEW_BUTTON_PART_VIEW(89, Page.PART_VIEW, "Has 'New' Button On The 'Part View' Page"),
	HAS_DELETE_BUTTON_PART_VIEW(90, Page.PART_VIEW, "Has 'Delete' Button On The 'Part View' Page"),
	HAS_CANCEL_BUTTON_PART_VIEW(91, Page.PART_VIEW, "Has 'Cancel' Button On The 'Part View' Page"),
	HAS_EDIT_BUTTON_PART_VIEW(92, Page.PART_VIEW, "Has 'Edit' Button On The 'Part View' Page"),

	HAS_SAVE_BUTTON_PROJECT_ADD(93, Page.PROJECT_ADD, "Has 'Save' Button On The 'Project Add ' Page"),
	HAS_NEW_BUTTON_PROJECT_ADD(94, Page.PROJECT_ADD, "Has 'New' Button On The 'Project Add' Page"),
	HAS_DELETE_BUTTON_PROJECT_ADD(95, Page.PROJECT_ADD, "Has 'Delete' Button On The 'Project Add' Page"),
	HAS_CANCEL_BUTTON_PROJECT_ADD(96, Page.PROJECT_ADD, "Has 'Cancel' Button On The 'Project Add' Page"),

	HAS_SAVE_BUTTON_PROJECT_VIEW(97, Page.PROJECT_VIEW, "Has 'Save' Button On The 'Project View' Page"),
	HAS_NEW_BUTTON_PROJECT_VIEW(98, Page.PROJECT_VIEW, "Has 'New' Button On The 'Project View' Page"),
	HAS_DELETE_BUTTON_PROJECT_VIEW(99, Page.PROJECT_VIEW, "Has 'Delete' Button On The 'Project View' Page"),
	HAS_CANCEL_BUTTON_PROJECT_VIEW(100, Page.PROJECT_VIEW, "Has 'Cancel' Button On The 'Project View' Page"),
	HAS_EDIT_BUTTON_PROJECT_VIEW(101, Page.PROJECT_VIEW, "Has 'Edit' Button On The 'Project View' Page"),

	HAS_SAVE_BUTTON_SCHEDULED_MAINTENANCE_ADD(102, Page.SCHEDULED_MAINTENANCE_ADD, "Has 'Save' Button On The 'Scheduled Maintenance Add ' Page"),
	HAS_NEW_BUTTON_SCHEDULED_MAINTENANCE_ADD(103, Page.SCHEDULED_MAINTENANCE_ADD, "Has 'New' Button On The 'Scheduled Maintenance Add' Page"),
	HAS_DELETE_BUTTON_SCHEDULED_MAINTENANCE_ADD(104, Page.SCHEDULED_MAINTENANCE_ADD, "Has 'Delete' Button On The 'Scheduled Maintenance Add' Page"),
	HAS_CANCEL_BUTTON_SCHEDULED_MAINTENANCE_ADD(105, Page.SCHEDULED_MAINTENANCE_ADD, "Has 'Cancel' Button On The 'Scheduled Maintenance Add' Page"),

	HAS_SAVE_BUTTON_SCHEDULED_MAINTENANCE_VIEW(106, Page.SCHEDULED_MAINTENANCE_VIEW, "Has 'Save' Button On The 'Scheduled Maintenance View' Page"),
	HAS_NEW_BUTTON_SCHEDULED_MAINTENANCE_VIEW(107, Page.SCHEDULED_MAINTENANCE_VIEW, "Has 'New' Button On The 'Scheduled Maintenance View' Page"),
	HAS_DELETE_BUTTON_SCHEDULED_MAINTENANCE_VIEW(108, Page.SCHEDULED_MAINTENANCE_VIEW, "Has 'Delete' Button On The 'Scheduled Maintenance View' Page"),
	HAS_CANCEL_BUTTON_SCHEDULED_MAINTENANCE_VIEW(109, Page.SCHEDULED_MAINTENANCE_VIEW, "Has 'Cancel' Button On The 'Scheduled Maintenance View' Page"),
	HAS_EDIT_BUTTON_SCHEDULED_MAINTENANCE_VIEW(110, Page.SCHEDULED_MAINTENANCE_VIEW, "Has 'Edit' Button On The 'Scheduled Maintenance View' Page"),

	HAS_SAVE_BUTTON_TASK_ADD(111, Page.TASK_ADD, "Has 'Save' Button On The 'Task Add ' Page"),
	HAS_NEW_BUTTON_TASK_ADD(112, Page.TASK_ADD, "Has 'New' Button On The 'Task Add' Page"),
	HAS_DELETE_BUTTON_TASK_ADD(113, Page.TASK_ADD, "Has 'Delete' Button On The 'Task Add' Page"),
	HAS_CANCEL_BUTTON_TASK_ADD(114, Page.TASK_ADD, "Has 'Cancel' Button On The 'Task Add' Page"),

	HAS_SAVE_BUTTON_TASK_VIEW(115, Page.TASK_VIEW, "Has 'Save' Button On The 'Task View' Page"),
	HAS_NEW_BUTTON_TASK_VIEW(116, Page.TASK_VIEW, "Has 'New' Button On The 'Task View' Page"),
	HAS_DELETE_BUTTON_TASK_VIEW(117, Page.TASK_VIEW, "Has 'Delete' Button On The 'Task View' Page"),
	HAS_CANCEL_BUTTON_TASK_VIEW(118, Page.TASK_VIEW, "Has 'Cancel' Button On The 'Task View' Page"),
	HAS_EDIT_BUTTON_TASK_VIEW(119, Page.TASK_VIEW, "Has 'Edit' Button On The 'Task View' Page"),

	HAS_SAVE_BUTTON_WORK_ORDER_ADD(120, Page.WORK_ORDER_ADD, "Has 'Save' Button On The 'Work Order Add ' Page"),
	HAS_NEW_BUTTON_WORK_ORDER_ADD(121, Page.WORK_ORDER_ADD, "Has 'New' Button On The 'Work Order Add' Page"),
	HAS_DELETE_BUTTON_WORK_ORDER_ADD(122, Page.WORK_ORDER_ADD, "Has 'Delete' Button On The 'Work Order Add' Page"),
	HAS_CANCEL_BUTTON_WORK_ORDER_ADD(114, Page.WORK_ORDER_ADD, "Has 'Cancel' Button On The 'Work Order Add' Page"),
	HAS_APPROVED_BUTTON_WORK_ORDER_ADD(207, Page.WORK_ORDER_ADD, "Has 'Approved' Button On The 'Work Order Add' Page"),
	HAS_CLOSED_BUTTON_WORK_ORDER_ADD(208, Page.WORK_ORDER_ADD, "Has 'Closed' Button On The 'Work Order Add' Page"),

	HAS_SAVE_BUTTON_WORK_ORDER_VIEW(123, Page.WORK_ORDER_VIEW, "Has 'Save' Button On The 'Work Order View' Page"),
	HAS_NEW_BUTTON_WORK_ORDER_VIEW(124, Page.WORK_ORDER_VIEW, "Has 'New' Button On The 'Work Order View' Page"),
	HAS_DELETE_BUTTON_WORK_ORDER_VIEW(125, Page.WORK_ORDER_VIEW, "Has 'Delete' Button On The 'Work Order View' Page"),
	HAS_CANCEL_BUTTON_WORK_ORDER_VIEW(126, Page.WORK_ORDER_VIEW, "Has 'Cancel' Button On The 'Work Order View' Page"),
	HAS_EDIT_BUTTON_WORK_ORDER_VIEW(127, Page.WORK_ORDER_VIEW, "Has 'Edit' Button On The 'Work Order View' Page"),

	HAS_SAVE_BUTTON_PURCHASE_ORDER_ADD(128, Page.PURCHASE_ORDER_ADD, "Has 'Save' Button On The 'Purchase Order Add ' Page"),
	HAS_NEW_BUTTON_PURCHASE_ORDER_ADD(129, Page.PURCHASE_ORDER_ADD, "Has 'New' Button On The 'Purchase Order Add' Page"),
	HAS_DELETE_BUTTON_PURCHASE_ORDER_ADD(130, Page.PURCHASE_ORDER_ADD, "Has 'Delete' Button On The 'Purchase Order Add' Page"),
	HAS_CANCEL_BUTTON_PURCHASE_ORDER_ADD(131, Page.PURCHASE_ORDER_ADD, "Has 'Cancel' Button On The 'Purchase Order Add' Page"),
	HAS_SUBMIT_FOR_APPROVAL_BUTTON_PURCHASE_ORDER_ADD(132, Page.PURCHASE_ORDER_ADD, "Has 'Submit For Approval' Button On The 'Purchase Order Add' Page"),
	HAS_PURCHASE_ORDER_APPROVED_BUTTON_PURCHASE_ORDER_ADD(133, Page.PURCHASE_ORDER_ADD, "Has 'Purchase Order Approved' Button On The 'Purchase Order Add' Page"),
	HAS_PURCHASE_ORDER_REJECT_BUTTON_PURCHASE_ORDER_ADD(134, Page.PURCHASE_ORDER_ADD, "Has 'Reject Purchase Order' Button On The 'Purchase Order Add' Page"),
	HAS_PURCHASE_ORDER_CANCEL_BUTTON_PURCHASE_ORDER_ADD(135, Page.PURCHASE_ORDER_ADD, "Has 'Cancel Purchase Order' Button On The 'Purchase Order Add' Page"),
	HAS_BACK_TO_DRAFT_BUTTON_PURCHASE_ORDER_ADD(136, Page.PURCHASE_ORDER_ADD, "Has 'Back To Draft' Button On The 'Purchase Order Add' Page"),

	HAS_SAVE_BUTTON_PURCHASE_ORDER_VIEW(137, Page.PURCHASE_ORDER_VIEW, "Has 'Save' Button On The 'Purchase Order View' Page"),
	HAS_NEW_BUTTON_PURCHASE_ORDER_VIEW(138, Page.PURCHASE_ORDER_VIEW, "Has 'New' Button On The 'Purchase Order View' Page"),
	HAS_DELETE_BUTTON_PURCHASE_ORDER_VIEW(139, Page.PURCHASE_ORDER_VIEW, "Has 'Delete' Button On The 'Purchase Order View' Page"),
	HAS_CANCEL_BUTTON_PURCHASE_ORDER_VIEW(140, Page.PURCHASE_ORDER_VIEW, "Has 'Cancel' Button On The 'Purchase Order View' Page"),
	HAS_EDIT_BUTTON_PURCHASE_ORDER_VIEW(141, Page.PURCHASE_ORDER_VIEW, "Has 'Edit' Button On The 'Purchase Order View' Page"),

	HAS_SAVE_BUTTON_RECEIPT_ORDER_ADD(142, Page.RECEIPT_ORDER_ADD, "Has 'Save' Button On The 'Receipt Order Add ' Page"),
	HAS_NEW_BUTTON_RECEIPT_ORDER_ADD(143, Page.RECEIPT_ORDER_ADD, "Has 'New' Button On The 'Receipt Order Add' Page"),
	HAS_DELETE_BUTTON_RECEIPT_ORDER_ADD(145, Page.RECEIPT_ORDER_ADD, "Has 'Delete' Button On The 'Receipt Order Add' Page"),
	HAS_CANCEL_BUTTON_RECEIPT_ORDER_ADD(146, Page.RECEIPT_ORDER_ADD, "Has 'Cancel' Button On The 'Receipt Order Add' Page"),
	HAS_RECEIVE_BUTTON_RECEIPT_ORDER_ADD(147, Page.RECEIPT_ORDER_ADD, "Has 'Receive' Button On The 'Receipt Order Add' Page"),
	HAS_RECEIPT_ORDER_CANCEL_BUTTON_RECEIPT_ORDER_ADD(148, Page.RECEIPT_ORDER_ADD, "Has 'Cancel Receipt Order' Button On The 'Receipt Order Add' Page"),

	HAS_SAVE_BUTTON_RECEIPT_ORDER_VIEW(149, Page.RECEIPT_ORDER_VIEW, "Has 'Save' Button On The 'Receipt Order View' Page"),
	HAS_NEW_BUTTON_RECEIPT_ORDER_VIEW(150, Page.RECEIPT_ORDER_VIEW, "Has 'New' Button On The 'Receipt Order View' Page"),
	HAS_DELETE_BUTTON_RECEIPT_ORDER_VIEW(151, Page.RECEIPT_ORDER_VIEW, "Has 'Delete' Button On The 'Receipt Order View' Page"),
	HAS_CANCEL_BUTTON_RECEIPT_ORDER_VIEW(152, Page.RECEIPT_ORDER_VIEW, "Has 'Cancel' Button On The 'Receipt Order View' Page"),
	HAS_EDIT_BUTTON_RECEIPT_ORDER_VIEW(153, Page.RECEIPT_ORDER_VIEW, "Has 'Edit' Button On The 'Receipt Order View' Page"),

	HAS_SAVE_BUTTON_RFQ_ADD(154, Page.RFQ_ADD, "Has 'Save' Button On The 'RFQ Add ' Page"),
	HAS_NEW_BUTTON_RFQ_ADD(155, Page.RFQ_ADD, "Has 'New' Button On The 'RFQ Add' Page"),
	HAS_DELETE_BUTTON_RFQ_ADD(156, Page.RFQ_ADD, "Has 'Delete' Button On The 'RFQ Add' Page"),
	HAS_CANCEL_BUTTON_RFQ_ADD(157, Page.RFQ_ADD, "Has 'Cancel' Button On The 'RFQ Add' Page"),
	HAS_REQUEST_QUOTATION_BUTTON_RFQ_ADD(158, Page.RFQ_ADD, "Has 'Request quota' Button On The 'RFQ Add' Page"),
	HAS_CANCEL_QUOTATION_BUTTON_RFQ_ADD(159, Page.RFQ_ADD, "Has 'Cancel quota' Button On The 'RFQ Add' Page"),

	HAS_SAVE_BUTTON_RFQ_VIEW(160, Page.RFQ_VIEW, "Has 'Save' Button On The 'RFQ View' Page"),
	HAS_NEW_BUTTON_RFQ_VIEW(161, Page.RFQ_VIEW, "Has 'New' Button On The 'RFQ View' Page"),
	HAS_DELETE_BUTTON_RFQ_VIEW(162, Page.RFQ_VIEW, "Has 'Delete' Button On The 'RFQ View' Page"),
	HAS_CANCEL_BUTTON_RFQ_VIEW(163, Page.RFQ_VIEW, "Has 'Cancel' Button On The 'RFQ View' Page"),
	HAS_EDIT_BUTTON_RFQ_VIEW(164, Page.RFQ_VIEW, "Has 'Edit' Button On The 'RFQ View' Page"),

//	HAS_UPDATE_BUTTON_USER_PROFILE_VIEW(165, Page.USER_PROFILE_ADD, "Has 'Update' Button On The 'User Profile Add' Page"),

	HAS_SAVE_BUTTON_APP_ADD(166, Page.APP_ADD, "Has 'Save' Button On The 'App Add ' Page"),
	HAS_NEW_BUTTON_APP_ADD(167, Page.APP_ADD, "Has 'New' Button On The 'App Add' Page"),
	HAS_DELETE_BUTTON_APP_ADD(168, Page.APP_ADD, "Has 'Delete' Button On The 'App Add' Page"),
	HAS_CANCEL_BUTTON_APP_ADD(169, Page.APP_ADD, "Has 'Cancel' Button On The 'App Add' Page"),

	HAS_SAVE_BUTTON_APP_VIEW(170, Page.APP_VIEW, "Has 'Save' Button On The 'App View' Page"),
	HAS_NEW_BUTTON_APP_VIEW(171, Page.APP_VIEW, "Has 'New' Button On The 'App View' Page"),
	HAS_DELETE_BUTTON_APP_VIEW(172, Page.APP_VIEW, "Has 'Delete' Button On The 'App View' Page"),
	HAS_CANCEL_BUTTON_APP_VIEW(173, Page.APP_VIEW, "Has 'Cancel' Button On The 'App View' Page"),
	HAS_EDIT_BUTTON_APP_VIEW(174, Page.APP_VIEW, "Has 'Edit' Button On The 'App View' Page"),

	HAS_SAVE_BUTTON_AOD_ADD(175, Page.AOD_ADD, "Has 'Save' Button On The 'AOD Add' Page"),
	HAS_NEW_BUTTON_AOD_ADD(176, Page.AOD_ADD, "Has 'New' Button On The 'AOD Add' Page"),
	HAS_DELETE_BUTTON_AOD_ADD(177, Page.AOD_ADD, "Has 'Delete' Button On The 'AOD Add' Page"),
	HAS_CANCEL_BUTTON_AOD_ADD(178, Page.AOD_ADD, "Has 'Cancel' Button On The 'AOD Add' Page"),
	HAS_STATUS_CHANGE_BUTTON_AOD_ADD(179, Page.AOD_ADD, "Has 'Status Change' Button On The 'AOD Add' Page"),
	HAS_PRINT_BUTTON_AOD_ADD(180, Page.AOD_ADD, "Has 'Print' Button On The 'AOD Add' Page"),

	HAS_SAVE_BUTTON_AOD_VIEW(181, Page.AOD_VIEW, "Has 'Save' Button On The 'AOD View' Page"),
	HAS_NEW_BUTTON_AOD_VIEW(182, Page.AOD_VIEW, "Has 'New' Button On The 'AOD View' Page"),
	HAS_DELETE_BUTTON_AOD_VIEW(183, Page.AOD_VIEW, "Has 'Delete' Button On The 'AOD View' Page"),
	HAS_CANCEL_BUTTON_AOD_VIEW(184, Page.AOD_VIEW, "Has 'Cancel' Button On The 'AOD View' Page"),
	HAS_EDIT_FUNCTION_AOD_VIEW(185, Page.AOD_VIEW, "Has 'Edit' Button On The 'AOD View' Page"),

	HAS_SAVE_BUTTON_AOD_RETURN_ADD(186, Page.AOD_RETURN_ADD, "Has 'Save' Button On The 'AOD Return Add' Page"),
	HAS_NEW_BUTTON_AOD_RETURN_ADD(187, Page.AOD_RETURN_ADD, "Has 'New' Button On The 'AOD Return Add' Page"),
	HAS_DELETE_BUTTON_AOD_RETURN_ADD(188, Page.AOD_RETURN_ADD, "Has 'Delete' Button On The 'AOD Return Add' Page"),
	HAS_CANCEL_BUTTON_AOD_RETURN_ADD(189, Page.AOD_RETURN_ADD, "Has 'Cancel' Button On The 'AOD Return Add' Page"),

	HAS_SAVE_BUTTON_AOD_RETURN_VIEW(190, Page.AOD_RETURN_VIEW, "Has 'Save' Button On The 'AOD Return View' Page"),
	HAS_NEW_BUTTON_AOD_RETURN_VIEW(191, Page.AOD_RETURN_VIEW, "Has 'New' Button On The 'AOD Return View' Page"),
	HAS_DELETE_BUTTON_AOD_RETURN_VIEW(192, Page.AOD_RETURN_VIEW, "Has 'Delete' Button On The 'AOD Return View' Page"),
	HAS_CANCEL_BUTTON_AOD_RETURN_VIEW(193, Page.AOD_RETURN_VIEW, "Has 'Cancel' Button On The 'AOD Return View' Page"),
	HAS_EDIT_FUNCTION_AOD_RETURN_VIEW(194, Page.AOD_RETURN_VIEW, "Has 'Edit' Button On The 'AOD Return View' Page"),

	HAS_NEW_BUTTON_STOCK_ADD(195, Page.STOCK_ADD, "Has 'New' Button On The 'Stock Add' Page"),
	HAS_VIEW_BUTTON_STOCK_ADD(196, Page.STOCK_ADD, "Has 'View Stock' Button On The 'Stock Add' Page"),
	HAS_CANCEL_BUTTON_STOCK_ADD(197, Page.STOCK_ADD, "Has 'Cancel' Button On The 'Stock  Add' Page"),

	HAS_NEW_BUTTON_STOCK_VIEW(198, Page.STOCK_VIEW, "Has 'New' Button On The 'Stock  View' Page"),
	HAS_CANCEL_BUTTON_STOCK_VIEW(199, Page.STOCK_VIEW, "Has 'Cancel' Button On The 'Stock  View' Page"),

	HAS_NEW_BUTTON_STOCK_ADJUSTMENT_ADD(200, Page.STOCK_ADJUSTMENT_ADD, "Has 'New' Button On The 'Stock Adjustment Add' Page"),
	HAS_UPDATE_BUTTON_STOCK_ADJUSTMENT_ADD(201, Page.STOCK_ADJUSTMENT_ADD, "Has 'Update' Button On The 'Stock Adjustment Add' Page"),
	HAS_CANCEL_BUTTON_STOCK_ADJUSTMENT_ADD(202, Page.STOCK_ADJUSTMENT_ADD, "Has 'Cancel' Button On The 'Stock Adjustment Add' Page"),

	HAS_SAVE_BUTTON_STOCK_ADJUSTMENT_VIEW(203, Page.STOCK_ADJUSTMENT_VIEW, "Has 'Save' Button On The 'Stock Adjustment View' Page"),
	HAS_NEW_BUTTON_STOCK_ADJUSTMENT_VIEW(204, Page.STOCK_ADJUSTMENT_VIEW, "Has 'New' Button On The 'Stock Adjustment View' Page"),
	HAS_DELETE_BUTTON_STOCK_ADJUSTMENT_VIEW(205, Page.STOCK_ADJUSTMENT_VIEW, "Has 'Delete' Button On The 'Stock Adjustment View' Page"),
	HAS_CANCEL_BUTTON_STOCK_ADJUSTMENT_VIEW(206, Page.STOCK_ADJUSTMENT_VIEW, "Has 'Cancel' Button On The 'Stock Adjustment View' Page"),

	HAS_SAVE_BUTTON_WEARHOUSE_VIEW(207, Page.WEARHOUSE_VIEW, "Has 'Save' Button On The 'Warehouse View' Page"),
	HAS_NEW_BUTTON_WEARHOUSE_VIEW(208, Page.WEARHOUSE_VIEW, "Has 'New' Button On The 'Warehouse View' Page"),
	HAS_DELETE_BUTTON_WEARHOUSE_VIEW(209, Page.WEARHOUSE_VIEW, "Has 'Delete' Button On The 'Warehouse View' Page"),
	HAS_CANCEL_BUTTON_WEARHOUSE_VIEW(210, Page.WEARHOUSE_VIEW, "Has 'Cancel' Button On The 'Warehouse View' Page"),
	HAS_EDIT_FUNCTION_WEARHOUSE_VIEW(211, Page.WEARHOUSE_VIEW, "Has 'Edit' Button On The 'Warehouse View' Page"),
	
	HAS_SAVE_BUTTON_WEARHOUSE_ADD(212, Page.WEARHOUSE_ADD, "Has 'Save' Button On The 'Wear Housee Add' Page"),
	HAS_NEW_BUTTON_WEARHOUSE_ADD(213, Page.WEARHOUSE_ADD, "Has 'New' Button On The 'Warehouse Add' Page"),
	HAS_DELETE_BUTTON_WEARHOUSE_ADD(214, Page.WEARHOUSE_ADD, "Has 'Delete' Button On The 'Warehouse Add' Page"),
	HAS_CANCEL_BUTTON_WEARHOUSE_ADD(215, Page.WEARHOUSE_ADD, "Has 'Cancel' Button On The 'Warehouse Add' Page"),
	
	HAS_ASSET_WIGET(216, Page.DASHBOARD_VIEW, "Has 'Asset' Widget On The 'Dashboard' "),
	HAS_MAINTAINACE_WIGET(217, Page.DASHBOARD_VIEW, "Has 'Maintainace' Widget On The 'Dashboard'"),
	HAS_INVENTORY_WIGET(218, Page.DASHBOARD_VIEW, "Has 'Inventory' Widget On The 'Dashboard'"),
	HAS_NOTIFICATION_WIGET(219, Page.DASHBOARD_VIEW, "Has 'Notification' Widget On The 'Dashboard'"),
	HAS_BIZ_WIGET(220, Page.DASHBOARD_VIEW, "Has 'Maintainace' Widget On The 'Dashboard'"),
	HAS_SETTINGS_WIGET(221, Page.DASHBOARD_VIEW, "Has 'Settings' Widget On The 'Dashboard'");

	
	private Integer id;
	private String name;
	private Page page;

	private PagePermission(Integer id, Page page, String name){
		setId(id);
		setName(name);
		setPage(page);
	}

	public static List<PagePermission> getPagePermissionsByPage(Page page) {
		List<PagePermission> list = new ArrayList<PagePermission>();

		PagePermission[] arry = PagePermission.values();
		for (PagePermission element : arry) {
			if (element.getPage().equals(page)) {
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

	public Page getPage() {
		return page;
	}

	private void setPage(Page page) {
		this.page = page;
	}

}
