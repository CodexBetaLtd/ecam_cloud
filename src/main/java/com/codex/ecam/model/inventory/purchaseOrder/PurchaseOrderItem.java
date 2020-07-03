package com.codex.ecam.model.inventory.purchaseOrder;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.TaxValue;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.model.inventory.stock.StockHistory;
import com.codex.ecam.model.maintenance.Account;
import com.codex.ecam.model.maintenance.ChargeDepartment;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_purchase_order_item")
public class PurchaseOrderItem extends BaseModel {

	private static final long serialVersionUID = -8110969685723306596L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "purchase_order_item_s")
	@SequenceGenerator(name = "purchase_order_item_s", sequenceName = "purchase_order_item_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "account_id")
	@ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
	private Account account;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@JoinColumn(name = "charge_department_id")
	@ManyToOne(targetEntity = ChargeDepartment.class, fetch = FetchType.LAZY)
	private ChargeDepartment chargeDepartment;

	@JoinColumn(name = "parent_purchase_order_item_id")
	@ManyToOne(targetEntity = PurchaseOrderItem.class, fetch = FetchType.LAZY)
	private PurchaseOrderItem parentPurchaseOrderItem;
	
	@JoinColumn(name = "purchase_order_id")
	@ManyToOne(targetEntity = PurchaseOrder.class, fetch = FetchType.LAZY)
	private PurchaseOrder purchaseOrder;

	@JoinColumn(name = "requested_by_user_id")
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User requestedByUser;

	@JoinColumn(name = "site_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset site;

	@JoinColumn(name = "source_asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset sourceAsset;

	@JoinColumn(name = "source_work_order_id")
	@ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
	private WorkOrder sourceWorkOrder;

	@JoinColumn(name = "stock_history_id")
	@ManyToOne(targetEntity = StockHistory.class, fetch = FetchType.LAZY)
	private StockHistory stockHistory;

	@JoinColumn(name = "stock_id")
	@ManyToOne(targetEntity = Stock.class, fetch = FetchType.LAZY)
	private Stock stock;

	@JoinColumn(name = "supplier_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business supplier;

	@Column(name = "business_asset_no")
	private String businessAssetNo;

	@Column(name = "description")
	private String description;

	@Column(name = "remote_org_unit_price")
	private Double remoteOrgUnitPrice;

	@Column(name = "tax_rate")
	private Double taxRate;

	@Column(name = "total_price")
	private Double totalPrice;

	@Column(name = "unit_price")
	private Double unitPrice;

	@Column(name = "qty_on_order")
	private Integer qtyOnOrder;

	@Column(name = "is_added_directly_to_purchase_order")
	private Boolean isAddedDirectlyToPurchaseOrder;

	@Column(name = "is_production_equipment_down_while_on_order")
	private Boolean isProductionEquipmentDownWhileOnOrder;

	@Column(name = "is_supplier_confirmed")
	private Boolean isSupplierConfirmed;

	@Column(name = "required_by_date")
	private Date requiredByDate;
	
	@OneToMany(mappedBy = "purchaseOrderItem", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<PurchaseOrderItemRFQItem> rfqItems = new ArrayList<>();
	
	@OneToMany(mappedBy = "purchaseOrderItem", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<PurchaseOrderItemTax> purchaseOrderItemTaxs = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public ChargeDepartment getChargeDepartment() {
		return chargeDepartment;
	}

	public void setChargeDepartment(ChargeDepartment chargeDepartment) {
		this.chargeDepartment = chargeDepartment;
	}

	public PurchaseOrderItem getParentPurchaseOrderItem() {
		return parentPurchaseOrderItem;
	}

	public void setParentPurchaseOrderItem(PurchaseOrderItem parentPurchaseOrderItem) {
		this.parentPurchaseOrderItem = parentPurchaseOrderItem;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public User getRequestedByUser() {
		return requestedByUser;
	}

	public void setRequestedByUser(User requestedByUser) {
		this.requestedByUser = requestedByUser;
	}

	public Asset getSite() {
		return site;
	}

	public void setSite(Asset site) {
		this.site = site;
	}

	public Asset getSourceAsset() {
		return sourceAsset;
	}

	public void setSourceAsset(Asset sourceAsset) {
		this.sourceAsset = sourceAsset;
	}

	public WorkOrder getSourceWorkOrder() {
		return sourceWorkOrder;
	}

	public void setSourceWorkOrder(WorkOrder sourceWorkOrder) {
		this.sourceWorkOrder = sourceWorkOrder;
	}

	public StockHistory getStockHistory() {
		return stockHistory;
	}

	public void setStockHistory(StockHistory stockHistory) {
		this.stockHistory = stockHistory;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Business getSupplier() {
		return supplier;
	}

	public void setSupplier(Business supplier) {
		this.supplier = supplier;
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

	public Double getRemoteOrgUnitPrice() {
		return remoteOrgUnitPrice;
	}

	public void setRemoteOrgUnitPrice(Double remoteOrgUnitPrice) {
		this.remoteOrgUnitPrice = remoteOrgUnitPrice;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getQtyOnOrder() {
		return qtyOnOrder;
	}

	public void setQtyOnOrder(Integer qtyOnOrder) {
		this.qtyOnOrder = qtyOnOrder;
	}

	public Boolean getIsAddedDirectlyToPurchaseOrder() {
		return isAddedDirectlyToPurchaseOrder;
	}

	public void setIsAddedDirectlyToPurchaseOrder(Boolean isAddedDirectlyToPurchaseOrder) {
		this.isAddedDirectlyToPurchaseOrder = isAddedDirectlyToPurchaseOrder;
	}

	public Boolean getIsProductionEquipmentDownWhileOnOrder() {
		return isProductionEquipmentDownWhileOnOrder;
	}

	public void setIsProductionEquipmentDownWhileOnOrder(Boolean isProductionEquipmentDownWhileOnOrder) {
		this.isProductionEquipmentDownWhileOnOrder = isProductionEquipmentDownWhileOnOrder;
	}

	public Boolean getIsSupplierConfirmed() {
		return isSupplierConfirmed;
	}

	public void setIsSupplierConfirmed(Boolean isSupplierConfirmed) {
		this.isSupplierConfirmed = isSupplierConfirmed;
	}

	public Date getRequiredByDate() {
		return requiredByDate;
	}

	public void setRequiredByDate(Date requiredByDate) {
		this.requiredByDate = requiredByDate;
	}

	public List<PurchaseOrderItemRFQItem> getRfqItems() {
		return rfqItems;
	}

	public void setRfqItems(List<PurchaseOrderItemRFQItem> rfqItems) {
		this.rfqItems = rfqItems;
	}

	public List<PurchaseOrderItemTax> getPurchaseOrderItemTaxs() {
		return purchaseOrderItemTaxs;
	}

	public void setPurchaseOrderItemTaxs(List<PurchaseOrderItemTax> purchaseOrderItemTaxs) {
		this.purchaseOrderItemTaxs = purchaseOrderItemTaxs;
	}



	
}
