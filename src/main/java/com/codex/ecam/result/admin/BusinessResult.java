package com.codex.ecam.result.admin;

import com.codex.ecam.dto.biz.business.BusinessDTO;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.result.BaseResult;

public class BusinessResult extends BaseResult<Business, BusinessDTO> {

    public BusinessResult(Business domain, BusinessDTO dto) {
        super(domain, dto);
    }
    
    @Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
