package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.AssetModelDTO;
import com.codex.ecam.model.admin.AssetModel;
import com.codex.ecam.result.BaseResult;

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
