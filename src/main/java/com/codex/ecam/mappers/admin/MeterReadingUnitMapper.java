package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.admin.MeterReadingUnitDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.admin.MeterReadingUnit;

public class MeterReadingUnitMapper extends GenericMapper<MeterReadingUnit, MeterReadingUnitDTO> {

	private static MeterReadingUnitMapper instance = null;

	private MeterReadingUnitMapper() {
	}

	public static MeterReadingUnitMapper getInstance() {
		if (instance == null) {
			instance = new MeterReadingUnitMapper();
		}
		return instance;
	}

	@Override
	public MeterReadingUnitDTO domainToDto(MeterReadingUnit domain) throws Exception {

		MeterReadingUnitDTO dto = new MeterReadingUnitDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setSymbol(domain.getSymbol());
		dto.setPrecision(domain.getPrecision());

//		if (domain.getBusiness() != null) {
//			dto.setBusinessId(domain.getBusiness().getId());
//		}

		setCommanDTOFields(dto, domain);

		return dto;
	}


	@Override
	public void dtoToDomain(MeterReadingUnitDTO dto, MeterReadingUnit domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setSymbol(dto.getSymbol());
		domain.setPrecision(dto.getPrecision());

		setCommanDomainFields(dto, domain);

	}

	@Override
	public MeterReadingUnitDTO domainToDtoForDataTable(MeterReadingUnit domain) throws Exception {
		MeterReadingUnitDTO dto = new MeterReadingUnitDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setSymbol(domain.getSymbol());
		dto.setPrecision(domain.getPrecision());
		return dto;
	}

}
