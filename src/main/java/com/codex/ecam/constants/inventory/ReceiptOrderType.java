package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum ReceiptOrderType {
	
	NORMAL(0, "Normal"),
    REFURBISH(1, "Refurbish");
	

    private Integer id;
    private String name;
	
	private ReceiptOrderType(Integer id, String name){
		setId(id);
		setName(name);
	}
	
	
	public static List<ReceiptOrderType> getAllReceiptOrderType(){
		List<ReceiptOrderType> receiptOrderTypes=new ArrayList<>();
		receiptOrderTypes.add(ReceiptOrderType.NORMAL);
		receiptOrderTypes.add(ReceiptOrderType.REFURBISH);
		return receiptOrderTypes;
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
