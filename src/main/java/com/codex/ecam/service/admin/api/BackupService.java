package com.codex.ecam.service.admin.api;

import javax.servlet.http.HttpServletResponse;

public interface BackupService {

	void createBackup(Integer businessId, HttpServletResponse response);

}
