package com.neolith.focus.constants.util;

public enum AffixList {

    ESTIMATE(0, "estimate", "EP", "printEstimate", "newEstimate"),
    EXPENSE(1, "expense", "EXP", "printExpense", "newExpense"),
    AOD(2, "aod", "AOD", "printAOD", "newAOD"),
    AOD_RETURN(3, "aodreturn", "AODREN", "printAODReturn", "newAODReturn"),
    GRN(4, "grn", "GRN", "printGRN", "newGRN"),
    BUSINESS(5, "business", "BIZ", "printBusiness", "newBusiness"),
    SUPPLIER(6, "supplier", "SUP", "printSupplier", "newSupplier"),
    STOCK(7, "stock", "STK", "printStock", "newStock"),
    STOCK_ADJUSTMENT(8, "stock", "STK-ADJ", "printStockAdjustment", "newStockAdjustment"),
    OTHER(9, "Other", "Oth", "printOther", "newOther");

    private Integer id;
    private String name;
    private String code;
    private String printName;
    private String newItem;

    AffixList(Integer id, String name, String code, String printName, String newItem) {
        setId(id);
        setName(name);
        setCode(code);
        setPrintName(printName);
        setNewItem(newItem);
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getNewItem() {
        return newItem;
    }

    public void setNewItem(String newItem) {
        this.newItem = newItem;
    }
}
