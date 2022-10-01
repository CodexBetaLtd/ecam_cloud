package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum ShippingType {

    AIR(0, "Air"),
    GROUND(1, "Ground"),
    COURIER(2, "Courier"),
    PICK_UP(3, "Pick up"),
    OTHERS(4, "Other");
	
    private Integer id;
    private String name;
    
    private ShippingType(Integer id,String name){
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
    
    public static List<ShippingType> getShippingTypeList() {
        List<ShippingType> list = new ArrayList<ShippingType>();
        list.add(ShippingType.AIR);
        list.add(ShippingType.GROUND);
        list.add(ShippingType.COURIER);
        list.add(ShippingType.PICK_UP);
        list.add(ShippingType.OTHERS);
        
        return list;
    }
    
}
