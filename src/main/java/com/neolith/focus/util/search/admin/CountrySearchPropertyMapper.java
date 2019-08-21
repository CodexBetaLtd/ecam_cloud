package com.neolith.focus.util.search.admin;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class CountrySearchPropertyMapper extends BaseSearchPropertyMapper {

	private static CountrySearchPropertyMapper instance = null;

	private CountrySearchPropertyMapper() {
	}

	public static CountrySearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new CountrySearchPropertyMapper();
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
