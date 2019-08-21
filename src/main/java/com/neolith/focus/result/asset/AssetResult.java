package com.neolith.focus.result.asset;

import com.neolith.focus.dto.asset.AssetDTO;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.result.BaseResult;

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
