package com.codex.ecam.result.admin;

import com.codex.ecam.dto.biz.business.BussinessTypeDTO;
import com.codex.ecam.model.biz.business.BusinessTypeDefinition;
import com.codex.ecam.result.BaseResult;

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