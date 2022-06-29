package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.cmmssetting.tax.TaxDTO;
import com.codex.ecam.model.admin.Tax;
import com.codex.ecam.result.BaseResult;

public class TaxResult extends BaseResult<Tax, TaxDTO>{

	public TaxResult(Tax domain, TaxDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
