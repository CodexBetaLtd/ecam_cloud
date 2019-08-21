package com.neolith.focus.result.admin;

import com.neolith.focus.dto.biz.business.BussinessTypeDTO;
import com.neolith.focus.model.biz.business.BusinessTypeDefinition;
import com.neolith.focus.result.BaseResult;

public class BusinessTypeResult extends BaseResult<BusinessTypeDefinition, BussinessTypeDTO> {

	public BusinessTypeResult(BusinessTypeDefinition domain, BussinessTypeDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}
}