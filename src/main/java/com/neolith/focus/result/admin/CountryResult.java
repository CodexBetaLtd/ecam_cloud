package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.CountryDTO;
import com.neolith.focus.model.admin.Country;
import com.neolith.focus.result.BaseResult;

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