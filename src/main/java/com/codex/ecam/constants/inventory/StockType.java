package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum StockType {

	NORMAL(0, "Normal"),
	REFURBISH(1, "Refurbish"),
	DISPOSABLE(2, "Disposable"),
    OTHER(3, "Other");

    private Integer id;
    private String name;

    StockType(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static List<StockType> getPartTypes() {
        List<StockType> list = new ArrayList<StockType>();
        list.add(StockType.NORMAL); 
        list.add(StockType.REFURBISH); 
        list.add(StockType.DISPOSABLE); 
        list.add(StockType.OTHER);
        return list;
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
