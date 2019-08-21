package com.neolith.focus.constants;

import java.util.ArrayList;
import java.util.List;

public enum MeterReadingLogicType {
	
	GREATER_THAN(1, "Greater Than"),
	LESS_THAN(2, "Less Than");

    private Integer id;
    private String name;

    MeterReadingLogicType(Integer id, String name){
        setId(id);
        setName(name);
    }

    public static List<MeterReadingLogicType> getMeterReadingLogics() {
        List<MeterReadingLogicType> list = new ArrayList<MeterReadingLogicType>();

        list.add(GREATER_THAN);
        list.add(LESS_THAN);

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
