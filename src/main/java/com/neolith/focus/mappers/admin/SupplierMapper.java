package com.neolith.focus.mappers.admin;

import com.neolith.focus.dto.biz.supplier.SupplierDTO;
import com.neolith.focus.exception.admin.SupplierException;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.biz.business.Business;


public class SupplierMapper extends GenericMapper<Business, SupplierDTO> {

	private static SupplierMapper instance = null;

	private SupplierMapper() {
	}

	public static SupplierMapper getInstance() {
		if (instance == null) {
			instance = new SupplierMapper();
		}
		return instance;
	}

	@Override
	public SupplierDTO domainToDto(Business domain) throws Exception {
		SupplierDTO dto = new SupplierDTO();
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

		if (domain.getCountry() != null) {
			dto.setCountryId(domain.getCountry().getId());
		}

		if (domain.getBusinessClassification() != null) {
			dto.setBusinessClassificationId(domain.getBusinessClassification().getId());
			dto.setBusinessClassificationName(domain.getBusinessClassification().getName());
		}
		if (domain.getCurrency() != null) {
			dto.setCurrencyId(domain.getCurrency().getId());
			dto.setCurrencyName(domain.getCurrency().getName());
			dto.setCurrencySymbol(domain.getCurrency().getSymbol());
		}
		if (domain.getVirtualBusiness() != null) {
			dto.setVirtualBusinessId(domain.getBusinessVirtual().getId());
			if (domain.getBusinessVirtual().getBusiness() != null) {
				dto.setVirtualBusinessOwnerId(domain.getBusinessVirtual().getBusiness().getId());
				dto.setVirtualBusinessOwnerName(domain.getBusinessVirtual().getBusiness().getName());
			}
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(SupplierDTO dto, Business domain) throws SupplierException {
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

		setCommanDomainFields(dto, domain);
	}

	@Override
	public SupplierDTO domainToDtoForDataTable(Business domain) throws SupplierException {
		SupplierDTO dto = new SupplierDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		
		if (domain.getBusinessVirtual() != null) {
			dto.setVirtualBusinessOwnerName(domain.getBusinessVirtual().getBusiness().getName());
		}
		return dto;
	}

}
