package com.codex.ecam.model.admin;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
@Entity
@Table(name = "tbl_currency")
public class Currency extends BaseModel {
	
	private static final long serialVersionUID = 2795181843338832371L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "currency_s")
    @SequenceGenerator(name = "currency_s", sequenceName = "currency_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

	@Column(name="name")
	private String name;
	
	@Column(name="symbol")
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
