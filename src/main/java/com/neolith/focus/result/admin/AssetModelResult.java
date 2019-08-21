package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.AssetModelDTO;
import com.neolith.focus.model.admin.AssetModel;
import com.neolith.focus.result.BaseResult;

public class AssetModelResult extends BaseResult<AssetModel, AssetModelDTO>{

	public AssetModelResult(AssetModel domain, AssetModelDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setModelId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
