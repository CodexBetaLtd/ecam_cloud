package com.codex.ecam.mappers.report;

import com.codex.ecam.dto.report.data.PartRepDTO;
import com.codex.ecam.mappers.GenericReportMapper;
import com.codex.ecam.model.asset.Asset;

public class PartReportMapper extends GenericReportMapper<Asset, PartRepDTO> {

	private static PartReportMapper instance = null;

	private PartReportMapper() {

	}

	public static PartReportMapper getInstance() {
		if (instance == null) {
			instance = new PartReportMapper();
		}
		return instance;
	}

	@Override
	public PartRepDTO domainToRepDTO(Asset domain) throws Exception {
		final PartRepDTO repDTO = new PartRepDTO();
		if (domain != null) {
			repDTO.setPartReportItemCode(domain.getCode());
			repDTO.setPartReportItemDescription(domain.getDescription());;
			repDTO.setPartReportPartNo(domain.getName());
			repDTO.setPartType(domain.getPartType());
		}
		return repDTO;
	}

}
