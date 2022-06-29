package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum PartType {

	NORMAL(0, "Normal"),
	REPAIRABLE(1, "Repairable"),
	DISPOSABLE(2, "Disposable"),
    OTHER(3, "Other");

    private Integer id;
    private String name;

    PartType(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static List<PartType> getPartTypes() {
        List<PartType> list = new ArrayList<PartType>();
        list.add(PartType.NORMAL); 
        list.add(PartType.REPAIRABLE); 
        list.add(PartType.DISPOSABLE); 
        list.add(PartType.OTHER);
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
