package com.codex.ecam.constants;

public enum PurchaseOrderStatus {
	
	DRAFT(0, "Draft"),
	WAITING_FOR_APPROVAL(1, "Waiting For Approval"),
	RECEIVED_NOT_INVOICED(2, "Received Not Invoiced"),
	APPROVED(3, "Purchase order Approved"),
	REJECT(4, "Purchase order reject"),
    CANCEL(5, "Purchase order cancel");

    private Integer id;
    private String name;
	
	private PurchaseOrderStatus(Integer id, String name){
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
