package com.codex.ecam.mappers.report;

import com.codex.ecam.dto.report.data.aodReturn.AODReturnRepDTO;
import com.codex.ecam.mappers.GenericReportMapper;
import com.codex.ecam.model.inventory.aodRetun.AODReturn;


public class AODRetrunReportMapper extends GenericReportMapper<AODReturn, AODReturnRepDTO> {

	private static AODRetrunReportMapper instance = null;

	private AODRetrunReportMapper() {
	}

	public static AODRetrunReportMapper getInstance() {
		if (instance == null) {
			instance = new AODRetrunReportMapper();
		}
		return instance;
	}

	@Override
	public AODReturnRepDTO domainToRepDTO(AODReturn domain) throws Exception {
		AODReturnRepDTO repDTO = new AODReturnRepDTO();
		repDTO.setAodReturnNo(domain.getReturnNo());
		repDTO.setAodReturnRefNo(domain.getReturnRefNo());

		repDTO.setAodReturnDate(domain.getReturnDate());
		repDTO.setAodReturnStatus(domain.getAodReturnStatus().getName());

		return repDTO;
	}



}
