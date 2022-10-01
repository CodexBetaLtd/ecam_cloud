package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum CostLogType {

    GENERAL(0, "General Cost Log");

    private Integer id;
    private String name;

    private CostLogType(Integer id, String name) {
        setId(id);
        setName(name);
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

    public static List<CostLogType> getCostLogTypeList() {
        List<CostLogType> list = new ArrayList<CostLogType>();
        list.add(CostLogType.GENERAL);
        return list;
    }

}
