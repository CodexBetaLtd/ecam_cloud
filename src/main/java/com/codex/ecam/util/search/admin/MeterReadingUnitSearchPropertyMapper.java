package com.codex.ecam.util.search.admin;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class MeterReadingUnitSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static MeterReadingUnitSearchPropertyMapper instance = null;

	private MeterReadingUnitSearchPropertyMapper() {
	}

	public static MeterReadingUnitSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new MeterReadingUnitSearchPropertyMapper();
		}
		return instance;
	}

	@Override
	protected void mapSearchParamsToPropertyParams(String tableColumn) {

		switch (tableColumn) {

		default:
			break;

		}
	}

}
