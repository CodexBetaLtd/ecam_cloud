package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.biz.business.BussinessTypeDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.biz.business.BusinessTypeDefinition;

public class BussinesTypeMapper extends GenericMapper<BusinessTypeDefinition, BussinessTypeDTO>{

	private static BussinesTypeMapper instance = null;

	private  BussinesTypeMapper() {
	}

	public static BussinesTypeMapper getInstance() {
		if (instance == null) {
			instance = new BussinesTypeMapper();
		}
		return instance;
	}

	@Override
	public BussinessTypeDTO domainToDto(BusinessTypeDefinition domain) throws Exception {
		BussinessTypeDTO dto = new BussinessTypeDTO();
		dto.setId(domain.getId());
		dto.setAllParent(domain.getAllParent());
		dto.setBusinessTypeDefinitionName(domain.getBusinessTypeDefinitionName());
		dto.setBusinessTypeDefinitionShort(domain.getBusinessTypeDefinitionShort());
		dto.setDefaultParentId(domain.getDefaultParentId());
		dto.setDefinable(domain.getDefinable());

		if ( domain.getBusiness() != null ) {
			dto.setBusinessId(domain.getBusiness().getId());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(BussinessTypeDTO dto, BusinessTypeDefinition domain) throws Exception {
		domain.setId(dto.getId());
		domain.setAllParent(dto.getAllParent());
		domain.setBusinessTypeDefinitionName(dto.getBusinessTypeDefinitionName());
		domain.setBusinessTypeDefinitionShort(dto.getBusinessTypeDefinitionShort());
		domain.setDefaultParentId(dto.getDefaultParentId());
		domain.setDefinable(dto.getDefinable());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public BussinessTypeDTO domainToDtoForDataTable(BusinessTypeDefinition domain) throws Exception {
		BussinessTypeDTO dto = new BussinessTypeDTO();

		dto.setId(domain.getId());
		dto.setAllParent(domain.getAllParent());
		dto.setBusinessTypeDefinitionName(domain.getBusinessTypeDefinitionName());
		dto.setBusinessTypeDefinitionShort(domain.getBusinessTypeDefinitionShort()); 
		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;
	}

}
