package com.codex.ecam.model.inventory.purchaseOrder;

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
import com.codex.ecam.model.admin.TaxValue;

@Entity
@Table(name = "tbl_purchase_order_item_tax")
public class PurchaseOrderItemTax extends BaseModel {

	private static final long serialVersionUID = 6013158558768529621L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "purchase_order_item_tax_s")
	@SequenceGenerator(name = "purchase_order_item_tax_s", sequenceName = "purchase_order_item_tax_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "purchase_order_item_id")
	@ManyToOne(targetEntity = PurchaseOrderItem.class, fetch = FetchType.LAZY)
	private PurchaseOrderItem purchaseOrderItem;

	@JoinColumn(name = "tax_value_id")
	@ManyToOne(targetEntity = TaxValue.class, fetch = FetchType.LAZY)
	private TaxValue taxValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public PurchaseOrderItem getPurchaseOrderItem() {
		return purchaseOrderItem;
	}

	public void setPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
		this.purchaseOrderItem = purchaseOrderItem;
	}

	public TaxValue getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(TaxValue taxValue) {
		this.taxValue = taxValue;
	}



}
