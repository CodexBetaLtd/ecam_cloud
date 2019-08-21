package com.neolith.focus.mappers.admin;

import com.neolith.focus.dto.biz.business.BusinessGroupDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.biz.business.BusinessGroup;

public class BusinessGroupMapper extends GenericMapper<BusinessGroup, BusinessGroupDTO> {
	
	private static  BusinessGroupMapper instance = null;
	
	private  BusinessGroupMapper() {
	}
	
	public static BusinessGroupMapper getInstance() {
		if (instance == null) {
			instance = new BusinessGroupMapper();
		}
		return instance;
	}

	@Override
	public BusinessGroupDTO domainToDto(BusinessGroup domain) throws Exception {
		BusinessGroupDTO dto = new BusinessGroupDTO();
		
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setIsDefaultManufacturer(domain.getIsDefaultManufacturer());
		dto.setIsDefaultSupplier(domain.getIsDefaultManufacturer());
		dto.setRelationshipType(domain.getRelationshipType());		
		dto.setVersion(domain.getVersion());
		
		return dto;
	}

	@Override
	public void dtoToDomain(BusinessGroupDTO dto, BusinessGroup domain) throws Exception {
		
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setIsDefaultManufacturer(dto.getIsDefaultManufacturer());
		domain.setIsDefaultSupplier(dto.getIsDefaultManufacturer());
		domain.setRelationshipType(dto.getRelationshipType());		
		domain.setVersion(dto.getVersion());
		
	}

    @Override
    public BusinessGroupDTO domainToDtoForDataTable(BusinessGroup domain) throws Exception {
		BusinessGroupDTO dto = new BusinessGroupDTO();
		
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setIsDefaultManufacturer(domain.getIsDefaultManufacturer());
		dto.setIsDefaultSupplier(domain.getIsDefaultManufacturer());
		dto.setRelationshipType(domain.getRelationshipType());	
		return dto;

    }

}
