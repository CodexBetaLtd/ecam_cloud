package com.neolith.focus.mappers.biz;

import java.util.ArrayList;
import java.util.List;

import com.neolith.focus.dto.asset.AssetDTO;
import com.neolith.focus.dto.asset.CustomerDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.model.biz.business.BusinessContact;
import com.neolith.focus.util.CommonUtil;

public class CustomerMapper extends GenericMapper<Business, CustomerDTO> {

	private static CustomerMapper instance = null;

	private CustomerMapper() {
	}

	public static CustomerMapper getInstance() {
		if (instance == null) {
			instance = new CustomerMapper();
		}
		return instance;
	}

	@Override
	public CustomerDTO domainToDto(Business domain) throws Exception {
		CustomerDTO dto = new CustomerDTO();

		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setAddress(domain.getAddress());
		dto.setCity(domain.getCity());
		dto.setProvince(domain.getProvince());
		dto.setPostalCode(domain.getPostalcode());
		dto.setTelephone(domain.getPhone());
		dto.setFax(domain.getFax());

		if ( domain.getCountry() != null ) {
			dto.setCountryId(domain.getCountry().getId());
			dto.setCountryName(domain.getCountry().getName());
		}

		if (domain.getVirtualBusiness() != null) {
			dto.setBusinessId(domain.getBusinessVirtual().getId());
			if (domain.getBusinessVirtual().getBusiness() != null) {
				dto.setBusinessId(domain.getBusinessVirtual().getBusiness().getId());
				dto.setBusinessName(domain.getBusinessVirtual().getBusiness().getName());
			}
		}


		setCustomerContacts(domain, dto);
		setAssets(dto, domain);

		setCommanDTOFields(dto, domain);

		return dto;
	}

	private void setAssets(CustomerDTO dto, Business domain) {
		List<AssetDTO> assetCustomerDTOs = new ArrayList<>();

		AssetDTO assetDTO;
		for (Asset asset : domain.getAssets()) {

			assetDTO = new AssetDTO();
			assetDTO.setId(asset.getId());
			assetDTO.setName(asset.getName());
			assetDTO.setCode(asset.getCode());
			assetDTO.setAssetCategoryName(asset.getAssetCategory().getName());
			assetDTO.setVersion(asset.getVersion());
			setLocationString(assetDTO, asset);

			assetCustomerDTOs.add(assetDTO);
		}

		dto.setAssets(assetCustomerDTOs);
	}

	private void setLocationString(AssetDTO dto, Asset domain) {
		StringBuilder strBuilder = new StringBuilder("");

		CommonUtil.appendString(strBuilder, domain.getAddress(), ", ");
		CommonUtil.appendString(strBuilder, domain.getCity(), ", ");
		CommonUtil.appendString(strBuilder, domain.getProvince(), ", ");

		dto.setLocation(strBuilder.toString());
	}

	private void setCustomerContacts(Business domain, CustomerDTO dto) throws Exception {
		if (domain.getContacts().size() > 0) {
			for ( BusinessContact customerContact : domain.getContacts() ) {
				dto.getContacts().add(ContactMapper.getInstance().domainToDto(customerContact.getContact()));
			}
		}
	}

	@Override
	public CustomerDTO domainToDtoForDataTable(Business domain) throws Exception {
		CustomerDTO dto = new CustomerDTO();

		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setAddress(domain.getAddress());
		dto.setCity(domain.getCity());
		dto.setProvince(domain.getProvince());
		dto.setPostalCode(domain.getPostalcode());
		if(domain.getCountry() !=null){
			dto.setCountryName(domain.getCountry().getName());
		}
		dto.setTelephone(domain.getPhone());
		dto.setFax(domain.getFax());

		return dto;
	}

	@Override
	public void dtoToDomain(CustomerDTO dto, Business domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setCode(dto.getCode());
		domain.setAddress(dto.getAddress());
		domain.setCity(dto.getCity());
		domain.setProvince(dto.getProvince());
		domain.setPostalcode(dto.getPostalCode());
		domain.setRoleCustomer(true);
		//domain.setT(dto.getTelephone());
		domain.setFax(dto.getFax());

		setCommanDomainFields(dto, domain);
	}
}
