package com.neolith.focus.result.admin;

import com.neolith.focus.dto.asset.AssetCategoryDTO;
import com.neolith.focus.model.asset.AssetCategory;
import com.neolith.focus.result.BaseResult;

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
