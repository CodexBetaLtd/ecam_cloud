package com.codex.ecam.model.biz.business;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;

@Entity
@Table(name="tbl_business_group")
public class BusinessGroup extends BaseModel{
	
	private static final long serialVersionUID = -1237065243618182099L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="business_group_s")
	@SequenceGenerator(name="business_group_s", sequenceName="business_group_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@Column(name="relationship_type")
	private Integer relationshipType;
	
	@Column(name="is_default_manufacturer")
	private Boolean isDefaultManufacturer;
	
	@Column(name="is_default_supplier")
	private Boolean isDefaultSupplier;

	@Column(name="name")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(Integer relationshipType) {
		this.relationshipType = relationshipType;
	}

	public Boolean getIsDefaultManufacturer() {
		return isDefaultManufacturer;
	}

	public void setIsDefaultManufacturer(Boolean isDefaultManufacturer) {
		this.isDefaultManufacturer = isDefaultManufacturer;
	}

	public Boolean getIsDefaultSupplier() {
		return isDefaultSupplier;
	}

	public void setIsDefaultSupplier(Boolean isDefaultSupplier) {
		this.isDefaultSupplier = isDefaultSupplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
