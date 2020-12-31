package com.codex.ecam.model.inventory.inventoryGroup;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;

@Entity
@Table(name = "tbl_inventory_group")
public class InventoryGroup extends BaseModel {

    private static final long serialVersionUID = 3214131986620871250L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "inventory_group_s")
    @SequenceGenerator(name = "inventory_group_s", sequenceName = "inventory_group_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "business_id")
    @ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
    private Business business;
    
    @JoinColumn(name = "site_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset site;

    @Column(name = "inventory_group_no")
    private String inventoryGroupNo;

    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "inventoryGroup", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<InventoryGroupPart> inventoryGroupParts;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Asset getSite() {
		return site;
	}

	public void setSite(Asset site) {
		this.site = site;
	}

	public String getInventoryGroupNo() {
		return inventoryGroupNo;
	}

	public void setInventoryGroupNo(String inventoryGroupNo) {
		this.inventoryGroupNo = inventoryGroupNo;
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

	public Set<InventoryGroupPart> getInventoryGroupParts() {
		return inventoryGroupParts;
	}

	public void setInventoryGroupParts(Set<InventoryGroupPart> inventoryGroupParts) {
		this.inventoryGroupParts = inventoryGroupParts;
	}
    
    

}
