package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum WorkOrderStatus {

	OPEN(0, "Open"),
	APPROVED(2, "Approved"),
	//complete
	CLOSED(1, "Closed");

	private Integer id;
	private String name;

	private WorkOrderStatus(Integer id, String name){
		setId(id);
		setName(name);
	}

	public static List<WorkOrderStatus> getWorkOrderStatusList() {
		List<WorkOrderStatus> statusList = new ArrayList<>();
		statusList.add(OPEN);
		statusList.add(APPROVED);
		statusList.add(CLOSED);
		return statusList;
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
