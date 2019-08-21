package com.codex.ecam.result.admin;

import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.model.asset.AssetCategory;
import com.codex.ecam.result.BaseResult;

public class AssetCategoryResult  extends BaseResult<AssetCategory, AssetCategoryDTO> {

	public AssetCategoryResult(AssetCategory domain, AssetCategoryDTO dto) {
        super(domain, dto);
    }
    
    @Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}
}
