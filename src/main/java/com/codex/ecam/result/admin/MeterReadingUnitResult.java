package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.MeterReadingUnitDTO;
import com.codex.ecam.model.admin.MeterReadingUnit;
import com.codex.ecam.result.BaseResult;

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