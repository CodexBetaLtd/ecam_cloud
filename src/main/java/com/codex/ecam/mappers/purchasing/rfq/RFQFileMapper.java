package com.codex.ecam.mappers.purchasing.rfq;

import com.codex.ecam.dto.inventory.rfq.RFQFileDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.rfq.RFQFile;

public class RFQFileMapper extends GenericMapper<RFQFile, RFQFileDTO>{

	private static RFQFileMapper instance = null;

	private RFQFileMapper() {
	}

	public static RFQFileMapper getInstance() {
		if (instance == null) {
			instance = new RFQFileMapper();
		}
		return instance;
	}

	@Override
	public RFQFileDTO domainToDto(RFQFile domain) throws Exception {
		RFQFileDTO dto = new RFQFileDTO();

		dto.setId(domain.getId());
		dto.setItemDescription(domain.getItemDescription());
		dto.setFileLocation(domain.getFileLocation());
		dto.setFileType(domain.getFileType());
		dto.setFileDate(domain.getFileDate());

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public RFQFileDTO domainToDtoForDataTable(RFQFile domain) throws Exception {
		RFQFileDTO dto = new RFQFileDTO();

		dto.setId(domain.getId());
		dto.setItemDescription(domain.getItemDescription());
		dto.setFileLocation(domain.getFileLocation());
		dto.setFileType(domain.getFileType());
		dto.setFileDate(domain.getFileDate());

		return dto;
	}

	@Override
	public void dtoToDomain(RFQFileDTO dto, RFQFile domain) throws Exception {
		domain.setId(dto.getId());
		domain.setItemDescription(dto.getItemDescription());
		domain.setFileLocation(dto.getFileLocation());
		domain.setFileType(dto.getFileType());
		domain.setFileDate(dto.getFileDate());

		setCommanDomainFields(dto, domain);
	}

}
