package com.neolith.focus.service.admin.api;

import javax.servlet.http.HttpServletResponse;

public interface BackupService {

	void createBackup(Integer businessId, HttpServletResponse response);

}
