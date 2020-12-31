package com.codex.ecam.util.search.inventory.receiptOrder;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class ReceiptOrderPropertyMapper extends BaseSearchPropertyMapper {


    private static ReceiptOrderPropertyMapper instance = null;

    private ReceiptOrderPropertyMapper() {
    }

    public static ReceiptOrderPropertyMapper getInstance() {
        if (instance == null) {
            instance = new ReceiptOrderPropertyMapper();
        }
        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String column) {

        switch (column) {

            case "code":
                addColumns("code");
                break;

            case "userDefinedCode":
                addColumns("userDefinedCode");
                break;

            case "supplierName":
                addColumns("supplier.name");
                break;

            case "statusName":
                addColumns("receiptOrderStatus.name");
                break;

            default:
                break;

        }

    }
}
