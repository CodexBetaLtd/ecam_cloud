package com.codex.ecam.util.search.inventory.rfq;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class RFQPropertyMapper extends BaseSearchPropertyMapper {


    private static RFQPropertyMapper instance = null;

    private RFQPropertyMapper() {
    }

    public static RFQPropertyMapper getInstance() {
        if (instance == null) {
            instance = new RFQPropertyMapper();
        }
        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String column) {

        switch (column) {

            case "code":
                addColumns("code");
                break;
                
            case "statusName":
            	addColumns("rfqStatus.name");
            	break;

            default:
                break;

        }

    }
}
