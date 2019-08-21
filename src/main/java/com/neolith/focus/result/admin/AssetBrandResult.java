package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.AssetBrandDTO;
import com.neolith.focus.model.admin.AssetBrand;
import com.neolith.focus.result.BaseResult;

public class AssetBrandResult extends BaseResult<AssetBrand, AssetBrandDTO>{

	public AssetBrandResult(AssetBrand domain, AssetBrandDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setBrandId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
