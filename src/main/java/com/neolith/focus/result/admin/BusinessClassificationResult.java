package com.neolith.focus.result.admin;

import com.neolith.focus.dto.biz.business.BusinessClassificationDTO;
import com.neolith.focus.model.biz.business.BusinessClassification;
import com.neolith.focus.result.BaseResult;

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
