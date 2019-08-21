package com.neolith.focus.model.inventory.rfq;

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.inventory.purchaseOrder.PurchaseOrderItemRFQItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tbl_rfq_item")
public class RFQItem extends BaseModel {

	private static final long serialVersionUID = 3167763864035252289L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "rfq_item_s")
	@SequenceGenerator(name = "rfq_item_s", sequenceName = "rfq_item_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@JoinColumn(name = "parent_rfq_item_id")
	@ManyToOne(targetEntity = RFQItem.class, fetch = FetchType.LAZY)
	private RFQItem parentRFQItem;

	@JoinColumn(name = "rfq_id")
	@ManyToOne(targetEntity = RFQ.class, fetch = FetchType.LAZY)
	private RFQ rfq;

	@Column(name = "business_asset_no")
	private String businessAssetNo;

	@Column(name = "description")
	private String description;

	@Column(name = "quoted_price_per_unit")
	private Double quotedPricePerUnit;

	@Column(name = "quoted_price_total")
	private Double quotedPriceTotal;

	@Column(name = "quoted")
	private Integer quoted;

	@Column(name = "requested")
	private Integer requested;
	
	@OneToMany(mappedBy = "rfqItem", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<PurchaseOrderItemRFQItem> purchaseOrderItems = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBusinessAssetNo() {
		return businessAssetNo;
	}

	public void setBusinessAssetNo(String businessAssetNo) {
		this.businessAssetNo = businessAssetNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getQuotedPricePerUnit() {
		return quotedPricePerUnit;
	}

	public void setQuotedPricePerUnit(Double quotedPricePerUnit) {
		this.quotedPricePerUnit = quotedPricePerUnit;
	}

	public Double getQuotedPriceTotal() {
		return quotedPriceTotal;
	}

	public void setQuotedPriceTotal(Double quotedPriceTotal) {
		this.quotedPriceTotal = quotedPriceTotal;
	}

	public Integer getQuoted() {
		return quoted;
	}

	public void setQuoted(Integer quoted) {
		this.quoted = quoted;
	}

	public Integer getRequested() {
		return requested;
	}

	public void setRequested(Integer requested) {
		this.requested = requested;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public RFQItem getParentRFQItem() {
		return parentRFQItem;
	}

	public void setParentRFQItem(RFQItem parentRFQItem) {
		this.parentRFQItem = parentRFQItem;
	}

	public RFQ getRfq() {
		return rfq;
	}

	public void setRfq(RFQ rfq) {
		this.rfq = rfq;
	}

	public List<PurchaseOrderItemRFQItem> getPurchaseOrderItems() {
		return purchaseOrderItems;
	}

	public void setPurchaseOrderItems(List<PurchaseOrderItemRFQItem> purchaseOrderItems) {
		this.purchaseOrderItems = purchaseOrderItems;
	}

}
