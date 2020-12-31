package com.codex.ecam.mappers.report;

import com.codex.ecam.dto.report.data.aod.AODRepDTO;
import com.codex.ecam.mappers.GenericReportMapper;
import com.codex.ecam.model.inventory.aod.AOD;


public class AODReportMapper extends GenericReportMapper<AOD, AODRepDTO> {

	private static AODReportMapper instance = null;

	private AODReportMapper() {
	}

	public static AODReportMapper getInstance() {
		if (instance == null) {
			instance = new AODReportMapper();
		}
		return instance;
	}

	@Override
	public AODRepDTO domainToRepDTO(AOD domain) throws Exception {
		AODRepDTO repDTO = new AODRepDTO();
		repDTO.setAodNo(domain.getAodNo());
		if(domain.getCustomer()!=null){
			repDTO.setCustomerName(domain.getCustomer().getName());	
		}
		repDTO.setDate(domain.getDate());
		repDTO.setAodStatus(domain.getAodStatus().getName());

		repDTO.setAodType(domain.getAodType().getName());
		return repDTO;
	}



}
