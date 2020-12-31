package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.CountryDTO;
import com.codex.ecam.model.admin.Country;
import com.codex.ecam.result.BaseResult;

public class CountryResult extends BaseResult<Country, CountryDTO>{

	public CountryResult(Country domain, CountryDTO dto) {
		super(domain, dto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());		
	}
}