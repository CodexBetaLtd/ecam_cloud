package com.codex.ecam.util.search.inventory.purchaseorder;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class PurchaseorderPropertyMapper extends BaseSearchPropertyMapper {


    private static PurchaseorderPropertyMapper instance = null;

    private PurchaseorderPropertyMapper() {
    }

    public static PurchaseorderPropertyMapper getInstance() {
        if (instance == null) {
            instance = new PurchaseorderPropertyMapper();
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
            	addColumns("purchaseOrderStatus.name");
            	break;
            	
            case "supplierName":
            	addColumns("supplier.name");
            	break;

            default:
                break;

        }

    }
}
