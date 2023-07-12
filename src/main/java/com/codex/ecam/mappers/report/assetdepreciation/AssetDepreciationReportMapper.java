package com.codex.ecam.mappers.report.assetdepreciation;

import com.codex.ecam.dto.report.data.assetdepreciation.AssetDepreciationRepDTO;
import com.codex.ecam.mappers.GenericReportMapper;
import com.codex.ecam.model.asset.Asset;


public class AssetDepreciationReportMapper extends GenericReportMapper<Asset, AssetDepreciationRepDTO> {

	private static AssetDepreciationReportMapper instance = null;

	private AssetDepreciationReportMapper() {
	}

	public static AssetDepreciationReportMapper getInstance() {
		if (instance == null) {
			instance = new AssetDepreciationReportMapper();
		}
		return instance;
	}

	@Override
	public AssetDepreciationRepDTO domainToRepDTO(Asset domain) throws Exception {

		AssetDepreciationRepDTO repDTO = new AssetDepreciationRepDTO();

		repDTO.setName(domain.getName());
		repDTO.setCode(domain.getCode());
		repDTO.setDescription(domain.getDescription());

		repDTO.setSerialNo(domain.getSerialNo());
		repDTO.setSize(domain.getSize());
		repDTO.setQuantity(domain.getQuantity());
		repDTO.setUnitCost(domain.getUnitCost());
		repDTO.setTotalCost(domain.getTotalCost());
		repDTO.setUsefulLife(domain.getUsefulLife());
		repDTO.setDateOfPurchase(domain.getDateOfPurchase());
		repDTO.setDepartment(domain.getDepartment());
		repDTO.setRemarks(domain.getRemarks());

		setAssetCategory(domain, repDTO);
		setParentAsset(domain, repDTO);
		setModel(domain, repDTO);
		setBrand(domain, repDTO);
		setSite(domain, repDTO);
		setSubSite(domain, repDTO);
		setParentAsset(domain, repDTO);
		return repDTO;
	}
	private void setAssetCategory(Asset domain, final AssetDepreciationRepDTO dto) {
		if (domain.getAssetCategory() != null) {
			dto.setAssetCategoryName(domain.getAssetCategory().getName());
		}
	}

	private void setSite(Asset domain, final AssetDepreciationRepDTO dto) {
		if (domain.getSite() != null) {
			dto.setSubLocation(domain.getSite().getName());
		}
	}

	private void setSubSite(Asset domain, final AssetDepreciationRepDTO dto) {
		if (domain.getSubSite() != null) {
			dto.setSubLocation2(domain.getSubSite().getName());
		}
	}

	private void setParentAsset(Asset domain, final AssetDepreciationRepDTO dto) {
		if (domain.getParentAsset() != null){
			dto.setMainLocationName(domain.getParentAsset().getName());
		}
	}

	private void setModel(Asset domain, final AssetDepreciationRepDTO dto) {
		if (domain.getModel() != null ){
			dto.setModelName(domain.getModel().getModelName());
		}
	}

	private void setBrand(Asset domain, final AssetDepreciationRepDTO dto) {
		if (domain.getBrand() != null){
			dto.setBrandName(domain.getBrand().getBrandName());
		}
	}


}
