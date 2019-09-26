package com.codex.ecam.util.search.biz;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

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
		
		
		default:
			break;

		}

	}

}
