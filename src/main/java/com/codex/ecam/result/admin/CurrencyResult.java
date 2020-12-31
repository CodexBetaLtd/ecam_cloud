package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.CurrencyDTO;
import com.codex.ecam.model.admin.Currency;
import com.codex.ecam.result.BaseResult;

public class CurrencyResult extends BaseResult<Currency, CurrencyDTO>{

	public CurrencyResult(Currency domain, CurrencyDTO dto) {
		super(domain, dto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());		
	}
}
