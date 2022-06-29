package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum MRNType {

    WORKORDER(0, "Work Order"),
    OTHER(1, "Other");

    private Integer id;
    private String name;

    MRNType(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static List<MRNType> getMRNTypes() {
        List<MRNType> list = new ArrayList<MRNType>();
        list.add(MRNType.WORKORDER); 
        list.add(MRNType.OTHER);
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
