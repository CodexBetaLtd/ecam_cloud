package com.codex.ecam.util.search.biz;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class BusinessTypeSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static BusinessTypeSearchPropertyMapper instance = null;

	private BusinessTypeSearchPropertyMapper() {
	}

	public static BusinessTypeSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new BusinessTypeSearchPropertyMapper();
		}
		return instance;
	}

	@Override
	protected void mapSearchParamsToPropertyParams(String tableColumn) {

		switch (tableColumn) {
			
			case "businessName" :
				addColumns("business.name");
				break;
			
			default:
				break;

		}
	}

}
