package com.neolith.focus.mappers.admin;

import com.neolith.focus.dto.biz.business.BusinessClassificationDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.biz.business.BusinessClassification;

public class BusinessClassificationMapper extends GenericMapper<BusinessClassification, BusinessClassificationDTO>{

	private static  BusinessClassificationMapper instance = null;

	private  BusinessClassificationMapper() {
	}

	public static BusinessClassificationMapper getInstance() {
		if (instance == null) {
			instance = new BusinessClassificationMapper();
		}
		return instance;
	}

	@Override
	public BusinessClassificationDTO domainToDto(BusinessClassification domain) throws Exception {
		BusinessClassificationDTO dto = new BusinessClassificationDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		setCommanDTOFields(dto, domain);
		return dto;
	}

	@Override
	public void dtoToDomain(BusinessClassificationDTO dto, BusinessClassification domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public BusinessClassificationDTO domainToDtoForDataTable(BusinessClassification domain) throws Exception {
		BusinessClassificationDTO dto = new BusinessClassificationDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName()); 
		return dto;
	}

}
