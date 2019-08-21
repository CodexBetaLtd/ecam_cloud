package com.neolith.focus.model.inventory.receiptOrder;

import java.math.BigDecimal;
import java.util.Date;
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

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.inventory.purchaseOrder.PurchaseOrderItem;
import com.neolith.focus.model.inventory.stock.Stock;

@Entity
@Table(name = "tbl_receipt_order_item")
public class ReceiptOrderItem extends BaseModel {

	private static final long serialVersionUID = 3163177500140917679L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "receipt_order_item_s")
	@SequenceGenerator(name = "receipt_order_item_s", sequenceName = "receipt_order_item_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@JoinColumn(name = "parent_receipt_order_item_id")
	@ManyToOne(targetEntity = ReceiptOrderItem.class, fetch = FetchType.LAZY)
	private ReceiptOrderItem parentReceiptOrderItem;

	@JoinColumn(name = "purchase_order_item_id")
	@ManyToOne(targetEntity = PurchaseOrderItem.class, fetch = FetchType.LAZY)
	private PurchaseOrderItem purchaseOrderItem;

	@JoinColumn(name = "receipt_order_id")
	@ManyToOne(targetEntity = ReceiptOrder.class, fetch = FetchType.LAZY)
	private ReceiptOrder receiptOrder;

	@JoinColumn(name = "stock_id")
	@ManyToOne(targetEntity = Stock.class, fetch = FetchType.LAZY)
	private Stock stock;

	//Warehouse
	@JoinColumn(name = "warehouse_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset warehouse;

	@Column(name = "unit_price")
	private BigDecimal unitPrice;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@Column(name = "quantity_ordered")
	private BigDecimal quantityOrdered;

	@Column(name = "quantity_received")
	private BigDecimal quantityReceived;

	@Column(name = "description")
	private String description;

	@Column(name = "imported_from_table")
	private String importedFromTable;

	@Column(name = "date_expiry_of_inventory_items")
	private Date dateExpiryOfInventoryItems;

	@OneToMany(mappedBy = "receiptOrderItem", fetch = FetchType.LAZY)
	private Set<Stock> stocks;

	@OneToMany(mappedBy = "receiptOrderItem", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<ReceiptOrderItemSerialNumber> receiptOrderItemSerialNumbers;

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

	public ReceiptOrderItem getParentReceiptOrderItem() {
		return parentReceiptOrderItem;
	}

	public void setParentReceiptOrderItem(ReceiptOrderItem parentReceiptOrderItem) {
		this.parentReceiptOrderItem = parentReceiptOrderItem;
	}

	public PurchaseOrderItem getPurchaseOrderItem() {
		return purchaseOrderItem;
	}

	public void setPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
		this.purchaseOrderItem = purchaseOrderItem;
	}

	public ReceiptOrder getReceiptOrder() {
		return receiptOrder;
	}

	public void setReceiptOrder(ReceiptOrder receiptOrder) {
		this.receiptOrder = receiptOrder;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Asset getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Asset warehouse) {
		this.warehouse = warehouse;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getQuantityOrdered() {
		return quantityOrdered;
	}

	public void setQuantityOrdered(BigDecimal quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

	public BigDecimal getQuantityReceived() {
		return quantityReceived;
	}

	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImportedFromTable() {
		return importedFromTable;
	}

	public void setImportedFromTable(String importedFromTable) {
		this.importedFromTable = importedFromTable;
	}

	public Date getDateExpiryOfInventoryItems() {
		return dateExpiryOfInventoryItems;
	}

	public void setDateExpiryOfInventoryItems(Date dateExpiryOfInventoryItems) {
		this.dateExpiryOfInventoryItems = dateExpiryOfInventoryItems;
	}

	public Set<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(Set<Stock> stocks) {
		updateCollection("stocks", stocks);
	}

	public Set<ReceiptOrderItemSerialNumber> getReceiptOrderItemSerialNumbers() {
		return receiptOrderItemSerialNumbers;
	}

	public void setReceiptOrderItemSerialNumbers(Set<ReceiptOrderItemSerialNumber> receiptOrderItemSerialNumbers) {
		updateCollection("receiptOrderItemSerialNumbers", receiptOrderItemSerialNumbers);
	}
}
