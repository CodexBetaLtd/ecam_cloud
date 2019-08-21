package com.neolith.focus.util.search.admin;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class CurrencySearchPropertyMapper extends BaseSearchPropertyMapper {

	private static CurrencySearchPropertyMapper instance = null;

	private CurrencySearchPropertyMapper() {
	}

	public static CurrencySearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new CurrencySearchPropertyMapper();
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
