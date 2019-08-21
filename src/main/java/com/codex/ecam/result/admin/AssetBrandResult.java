package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.AssetBrandDTO;
import com.codex.ecam.model.admin.AssetBrand;
import com.codex.ecam.result.BaseResult;

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
