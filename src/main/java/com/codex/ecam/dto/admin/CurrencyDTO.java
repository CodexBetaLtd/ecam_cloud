package com.codex.ecam.dto.admin;

import com.codex.ecam.dto.BaseDTO;

public class CurrencyDTO extends BaseDTO {

	private Integer id;
	private String name;
	private String  symbol;
	
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
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
}
