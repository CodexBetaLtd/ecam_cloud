package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.AssetEventTypeDTO;
import com.neolith.focus.model.asset.AssetEventType;
import com.neolith.focus.result.BaseResult;

public class AssetEventTypeResult extends BaseResult<AssetEventType, AssetEventTypeDTO>{

	public AssetEventTypeResult(AssetEventType domain, AssetEventTypeDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}
}