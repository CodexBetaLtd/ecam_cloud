package com.neolith.focus.result.asset;

import com.neolith.focus.dto.biz.warehouse.WareHouseDTO;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.result.BaseResult;

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
