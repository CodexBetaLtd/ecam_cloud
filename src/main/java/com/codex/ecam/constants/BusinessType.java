package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum BusinessType {

    BUSINESS(0, "Business"),
    SUPPLIER(1, "Supplier");

    private Integer id;
    private String name;

    BusinessType(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static List<BusinessType> getBusinessTypes() {
        List<BusinessType> list = new ArrayList<BusinessType>();
        list.add(BusinessType.BUSINESS);
        list.add(BusinessType.SUPPLIER);
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
