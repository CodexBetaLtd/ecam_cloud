package com.neolith.focus.result.admin;

import com.neolith.focus.dto.biz.business.BusinessDTO;
import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.result.BaseResult;

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
