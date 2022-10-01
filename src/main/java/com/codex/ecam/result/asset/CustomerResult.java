package com.codex.ecam.result.asset;

import com.codex.ecam.dto.asset.CustomerDTO;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.result.BaseResult;

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
