package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum PurchaseOrderAdditionalCostType {
	
	VAT(0,"VAT"),
	TAX(1,"Tax"),
	SHIPPING(2,"Shipping"),
	MISCELLANEOUS(3,"Miscellaneous");
	
	private Integer id;
	private String name;
	
	private PurchaseOrderAdditionalCostType(Integer id,String name){
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
	
    public static List<PurchaseOrderAdditionalCostType> getAdditionalCostTypeList() {
    	
        List<PurchaseOrderAdditionalCostType> list = new ArrayList<PurchaseOrderAdditionalCostType>();
        list.add(PurchaseOrderAdditionalCostType.VAT);
        list.add(PurchaseOrderAdditionalCostType.TAX);
        list.add(PurchaseOrderAdditionalCostType.SHIPPING);
        list.add(PurchaseOrderAdditionalCostType.MISCELLANEOUS);
           
        return list;
    }
	
}
