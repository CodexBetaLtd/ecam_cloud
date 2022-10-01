package com.codex.ecam.model.asset;

import java.math.BigDecimal;

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

@Entity
@Table(name = "tbl_spare_part")
public class SparePart extends BaseModel {

	private static final long serialVersionUID = 3466843752790056309L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "spare_part_s")
	@SequenceGenerator(name = "spare_part_s", sequenceName = "spare_part_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@JoinColumn(name = "part_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset sparePart;
	
	@Column(name="quantity")
	private BigDecimal quantity;
	
	@Column(name="description")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Asset getSparePart() {
		return sparePart;
	}

	public void setSparePart(Asset sparePart) {
		this.sparePart = sparePart;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}




}
