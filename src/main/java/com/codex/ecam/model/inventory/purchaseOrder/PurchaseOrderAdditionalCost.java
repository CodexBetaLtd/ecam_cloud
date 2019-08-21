package com.codex.ecam.model.inventory.purchaseOrder;

import javax.persistence.*;

import com.codex.ecam.constants.PurchaseOrderAdditionalCostType;
import com.codex.ecam.constants.ShippingType;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.biz.business.Business;

@Entity
@Table(name = "tbl_purchase_order_additional_cost")
public class PurchaseOrderAdditionalCost extends BaseModel {

	private static final long serialVersionUID = 4966344120131650083L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "purchase_order_additional_cost_s")
	@SequenceGenerator(name = "purchase_order_additional_cost_s", sequenceName = "purchase_order_additional_cost_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn(name = "purchase_order_id")
	@ManyToOne(targetEntity = PurchaseOrder.class, fetch = FetchType.LAZY)
	private PurchaseOrder purchaseOrder;

	@Column(name = "po_additional_cost_type_id")
	private PurchaseOrderAdditionalCostType purchaseOrderAdditionalCostType;

	@Column(name = "shipping_type_id")
	private ShippingType shippingType;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private Double price;

	@Column(name = "tax_rate")
	private Double taxRate;

	@Column(name = "is_override_po_item_tax")
	private Boolean isOverridePoItemTax;

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

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public Boolean getIsOverridePoItemTax() {
		return isOverridePoItemTax;
	}

	public void setIsOverridePoItemTax(Boolean isOverridePoItemTax) {
		this.isOverridePoItemTax = isOverridePoItemTax;
	}

	public PurchaseOrderAdditionalCostType getPurchaseOrderAdditionalCostType() {
		return purchaseOrderAdditionalCostType;
	}

	public void setPurchaseOrderAdditionalCostType(PurchaseOrderAdditionalCostType purchaseOrderAdditionalCostType) {
		this.purchaseOrderAdditionalCostType = purchaseOrderAdditionalCostType;
	}

	public ShippingType getShippingType() {
		return shippingType;
	}

	public void setShippingType(ShippingType shippingType) {
		this.shippingType = shippingType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
