package com.codex.ecam.service.maintenance.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.dto.maintenance.exworkorder.ExWorkOrderDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.maintenance.ExWorkOrderResult;

public interface ExWorkOrderService {

	
	ExWorkOrderDTO findById(Integer id) throws Exception;

	ExWorkOrderResult delete(Integer id);

	ExWorkOrderResult save(ExWorkOrderDTO exWorkOrderDTO);

	ExWorkOrderResult statusChange(Integer id, WorkOrderStatus status, String date, String note) throws Exception;

	DataTablesOutput<ExWorkOrderDTO> findAll(FocusDataTablesInput input) throws Exception;




}
