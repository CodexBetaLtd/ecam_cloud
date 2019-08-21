package com.neolith.focus.mappers.asset;

import com.neolith.focus.dto.asset.AssetCustomerDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.asset.AssetCustomer;

public class AssetCustomerMapper extends GenericMapper<AssetCustomer, AssetCustomerDTO> {
	
	private static AssetCustomerMapper instance = null;

    private AssetCustomerMapper() {
    }

    public static AssetCustomerMapper getInstance() {
        if (instance == null) {
            instance = new AssetCustomerMapper();
        }
        return instance;
    }

	@Override
	public AssetCustomerDTO domainToDto(AssetCustomer domain) throws Exception {
		AssetCustomerDTO dto = new AssetCustomerDTO();
		
		dto.setId(domain.getId());
		
		dto.setAssetId(domain.getAsset().getId());
		dto.setAssetName(domain.getAsset().getName());
		
		dto.setCustomerId(domain.getCustomer().getId());
		dto.setCustomerName(domain.getCustomer().getName());
		
		dto.setDate(domain.getCreatedDate());
        
        dto.setVersion(domain.getVersion());

        return dto;
	}

	@Override
	public AssetCustomerDTO domainToDtoForDataTable(AssetCustomer domain) throws Exception {
		AssetCustomerDTO dto = new AssetCustomerDTO();
		
		dto.setId(domain.getId());
		
		dto.setAssetId(domain.getAsset().getId());
		dto.setAssetName(domain.getAsset().getName());
		
		dto.setCustomerId(domain.getCustomer().getId());
		dto.setCustomerName(domain.getCustomer().getName());
		
		dto.setAddress(domain.getCustomer().getAddress() + ", " + domain.getCustomer().getCity());
		
		dto.setDate(domain.getCreatedDate());
        
        dto.setVersion(domain.getVersion());

        return dto;
	}

	@Override
	public void dtoToDomain(AssetCustomerDTO dto, AssetCustomer domain) throws Exception {
		domain.setId(dto.getId());
		domain.setVersion(dto.getVersion());
		domain.setIsDeleted(false);
	}

}
