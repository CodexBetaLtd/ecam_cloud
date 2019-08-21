package com.codex.ecam.constants;

public enum ResultStatus {

	SUCCESS(1, "Success"),
	ERROR(2, "Error"),
	FAILED(3, "Failed");

	private Long id;
	private String name;

	private ResultStatus(long id, String name){
		setId(id);
		setName(name);
	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}


}
