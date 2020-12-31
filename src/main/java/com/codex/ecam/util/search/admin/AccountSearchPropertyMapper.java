package com.codex.ecam.util.search.admin;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class AccountSearchPropertyMapper extends BaseSearchPropertyMapper {

    private static AccountSearchPropertyMapper instance = null;

    private AccountSearchPropertyMapper() {
    }

    public static AccountSearchPropertyMapper getInstance() {
        if (instance == null) {
            instance = new AccountSearchPropertyMapper();
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
