package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.AssetEventTypeDTO;
import com.codex.ecam.model.asset.AssetEventType;
import com.codex.ecam.result.BaseResult;

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