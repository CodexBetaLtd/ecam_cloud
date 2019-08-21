package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.CurrencyDTO;
import com.neolith.focus.model.admin.Currency;
import com.neolith.focus.result.BaseResult;

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
