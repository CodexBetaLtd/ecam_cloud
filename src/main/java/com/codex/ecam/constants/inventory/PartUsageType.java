package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum PartUsageType {

	NORMAL_PART(0, "Normal Part"),
	SPARE_PART(1, "Spare Part");

    private Integer id;
    private String name;

    PartUsageType(Integer id, String name) {
        setId(id);
        setName(name);
    }

    public static List<PartUsageType> getPartUsageTypes() {
        List<PartUsageType> list = new ArrayList<PartUsageType>();
        list.add(PartUsageType.NORMAL_PART); 
        list.add(PartUsageType.SPARE_PART); 
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
