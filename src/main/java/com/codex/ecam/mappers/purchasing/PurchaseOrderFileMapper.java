package com.codex.ecam.mappers.purchasing;

import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderFileDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderFile;

public class PurchaseOrderFileMapper extends GenericMapper<PurchaseOrderFile, PurchaseOrderFileDTO>{

	private static PurchaseOrderFileMapper instance = null;

	private PurchaseOrderFileMapper() {
	}

	public static PurchaseOrderFileMapper getInstance() {
		if (instance == null) {
			instance = new PurchaseOrderFileMapper();
		}
		return instance;
	}

	@Override
	public PurchaseOrderFileDTO domainToDto(PurchaseOrderFile domain) throws Exception {
		PurchaseOrderFileDTO dto = new PurchaseOrderFileDTO();

		dto.setId(domain.getId());
		dto.setItemDescription(domain.getItemDescription());
		dto.setFileLocation(domain.getFileLocation());
		dto.setFileType(domain.getFileType());
		dto.setFileDate(domain.getFileDate());

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public PurchaseOrderFileDTO domainToDtoForDataTable(PurchaseOrderFile domain) throws Exception {
		PurchaseOrderFileDTO dto = new PurchaseOrderFileDTO();

		dto.setId(domain.getId());
		dto.setItemDescription(domain.getItemDescription());
		dto.setFileLocation(domain.getFileLocation());
		dto.setFileType(domain.getFileType());
		dto.setFileDate(domain.getFileDate());

		return dto;
	}

	@Override
	public void dtoToDomain(PurchaseOrderFileDTO dto, PurchaseOrderFile domain) throws Exception {
		domain.setId(dto.getId());
		domain.setItemDescription(dto.getItemDescription());
		domain.setFileLocation(dto.getFileLocation());
		domain.setFileType(dto.getFileType());
		domain.setFileDate(dto.getFileDate());

		setCommanDomainFields(dto, domain);
	}

}
