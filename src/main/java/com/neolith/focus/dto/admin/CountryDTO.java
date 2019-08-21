package com.neolith.focus.dto.admin;

import com.neolith.focus.dto.BaseDTO;

public class CountryDTO extends BaseDTO {

	private Integer id;
	private String name;
	private String shortName;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
