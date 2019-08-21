package com.neolith.focus.constants.inventory;

public enum RFQStatus {

	DRAFT(0, "Draft Quotetion"),
	SENT(1, "Sent RFQ"),
	RECEIVED(2, "Received Quotetion"),
	CONFIRM(3,"Confirm Quotaion"),
	CANCELLED(4, "Cancelled");
	

    private Integer id;
    private String name;
	
	private RFQStatus(Integer id, String name){
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
