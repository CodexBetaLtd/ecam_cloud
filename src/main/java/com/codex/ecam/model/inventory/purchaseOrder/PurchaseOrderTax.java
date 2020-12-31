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
@Table(name = "tbl_purchase_order_tax")
public class PurchaseOrderTax extends BaseModel {

	private static final long serialVersionUID = 6013158558768529621L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "purchase_order_tax_s")
	@SequenceGenerator(name = "purchase_order_tax_s", sequenceName = "purchase_order_tax_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "purchase_order_id")
	@ManyToOne(targetEntity = PurchaseOrder.class, fetch = FetchType.LAZY)
	private PurchaseOrder purchaseOrder;

	@JoinColumn(name = "tax_value_id")
	@ManyToOne(targetEntity = TaxValue.class, fetch = FetchType.LAZY)
	private TaxValue taxValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}




	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public TaxValue getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(TaxValue taxValue) {
		this.taxValue = taxValue;
	}



}
