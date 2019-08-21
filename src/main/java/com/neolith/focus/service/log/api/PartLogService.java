package com.neolith.focus.service.log.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.biz.part.PartLogDTO;
import com.neolith.focus.repository.FocusDataTablesInput;

public interface PartLogService {

	DataTablesOutput<PartLogDTO> findAllByPartId(FocusDataTablesInput input, Integer id) throws Exception;
	
}
