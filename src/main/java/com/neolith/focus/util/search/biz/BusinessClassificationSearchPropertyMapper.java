package com.neolith.focus.util.search.biz;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class BusinessClassificationSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static BusinessClassificationSearchPropertyMapper instance = null;

	private BusinessClassificationSearchPropertyMapper() {
	}

	public static BusinessClassificationSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new BusinessClassificationSearchPropertyMapper();
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
