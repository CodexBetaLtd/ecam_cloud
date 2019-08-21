package com.codex.ecam.model.inventory.inventoryGroup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;

@Entity
@Table(name = "tbl_inventory_group_part")
public class InventoryGroupPart extends BaseModel {

	private static final long serialVersionUID = -8110969685723306596L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "inventory_group_part_s")
	@SequenceGenerator(name = "inventory_group_part_s", sequenceName = "inventory_group_part_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "part_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset part;

	@JoinColumn(name = "inventory_group_id")
	@ManyToOne(targetEntity = InventoryGroup.class, fetch = FetchType.LAZY)
	private InventoryGroup inventoryGroup;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Asset getPart() {
		return part;
	}

	public void setPart(Asset part) {
		this.part = part;
	}

	public InventoryGroup getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(InventoryGroup inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	
}
