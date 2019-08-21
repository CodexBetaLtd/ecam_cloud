package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.MeterReadingUnitDTO;
import com.neolith.focus.model.admin.MeterReadingUnit;
import com.neolith.focus.result.BaseResult;

public class MeterReadingUnitResult extends BaseResult<MeterReadingUnit, MeterReadingUnitDTO>{

	public MeterReadingUnitResult(MeterReadingUnit domain, MeterReadingUnitDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}
}