package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum StockAgeType {

    UP_TO_THREE_MONTHS(0, "Up to 3 Months"),
    THREE_TO_SIX_MONTHS(1, "3 Months - 6 Months"),
    SIX_TO_TWELVE_MONTHS(2, "6 Months - 12 Months"),
    ABOVE_TWELVE_MONTHS(3, "Above 12 Months");
	
    private Integer id;
    private String name;

    private StockAgeType(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static List<StockAgeType> getStockAgeTypes() {
        List<StockAgeType> ageTypes = new ArrayList<>();
        ageTypes.add(UP_TO_THREE_MONTHS);
        ageTypes.add(THREE_TO_SIX_MONTHS);
        ageTypes.add(SIX_TO_TWELVE_MONTHS);
        ageTypes.add(ABOVE_TWELVE_MONTHS);
        return ageTypes;
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
}
