package com.codex.ecam.model.inventory.stock;

import javax.persistence.*;

import com.codex.ecam.constants.inventory.StockType;
import com.codex.ecam.listeners.inventory.part.PartStockLogListener;
import com.codex.ecam.listeners.inventory.stock.StockNotificationPublishListener;
import com.codex.ecam.listeners.stock.StockLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrderItem;
import com.codex.ecam.model.inventory.stockAdjustment.StockAdjustment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_stock")
@EntityListeners({StockLogListener.class, PartStockLogListener.class, StockNotificationPublishListener.class})
public class Stock extends BaseModel {

	private static final long serialVersionUID = -5946492823618192116L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "stock_s")
	@SequenceGenerator(name = "stock_s", sequenceName = "stock_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn(name = "site_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset site;

	@JoinColumn(name = "warehouse_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset warehouse;

	@JoinColumn(name = "part_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset part;

	@JoinColumn(name = "receipt_order_item_id")
	@ManyToOne(targetEntity = ReceiptOrderItem.class, fetch = FetchType.LAZY)
	private ReceiptOrderItem receiptOrderItem;

	@Column(name = "stock_no")
	private String stockNo;

	@Column(name = "unit_price")
	private BigDecimal unitPrice;

	@Column(name = "avg_price")
	private BigDecimal avgPrice;

	@Column(name = "min_quantity")
	private BigDecimal minQuantity;

	@Column(name = "current_quantity")
	private BigDecimal currentQuantity;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "batch_no")
	private String batchNo;
	
	@Column(name = "selling_price")
	private BigDecimal sellingPrice;
	
	@Column(name = "buying_price")
	private BigDecimal buyingPrice;
	
	@Column(name = "stock_date")
	private Date date;
	
	@Column(name = "stock_type_id")
	private StockType stockType;
	
	@Column(name = "cost_center")
	private String costCenter;
	
	@Column(name = "account")
	private String account;

	@OneToMany(mappedBy = "stock", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<StockHistory> stockHistoryList;

	@OneToMany(mappedBy = "stock", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<StockAdjustment> stockAdjustmentList;	

	@OneToMany(mappedBy = "stock", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<StockNotification> stockNotifications;

	@Transient
	private BigDecimal lastQuantity;

	@Transient
	private BigDecimal newQuantity;

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

	public Asset getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Asset warehouse) {
		this.warehouse = warehouse;
	}

	public Asset getPart() {
		return part;
	}

	public void setPart(Asset part) {
		this.part = part;
	}

	public ReceiptOrderItem getReceiptOrderItem() {
		return receiptOrderItem;
	}

	public void setReceiptOrderItem(ReceiptOrderItem receiptOrderItem) {
		this.receiptOrderItem = receiptOrderItem;
	}

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}

	public BigDecimal getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(BigDecimal minQuantity) {
		this.minQuantity = minQuantity;
	}

	public BigDecimal getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(BigDecimal currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	public Set<StockHistory> getStockHistoryList() {
		return stockHistoryList;
	}

	public void setStockHistoryList(Set<StockHistory> stockHistoryList) {
		updateCollection("stockHistoryList", stockHistoryList);
	}

	public Set<StockAdjustment> getStockAdjustmentList() {
		return stockAdjustmentList;
	}

	public void setStockAdjustmentList(Set<StockAdjustment> stockAdjustmentList) {
		updateCollection("stockAdjustmentList", stockAdjustmentList);
	}

	public BigDecimal getLastQuantity() {
		return lastQuantity;
	}

	public void setLastQuantity(BigDecimal lastQuantity) {
		this.lastQuantity = lastQuantity;
	}

	public BigDecimal getNewQuantity() {
		return newQuantity;
	}

	public void setNewQuantity(BigDecimal newQuantity) {
		this.newQuantity = newQuantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public BigDecimal getBuyingPrice() {
		return buyingPrice;
	}

	public void setBuyingPrice(BigDecimal buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<StockNotification> getStockNotifications() {
		return stockNotifications;
	}

	public void setStockNotifications(Set<StockNotification> stockNotifications) {
		updateCollection("stockNotifications", stockNotifications);
	}

	public StockType getStockType() {
		return stockType;
	}

	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	} 
	
	
	
}
