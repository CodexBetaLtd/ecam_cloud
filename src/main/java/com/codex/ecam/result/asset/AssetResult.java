package com.codex.ecam.result.asset;

import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.result.BaseResult;

public class AssetResult extends BaseResult<Asset, AssetDTO> {

    public AssetResult(Asset domain, AssetDTO dto) {
        super(domain, dto);
    }

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
