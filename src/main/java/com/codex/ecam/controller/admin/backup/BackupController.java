package com.codex.ecam.controller.admin.backup;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codex.ecam.service.admin.api.BackupService;

@Controller
@RequestMapping(BackupController.REQUEST_MAPPING_URL)
public class BackupController {

	public static final String REQUEST_MAPPING_URL = "/backup";

	@Autowired
	private BackupService backupService;

	@RequestMapping(value = "/download-backup", method = RequestMethod.POST)
	public void downloadBackupFile(@RequestParam("businessId") Integer businessId, HttpServletResponse response) throws Exception {
		backupService.createBackup(businessId, response);
	}

}