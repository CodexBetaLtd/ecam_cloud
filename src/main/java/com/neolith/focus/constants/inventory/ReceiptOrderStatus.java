package com.neolith.focus.constants.inventory;

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
