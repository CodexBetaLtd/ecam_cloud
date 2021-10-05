package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.biz.business.BusinessDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.biz.business.Business;


public class BusinessMapper extends GenericMapper<Business, BusinessDTO> {

	private static BusinessMapper instance = null;

	private BusinessMapper() {
	}

	public static BusinessMapper getInstance() {
		if (instance == null) {
			instance = new BusinessMapper();
		}
		return instance;
	}

	@Override
	public BusinessDTO domainToDto(Business domain) throws Exception {
		final BusinessDTO dto = new BusinessDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setPhone(domain.getPhone2());
		dto.setPhone2(domain.getPhone2());
		dto.setFax(domain.getFax());
		dto.setWebSite(domain.getWebSite());
		dto.setPrimaryEmail(domain.getPrimaryEmail());
		dto.setSecondaryEmail(domain.getSecondaryEmail());
		dto.setNotes(domain.getNotes());
		dto.setAddress(domain.getAddress());
		dto.setCity(domain.getCity());
		dto.setProvince(domain.getProvince());
		dto.setPostalCode(domain.getPostalcode());

		dto.setIsFIFO(domain.getIsFIFO());

		if (domain.getCountry() != null) {
			dto.setCountryId(domain.getCountry().getId());
			dto.setCountryName(domain.getCountry().getName());
		}

		if (domain.getBusinessClassification() != null) {
			dto.setBusinessClassificationId(domain.getBusinessClassification().getId());
			dto.setBusinessClassificationName(domain.getBusinessClassification().getName());
		}
		if (domain.getCurrency() != null) {
			dto.setCurrencyId(domain.getCurrency().getId());
			dto.setCurrencyName(domain.getCurrency().getName());
		}

		if (domain.getRoleCustomer() != null) {
			dto.setRoleCustomer(domain.getRoleCustomer());
		}

		if (domain.getRoleSupplier() != null) {
			dto.setRoleSupplier(domain.getRoleSupplier());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(BusinessDTO dto, Business domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setCode(dto.getCode());
		domain.setPhone(dto.getPhone2());
		domain.setPhone2(dto.getPhone2());
		domain.setFax(dto.getFax());
		domain.setWebSite(dto.getWebSite());
		domain.setPrimaryEmail(dto.getPrimaryEmail());
		domain.setSecondaryEmail(dto.getSecondaryEmail());
		domain.setNotes(dto.getNotes());
		domain.setAddress(dto.getAddress());
		domain.setCity(dto.getCity());
		domain.setProvince(dto.getProvince());
		domain.setPostalcode(dto.getPostalCode());

		domain.setIsFIFO(dto.getIsFIFO());

		setCommanDomainFields(dto, domain);

	}

	@Override
	public BusinessDTO domainToDtoForDataTable(Business domain) throws Exception {
		final BusinessDTO dto = new BusinessDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());

		return dto;
	}

}
