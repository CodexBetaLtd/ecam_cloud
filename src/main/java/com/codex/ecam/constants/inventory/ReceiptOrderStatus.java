package com.codex.ecam.constants.inventory;

import java.util.ArrayList;
import java.util.List;

public enum ReceiptOrderStatus {
	
	DRAFT(0, "Draft"),
    RECEIVED(1, "Received"),
    CANCEL(2, "Cancel"),
    APPROVED(3, "Approved");
	

    private Integer id;
    private String name;
	
	private ReceiptOrderStatus(Integer id, String name){
		setId(id);
		setName(name);
	}

	
	public static List<ReceiptOrderStatus> getAllStatus(){
		List<ReceiptOrderStatus> list=new ArrayList<>();
		list.add(ReceiptOrderStatus.DRAFT);
		list.add(ReceiptOrderStatus.RECEIVED);
		list.add(ReceiptOrderStatus.CANCEL);
		list.add(ReceiptOrderStatus.APPROVED);
		
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
