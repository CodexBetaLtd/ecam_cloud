package com.neolith.focus.util.search.biz;

import com.neolith.focus.util.search.BaseSearchPropertyMapper;

public class SupplierSearchPropertyMapper extends BaseSearchPropertyMapper {

	private static SupplierSearchPropertyMapper instance = null;

	private SupplierSearchPropertyMapper() {
	}

	public static SupplierSearchPropertyMapper getInstance() {
		if (instance == null) {
			instance = new SupplierSearchPropertyMapper();
		}
		return instance;
	}

	@Override
	protected void mapSearchParamsToPropertyParams(String tableColumn) {

		switch (tableColumn) {
		
		case "virtualBusinessOwnerName" :
			addColumns("businessVirtual.business.name");
			break;
			
		default:
			break;

		}

	}

}
