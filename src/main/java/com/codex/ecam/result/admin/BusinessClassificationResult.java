package com.codex.ecam.result.admin;

import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;
import com.codex.ecam.model.biz.business.BusinessClassification;
import com.codex.ecam.result.BaseResult;

public class BusinessClassificationResult extends BaseResult<BusinessClassification, BusinessClassificationDTO> {

	public BusinessClassificationResult(BusinessClassification domain, BusinessClassificationDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
