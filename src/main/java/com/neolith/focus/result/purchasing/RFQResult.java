package com.neolith.focus.result.purchasing;

import com.neolith.focus.dto.inventory.rfq.RFQDTO;
import com.neolith.focus.model.inventory.rfq.RFQ;
import com.neolith.focus.result.BaseResult;

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
