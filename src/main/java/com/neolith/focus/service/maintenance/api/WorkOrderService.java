package com.neolith.focus.service.maintenance.api;

import com.neolith.focus.constants.WorkOrderStatus;
import com.neolith.focus.dto.maintenance.workOrder.WorkOrderDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.RestResult;
import com.neolith.focus.result.maintenance.WorkOrderResult;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.multipart.MultipartFile;

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
