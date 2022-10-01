package com.codex.ecam.result.purchasing;

import com.codex.ecam.dto.inventory.rfq.RFQDTO;
import com.codex.ecam.model.inventory.rfq.RFQ;
import com.codex.ecam.result.BaseResult;

public class RFQResult extends BaseResult<RFQ, RFQDTO>{

	public RFQResult(RFQ domain, RFQDTO dto) {
		super(domain, dto);
	}
	
	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}


}
