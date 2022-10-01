package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum SMABCTriggerType {
	
	TYPE_1(1, "Type 1",10000.0,25000.0,100000.0),
    TYPE_2(2, "Type 2",10000.0,40000.0,100000.0);
// type 1 ->A->10000,35000,60000,85000 B->25000,50000,75000 C->100000 every 100000km occured
// type 2 ->A->10000,20000,30000,50000,60000,70000,90000,110000,130000,140000,150000,170000,180000,190000 B->40000,80000,120000,160000 C->100000,200000 every 200000km occured

    private Integer id;
    private String name;
    private Double aValue;
    private Double bValue;
    private Double cValue;

    SMABCTriggerType(Integer id, String name){
        setId(id);
        setName(name);
    }
    
    private SMABCTriggerType(Integer id, String name, Double aValue, Double bValue, Double cValue) {
        setId(id);
        setName(name);
        setaValue(aValue);
        setbValue(bValue);
        setcValue(cValue);
	}

	public static List<SMABCTriggerType> getSMABCTriggerTypeList() {
        List<SMABCTriggerType> list = new ArrayList<SMABCTriggerType>();
        list.add(SMABCTriggerType.TYPE_1);
        list.add(SMABCTriggerType.TYPE_2);
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

	public Double getaValue() {
		return aValue;
	}

	public void setaValue(Double aValue) {
		this.aValue = aValue;
	}

	public Double getbValue() {
		return bValue;
	}

	public void setbValue(Double bValue) {
		this.bValue = bValue;
	}

	public Double getcValue() {
		return cValue;
	}

	public void setcValue(Double cValue) {
		this.cValue = cValue;
	}

    
}
