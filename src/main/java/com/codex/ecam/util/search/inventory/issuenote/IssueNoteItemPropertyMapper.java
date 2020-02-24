package com.codex.ecam.util.search.inventory.issuenote;

import com.codex.ecam.util.search.BaseSearchPropertyMapper;

public class IssueNoteItemPropertyMapper extends BaseSearchPropertyMapper {


    private static IssueNoteItemPropertyMapper instance = null;

    private IssueNoteItemPropertyMapper() {
    }

    public static IssueNoteItemPropertyMapper getInstance() {
        if (instance == null) {
            instance = new IssueNoteItemPropertyMapper();
        }
        return instance;
    }

    @Override
    protected void mapSearchParamsToPropertyParams(String column) {

        switch (column) {

            case "stockBatchNo":
                addColumns("stock.stockNo");
                break;

            case "partName":
                addColumns("part.name");
                break;

            case "itemQuantity":
                addColumns("quantity");
                break;
            case "itemReturnQuantity":
                addColumns("returnQuantity");
                break;
            default:
                break;

        }

    }
}
