package com.codex.ecam.result.asset;

import com.codex.ecam.dto.biz.warehouse.WareHouseDTO;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.result.BaseResult;

public class WareHouseResult extends BaseResult<Asset, WareHouseDTO> {

	public WareHouseResult(Asset domain, WareHouseDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
