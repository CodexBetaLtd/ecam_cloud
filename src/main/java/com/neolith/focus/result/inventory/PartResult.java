package com.neolith.focus.result.inventory;

import com.neolith.focus.dto.biz.part.PartDTO;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.result.BaseResult;

public class PartResult extends BaseResult<Asset, PartDTO> {

	public PartResult(Asset domain, PartDTO dto) {
		super(domain, dto);
	}
	
	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
