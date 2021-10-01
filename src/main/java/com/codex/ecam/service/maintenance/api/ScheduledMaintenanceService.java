package com.codex.ecam.service.maintenance.api;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.RestResult;
import com.codex.ecam.result.maintenance.ScheduledMaintenanceResult;

public interface ScheduledMaintenanceService {

	void scheduledMaintenanceFileDownload(Integer refId,HttpServletResponse response) throws Exception;

	DataTablesOutput<ScheduledMaintenanceDTO> findAll(FocusDataTablesInput dataTablesInput) throws Exception;

	DataTablesOutput<ScheduledMaintenanceDTO> findAllByProjectId(FocusDataTablesInput dataTablesInput, Integer id) throws Exception;

	ScheduledMaintenanceDTO findById(Integer id) throws Exception;

	ScheduledMaintenanceResult save(ScheduledMaintenanceDTO scheduleMaintenance);

	ScheduledMaintenanceResult delete(Integer id);

	RestResult<String> findCurrentScheduledMaintenanceCode(Integer businessId);

	String scheduledMaintenanceFileUpload(MultipartFile file,String refId) throws Exception;

	ScheduledMaintenanceResult deleteMultiple(Integer[] ids) throws Exception;

}
