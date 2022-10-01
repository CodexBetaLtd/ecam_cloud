package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.biz.business.BusinessClassification;

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
