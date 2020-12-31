package com.codex.ecam.service.log.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.biz.part.PartLogDTO;
import com.codex.ecam.repository.FocusDataTablesInput;

public interface PartLogService {

	DataTablesOutput<PartLogDTO> findAllByPartId(FocusDataTablesInput input, Integer id) throws Exception;
	
}
