package com.codex.ecam.util.search.biz;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class CustomerSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static CustomerSearchPropertyMapper instance = null;

	private CustomerSearchPropertyMapper() {
	}

	public static CustomerSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new CustomerSearchPropertyMapper();
		}
		return instance;
	}

	@Override
	protected void mapSearchParamsToPropertyParams(String tableColumn) {

		switch (tableColumn) {
		
		case "businessName" :
			addColumns("business.name");
			break;
			
		case "countryName" :
			addColumns("country.name");
			break;
			
		default:
			break;

		}

	}

}
