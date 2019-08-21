package com.codex.ecam.service.admin.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.codex.ecam.service.admin.api.BackupService;
import com.codex.ecam.util.FileDownloadUtil;

@Service
public class BackupServiceImpl implements BackupService {

	@Autowired
	private Environment environment;

	@Override
	public void createBackup(Integer businessId, HttpServletResponse response) {

		try {

			String externalFilePath = environment.getProperty("backup.location") + "/Dump_" + getDateTime() + ".sql";

			List<String[]> cmds = createDumpCreationCmds(environment.getProperty("jdbc.host"), environment.getProperty("jdbc.user"),
					environment.getProperty("jdbc.password"), externalFilePath, environment.getProperty("jdbc.db"), businessId);

			for ( String[] cmd : cmds) {

				Process proc = Runtime.getRuntime().exec(cmd);
				InputStream in = proc.getInputStream();
				byte buff[] = new byte[1024];
				try {
					while (in.read(buff) != -1) {
						// Use the output of the process...
					}
					// No more output was available from the process, so...
					// Ensure that the process completes
					proc.waitFor();
				} catch (IOException e) {
					// Insert code to handle exceptions that occur
					// when reading the process output
				} catch (InterruptedException e) {
					// Handle exception that could occur when waiting
					// for a spawned process to terminate
				}

				// Then examine the process exit code
				if (proc.exitValue() == 1) {
					// Use the exit value...
				}
			}

			FileDownloadUtil.flushFile(externalFilePath, "text/plain", response);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getDateTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
		return df.format(new Date());
	}

	private List<String[]> createDumpCreationCmds (String host, String userName, String password, String file, String dbName, Integer businessId) {

		List<String[]> cmds = new ArrayList<>();

		String[] cmd1 = {"/bin/sh","-c", "mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_business --where=id='" +  businessId + "' --single-transaction > " + file };
		cmds.add(cmd1);

		String[] cmd2 = {"/bin/sh","-c", "mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_activity_log tbl_app tbl_app_menu tbl_asset_brand tbl_asset_model "
				+ "tbl_asset_offline_tracker tbl_asset_owner tbl_asset_status tbl_business_classification tbl_business_group tbl_business_role_type tbl_contact tbl_country tbl_currency "
				+ "tbl_meter_reading_unit tbl_notification_folder tbl_parts tbl_persistent_login tbl_purchase_order_status_transition tbl_purchase_order_status_transition_permission "
				+ "tbl_receipt_order_status_transition tbl_related_app tbl_rfq_status tbl_rfq_status_transition tbl_status tbl_supplier tbl_task_type tbl_tax tbl_wo_cost tbl_wo_cost_account "
				+ "tbl_wo_cost_department tbl_wo_group tbl_wo_group_task tbl_wo_task_asset --single-transaction >> " + file};
		cmds.add(cmd2);

		String[] cmd3 = {"/bin/sh","-c", "mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_aod --where=business_id='" +  businessId + "', tbl_account --where=business_id='" +  businessId + "', "
				+ "tbl_asset --where=business_id='" +  businessId + "', tbl_wo --where=business_id='" +  businessId + "', tbl_account --where=business_id='" +  businessId + "', tbl_asset_event_type --where=business_id='" +  businessId + "', "
				+ "tbl_aod_return --where=business_id='" +  businessId + "', tbl_asset_business --where=business_id='" +  businessId + "', tbl_asset_catogery --where=business_id='" +  businessId + "', tbl_bom_group --where=business_id='" +  businessId + "', "
				+ "tbl_business_app --where=business_id='" +  businessId + "', tbl_business_contact --where=business_id='" +  businessId + "', tbl_business_role --where=business_id='" +  businessId + "', tbl_business_type_def --where=business_id='" +  businessId + "', "
				+ "tbl_certification --where=business_id='" +  businessId + "', tbl_charge_department --where=business_id='" +  businessId + "', tbl_customer --where=business_id='" +  businessId + "', tbl_inventory_group --where=business_id='" +  businessId + "', "
				+ "tbl_maintenance_type --where=business_id='" +  businessId + "', tbl_miscellaneous_expense_type --where=business_id='" +  businessId + "', tbl_notification --where=business_id='" +  businessId + "', tbl_priority --where=business_id='" +  businessId + "', "
				+ "tbl_project --where=business_id='" +  businessId + "', tbl_purchase_order --where=business_id='" +  businessId + "', tbl_quotation --where=business_id='" +  businessId + "', tbl_receipt_order --where=business_id='" +  businessId + "', "
				+ "tbl_rfq --where=business_id='" +  businessId + "', tbl_scheduled_maintenance --where=business_id='" +  businessId + "', tbl_stock --where=business_id='" +  businessId + "', tbl_task_group --where=business_id='" +  businessId + "', "
				+ "tbl_user --where=business_id='" +  businessId + "', tbl_user_group --where=business_id='" +  businessId + "', tbl_user_job_title --where=business_id='" +  businessId + "', tbl_user_skill_level --where=business_id='" +  businessId + "', "
				+ "tbl_wo --where=business_id='" +  businessId + "', tbl_wo_request --where=business_id='" +  businessId + "' --single-transaction >> " + file};
		cmds.add(cmd3);

		String[] cmd4 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_business_virtual --where=business_owner_id='" +  businessId + "' --single-transaction >> " + file};
		cmds.add(cmd4);

		String[] cmd5 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_aod_item --where \"aod_id in (select id from tbl_aod where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd5);

		String[] cmd6 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_asset_consuming_ref --where \"asset_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd6);

		String[] cmd7 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_asset_customer --where \"asset_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd7);

		String[] cmd8 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_asset_event_type_asset --where \"asset_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd8);

		String[] cmd9 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_asset_file --where \"asset_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd9);

		String[] cmd10 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_asset_log --where \"asset_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd10);

		String[] cmd11 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_asset_maintainace --where \"asset_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd11);

		String[] cmd12 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_asset_meter_reading --where \"asset_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd12);

		String[] cmd13 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_asset_user --where \"asset_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd13);

		String[] cmd14 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_avg_price --where \"part_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd14);

		String[] cmd15 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_bom_group_part --where \"part_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd15);

		String[] cmd16 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_customer_contact --where \"customer_id in (select id from tbl_customer where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd16);

		String[] cmd17 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_inventory_group_part --where \"inventory_group_id in (select id from tbl_inventory_group where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd17);

		String[] cmd18 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_part_notification --where \"part_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd18);

		String[] cmd19 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_project_user --where \"project_id in (select id from tbl_project where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd19);

		String[] cmd20 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_purchase_order_additional_cost --where \"purchase_order_id in (select id from tbl_purchase_order where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd20);

		String[] cmd21 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_purchase_order_discussion --where \"purchase_order_id in (select id from tbl_purchase_order where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd21);

		String[] cmd22 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_purchase_order_item --where \"purchase_order_id in (select id from tbl_purchase_order where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd22);

		String[] cmd23 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_purchase_order_log --where \"purchase_order_id in (select id from tbl_purchase_order where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd23);

		String[] cmd24 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_purchase_order_notification --where \"purchase_order_id in (select id from tbl_purchase_order where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd24);

		String[] cmd25 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_purchase_order_user --where \"purchase_order_id in (select id from tbl_purchase_order where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd25);

		String[] cmd26 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_receipt_order_item --where \"receipt_order_id in (select id from tbl_receipt_order where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd26);

		String[] cmd27 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_receipt_order_tax --where \"receipt_order_id in (select id from tbl_receipt_order where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd27);

		String[] cmd28 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_rfq_item --where \"rfq_id in (select id from tbl_rfq where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd28);

		String[] cmd29 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_rfq_queue --where \"asset_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd29);

		String[] cmd30 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_scheduled_maintenance_asset --where \"scheduled_maintenance_id in (select id from tbl_scheduled_maintenance where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd30);

		String[] cmd31 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_scheduled_maintenance_file --where \"scheduled_maintenance_id in (select id from tbl_scheduled_maintenance where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd31);

		String[] cmd32 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_scheduled_maintenance_log --where \"scheduled_maintenance_id in (select id from tbl_scheduled_maintenance where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd32);

		String[] cmd33 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_scheduled_maintenance_nesting --where \"scheduled_maintenance_id in (select id from tbl_scheduled_maintenance where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd33);

		String[] cmd34 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_scheduled_maintenance_notification --where \"scheduled_maintenance_id in (select id from tbl_scheduled_maintenance where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd34);

		String[] cmd35 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_scheduled_maintenance_trigger --where \"scheduled_maintenance_id in (select id from tbl_scheduled_maintenance where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd35);

		String[] cmd36 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_scheduled_maintenance_user --where \"scheduled_maintenance_id in (select id from tbl_scheduled_maintenance where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd36);

		String[] cmd37 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_stock_adjustment --where \"stock_id in (select id from tbl_stock where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd37);

		String[] cmd38 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_stock_history --where \"stock_id in (select id from tbl_stock where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd38);

		String[] cmd39 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_stock_notification --where \"stock_id in (select id from tbl_stock where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd39);

		String[] cmd40 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_stock_purchase_item --where \"stock_id in (select id from tbl_stock where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd40);

		String[] cmd41 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_task --where \"task_group_id in (select id from tbl_task_group where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd41);

		String[] cmd42 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_user_certification --where \"user_id in (select id from tbl_user where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd42);

		String[] cmd43 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_user_credentials --where \"user_id in (select id from tbl_user where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd43);

		String[] cmd44 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_user_file --where \"user_id in (select id from tbl_user where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd44);

		String[] cmd45 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_user_group_menu --where \"user_group_id in (select id from tbl_user_group where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd45);

		String[] cmd46 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_user_group_page --where \"user_group_id in (select id from tbl_user_group where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd46);

		String[] cmd47 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_user_po_status_transition_permission --where \"user_id in (select id from tbl_user where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd47);

		String[] cmd48 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_user_report_to --where \"user_id in (select id from tbl_user where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd48);

		String[] cmd49 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_user_site --where \"user_id in (select id from tbl_user where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd49);

		String[] cmd50 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_user_token --where \"user_id in (select id from tbl_user where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd50);

		String[] cmd51 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_warranty --where \"asset_id in (select id from tbl_asset where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd51);

		String[] cmd52 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_asset --where \"work_order_id in (select id from tbl_wo where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd52);

		String[] cmd53 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_file --where \"wo_id in (select id from tbl_wo where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd53);

		String[] cmd54 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_labor --where \"wo_id in (select id from tbl_wo where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd54);

		String[] cmd55 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_log --where \"work_order_id in (select id from tbl_wo where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd55);

		String[] cmd56 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_meter_readings --where \"work_order_id in (select id from tbl_wo where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd56);

		String[] cmd57 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_miscellaneouscost --where \"wo_id in (select id from tbl_wo where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd57);

		String[] cmd58 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_miscellaneous_expense --where \"work_order_id in (select id from tbl_wo where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd58);

		String[] cmd59 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_notification --where \"work_order_id in (select id from tbl_wo where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd59);

		String[] cmd60 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_parts --where \"work_order_id in (select id from tbl_wo where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd60);

		String[] cmd61 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_request_log --where \"wo_request_id in (select id from tbl_wo_request where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd61);

		String[] cmd62 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_request_task --where \"wo_request_id in (select id from tbl_wo_request where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd62);

		String[] cmd63 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_task --where \"work_order_id in (select id from tbl_wo where business_id='" +  businessId + "')\" --single-transaction >> " + file};
		cmds.add(cmd63);

		String[] cmd64 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_asset_event --where \"asset_event_type_asset_id in (select id from tbl_asset_event_type_asset where asset_event_type_id in (select id from tbl_asset_event_type where business_id='" +  businessId + "'))\" --single-transaction >> " + file};
		cmds.add(cmd64);

		String[] cmd65 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_aod_item_stock --where \"aod_item_id in (select id from tbl_aod_item where aod_id in (select id from tbl_aod where business_id='" +  businessId + "'))\" --single-transaction >> " + file};
		cmds.add(cmd65);

		String[] cmd66 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_asset_meter_reading_value --where \"asset_meter_reading_id in (select id from tbl_asset_meter_reading where asset_id in (select id from tbl_asset where business_id='" +  businessId + "'))\" --single-transaction >> " + file};
		cmds.add(cmd66);

		String[] cmd67 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_calendar_event --where \"scheduled_trigger_id in (select id from tbl_scheduled_maintenance_trigger where scheduled_maintenance_id in (select id from tbl_scheduled_maintenance where business_id='" +  businessId + "'))\" --single-transaction >> " + file};
		cmds.add(cmd67);

		String[] cmd68 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_purchase_order_item_rfq_item --where \"purchase_order_item_id in (select id from tbl_purchase_order_item where purchase_order_id in (select id from tbl_purchase_order where business_id='" +  businessId + "'))\" --single-transaction >> " + file};
		cmds.add(cmd68);

		String[] cmd69 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_receipt_order_item_serial_number --where \"receipt_order_item_id in (select id from tbl_receipt_order_item where receipt_order_id in (select id from tbl_receipt_order where business_id='" +  businessId + "'))\" --single-transaction >> " + file};
		cmds.add(cmd69);

		String[] cmd70 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_scheduled_maintenance_task --where \"scheduled_maintenance_trigger_id in (select id from tbl_scheduled_maintenance_trigger where scheduled_maintenance_id in (select id from tbl_scheduled_maintenance where business_id='" +  businessId + "'))\" --single-transaction >> " + file};
		cmds.add(cmd70);

		String[] cmd71 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_user_group_menu_sub_menu --where \"user_group_menu_id in (select id from tbl_user_group_menu where user_group_id in (select id from tbl_user_group where business_id='" +  businessId + "'))\" --single-transaction >> " + file};
		cmds.add(cmd71);

		String[] cmd72 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_user_group_page_permission --where \"user_group_page_id in (select id from tbl_user_group_page where user_group_id in (select id from tbl_user_group where business_id='" +  businessId + "'))\" --single-transaction >> " + file};
		cmds.add(cmd72);

		String[] cmd73 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_user_site_group --where \"user_site_id in (select id from tbl_user_site where user_id in (select id from tbl_user where business_id='" +  businessId + "'))\" --single-transaction >> " + file};
		cmds.add(cmd73);

		String[] cmd74 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_task_labor --where \"wo_task_id in (select id from tbl_wo_task where work_order_id in (select id from tbl_wo where business_id='" +  businessId + "'))\" --single-transaction >> " + file};
		cmds.add(cmd74);

		String[] cmd75 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_wo_task_part --where \"wo_task_id in (select id from tbl_wo_task where work_order_id in (select id from tbl_wo where business_id='" +  businessId + "'))\" --single-transaction >> " + file};
		cmds.add(cmd75);

		String[] cmd76 = {"/bin/sh", "-c" ,"mysqldump -h" + host + " -u" + userName +" -p" + password + " " + dbName + " tbl_scheduled_maintenance_part --where \"scheduled_maintenance_task_id in (select id from tbl_scheduled_maintenance_task where scheduled_maintenance_trigger_id in (select id from tbl_scheduled_maintenance_trigger where scheduled_maintenance_id in (select id from tbl_scheduled_maintenance where business_id='" +  businessId + "')))\" --single-transaction >> " + file};
		cmds.add(cmd76);

		return cmds;

	}

}
