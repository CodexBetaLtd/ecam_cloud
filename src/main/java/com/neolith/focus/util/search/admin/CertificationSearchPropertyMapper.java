package com.neolith.focus.util.search.admin;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class CertificationSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static CertificationSearchPropertyMapper instance = null;

	public static CertificationSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new CertificationSearchPropertyMapper();
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
