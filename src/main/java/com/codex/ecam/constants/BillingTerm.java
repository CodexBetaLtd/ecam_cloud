package com.codex.ecam.constants;

import java.util.ArrayList;
import java.util.List;

public enum BillingTerm {

	MONTHLY(0, "Monthly"), 
	ANNUALY(1, "Annualy");

	private Integer id;
	private String name;

	private BillingTerm(Integer id, String name) {
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
	
    public static List<BillingTerm> getBillingTermList() {
        List<BillingTerm> list = new ArrayList<BillingTerm>();
        list.add(BillingTerm.MONTHLY);
        list.add(BillingTerm.ANNUALY);
        return list;
    }

}
