package com.codex.ecam.service.maintenance.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.RestResult;
import com.codex.ecam.result.maintenance.WorkOrderResult;

import javax.servlet.http.HttpServletResponse;

public interface WorkOrderService {

	void workorderFileDownload(Integer refId, HttpServletResponse response) throws Exception ;
	
	WorkOrderDTO findById(Integer id) throws Exception;

	WorkOrderResult delete(Integer id);

	WorkOrderResult save(WorkOrderDTO workOrderDTO);

	WorkOrderResult statusChange(Integer id, WorkOrderStatus status, String date, String note) throws Exception;

	DataTablesOutput<WorkOrderDTO> findAll(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<WorkOrderDTO> findAllByProjectId(FocusDataTablesInput dataTablesInput, Integer id) throws Exception;

	DataTablesOutput<WorkOrderDTO> findWorkOrdersByDate(FocusDataTablesInput input, String date) throws Exception;

	RestResult<String> findCurrentWorkOrderCode(Integer businessId);

	String workorderFileUpload(MultipartFile file, String refId) throws Exception ;

	DataTablesOutput<WorkOrderDTO> findAllByBusiness(FocusDataTablesInput dataTablesInput, Integer id) throws Exception;


}
