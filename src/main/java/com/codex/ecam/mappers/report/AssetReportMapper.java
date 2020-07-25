package com.codex.ecam.mappers.report;

import com.codex.ecam.dto.report.data.asset.AssetRepDTO;
import com.codex.ecam.mappers.GenericReportMapper;
import com.codex.ecam.model.asset.Asset;


public class AssetReportMapper extends GenericReportMapper<Asset, AssetRepDTO> {

	private static AssetReportMapper instance = null;

	private AssetReportMapper() {
	}

	public static AssetReportMapper getInstance() {
		if (instance == null) {
			instance = new AssetReportMapper();
		}
		return instance;
	}

	@Override
	public AssetRepDTO domainToRepDTO(Asset domain) throws Exception {
		AssetRepDTO dto = new AssetRepDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());
		dto.setIsOnline(domain.getIsOnline());
		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setBusinessName(domain.getBusiness().getName());
			dto.setBusinessAddress(domain.getBusiness().getAddress());
		}

		if (domain.getAssetCategory() != null) {
			dto.setAssetCategoryId(domain.getAssetCategory().getId());
			dto.setAssetCategoryType(domain.getAssetCategory().getAssetCategoryType());
			dto.setAssetCategoryName(domain.getAssetCategory().getName());
		}

		if (domain.getSite() != null) {
			dto.setSiteId(domain.getSite().getId());
		}

		if (domain.getParentAsset() != null){
			dto.setParentAssetId(domain.getParentAsset().getId());
			dto.setParentAssetName(domain.getParentAsset().getName());
		}

		if (domain.getModel() != null ){
			dto.setModel(domain.getModel().getId());
			dto.setModelName(domain.getModel().getModelName());
		}

		if (domain.getBrand() != null){
			dto.setBrand(domain.getBrand().getId());
			dto.setBrandName(domain.getBrand().getBrandName());
		}

		if ( domain.getCustomer() != null ) {
			dto.setCustomerId(domain.getCustomer().getId());
			dto.setCustomerName(domain.getCustomer().getName());
		}
		

		dto.setNotes(domain.getNotes());

		dto.setAddress(domain.getAddress());
		dto.setCity(domain.getCity());
		dto.setProvince(domain.getProvince());
		dto.setPostalCode(domain.getPostalcode());
		
		if (domain.getCountry() != null) {
			dto.setCountryId(domain.getCountry().getId());
		}
		if(domain.getLastPrice()!=null){
			dto.setAssetPrice(domain.getLastPrice().doubleValue());

		}else{
			dto.setAssetPrice(0.0);
		}

		dto.setSerialNo(domain.getSerialNo());

		return dto;
	}



}
