package com.codex.ecam.mappers.biz;

import com.codex.ecam.dto.biz.supplier.SupplierDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.biz.supplier.Supplier;


public class SupplierMapper extends GenericMapper<Supplier, SupplierDTO> {

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
	public SupplierDTO domainToDto(Supplier domain) throws Exception {
		final SupplierDTO dto = new SupplierDTO();
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
		dto.setIsServiceProvider(domain.getIsServiceProvider());

		setSupplierCountry(domain, dto);
		setSupplierCurrency(domain, dto);
		setSupplierBusinessClassification(domain, dto);

		setCommanDTOFields(dto, domain);

		return dto;
	}

	private void setSupplierCountry(Supplier domain, SupplierDTO dto) {
		if (domain.getCountry() != null) {
			dto.setCountryId(domain.getCountry().getId());
			dto.setCountryName(domain.getCountry().getName());
		}
	}

	private void setSupplierCurrency(Supplier domain, SupplierDTO dto) {
		if (domain.getCurrency() != null) {
			dto.setCurrencyId(domain.getCurrency().getId());
			dto.setCurrencyName(domain.getCurrency().getName());
		}
	}

	private void setSupplierBusinessClassification(Supplier domain, SupplierDTO dto) {
		if (domain.getBusinessClassification() != null) {
			dto.setBusinessClassificationId(domain.getBusinessClassification().getId());
			dto.setBusinessClassificationName(domain.getBusinessClassification().getName());
		}
	}

	@Override
	public void dtoToDomain(SupplierDTO dto, Supplier domain) throws Exception {
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
		domain.setIsServiceProvider(dto.getIsServiceProvider());


		setCommanDomainFields(dto, domain);

	}

	@Override
	public SupplierDTO domainToDtoForDataTable(Supplier domain) throws Exception {
		final SupplierDTO dto = new SupplierDTO();
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

		setSupplierCountry(domain, dto);
		return dto;
	}

}
