package com.codex.ecam.result.inventory;

import com.codex.ecam.dto.biz.part.PartDTO;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.result.BaseResult;

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
