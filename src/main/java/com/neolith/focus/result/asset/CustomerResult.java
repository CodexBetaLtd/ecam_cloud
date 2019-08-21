package com.neolith.focus.result.asset;

import com.neolith.focus.dto.asset.CustomerDTO;
import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.result.BaseResult;

public class CustomerResult extends BaseResult<Business, CustomerDTO> {
	
    public CustomerResult(Business domain, CustomerDTO dto) {
        super(domain, dto);
    }

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
