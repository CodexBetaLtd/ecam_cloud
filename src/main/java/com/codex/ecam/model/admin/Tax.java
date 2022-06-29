package com.codex.ecam.model.admin;

import java.util.Set;

import javax.persistence.*;

import com.codex.ecam.constants.TAXType;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.biz.business.Business;

@Entity
@Table(name = "tbl_tax")
public class Tax extends BaseModel{

	private static final long serialVersionUID = 5525006230906983236L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tax_s")
	@SequenceGenerator(name = "tax_s" , sequenceName = "tax_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "priorty")
	private Integer priorty;
	
	@Column(name = "tax_type_id")
	private TAXType taxType;
	
	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@OneToMany(mappedBy = "tax", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<TaxValue> taxValues;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriorty() {
		return priorty;
	}

	public void setPriorty(Integer priorty) {
		this.priorty = priorty;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Set<TaxValue> getTaxValues() {
		return taxValues;
	}

	public void setTaxValues(Set<TaxValue> taxValues) {
		this.taxValues = taxValues;
	}

	public TAXType getTaxType() {
		return taxType;
	}

	public void setTaxType(TAXType taxType) {
		this.taxType = taxType;
	}
	
	
	
}