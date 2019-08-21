package com.neolith.focus.dto.inventory.stock;

import com.neolith.focus.dto.BaseDTO; 

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockDTO extends BaseDTO {

	private Integer businessIndex;
	private Integer stockRowIndex;

	private Integer id;
	private Integer businessId;
	private String businessRef;
	private Integer businessIdRef;

	private Integer siteId;
	private String site;

	private Integer partId;
	private String partName;//PartNo
	private String partCode;

	private Integer warehouseId;
	private String warehouseName;
	
	private String description;
	private String batchNo;
	private BigDecimal sellingPrice;
	private BigDecimal buyingPrice;

	private Integer refId;
	private String refCode;
	private Date refCratedDate;

	private Integer receiptOrderId;
	private String receiptOrderCode;

	private Integer aodId;
	private String aodCode;

	private Integer aodReturnId;
	private String aodReturnNo;

	private Integer stockAdjustmentId;
	private String stockAdjustmentCode;

	private String stockNo;
	private String stockTransactionDescription;

	private BigDecimal qtyMovement;
	private BigDecimal qtyOnHand;
	private BigDecimal minQty;
	private List<StockHistoryDTO> stockHistoryDTOs;
	
	private List<StockNotificationDTO> stockNotificationDTOs = new ArrayList<>();

	private String customerName;
	private String supplierName;
	private Date date=new Date();


	public Integer getBusinessIndex() {
		return businessIndex;
	}

	public void setBusinessIndex(Integer businessIndex) {
		this.businessIndex = businessIndex;
	}

	public Integer getStockRowIndex() {
		return stockRowIndex;
	}

	public void setStockRowIndex(Integer stockRowIndex) {
		this.stockRowIndex = stockRowIndex;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBusinessRef() {
		return businessRef;
	}

	public void setBusinessRef(String businessRef) {
		this.businessRef = businessRef;
	}

	public Integer getBusinessIdRef() {
		return businessIdRef;
	}

	public void setBusinessIdRef(Integer businessIdRef) {
		this.businessIdRef = businessIdRef;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Integer getRefId() {
		return refId;
	}

	public void setRefId(Integer refId) {
		this.refId = refId;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public Date getRefCratedDate() {
		return refCratedDate;
	}

	public void setRefCratedDate(Date refCratedDate) {
		this.refCratedDate = refCratedDate;
	}

	public Integer getReceiptOrderId() {
		return receiptOrderId;
	}

	public void setReceiptOrderId(Integer receiptOrderId) {
		this.receiptOrderId = receiptOrderId;
	}

	public String getReceiptOrderCode() {
		return receiptOrderCode;
	}

	public void setReceiptOrderCode(String receiptOrderCode) {
		this.receiptOrderCode = receiptOrderCode;
	}

	public Integer getAodId() {
		return aodId;
	}

	public void setAodId(Integer aodId) {
		this.aodId = aodId;
	}

	public String getAodCode() {
		return aodCode;
	}

	public void setAodCode(String aodCode) {
		this.aodCode = aodCode;
	}

	public Integer getAodReturnId() {
		return aodReturnId;
	}

	public void setAodReturnId(Integer aodReturnId) {
		this.aodReturnId = aodReturnId;
	}

	public String getAodReturnNo() {
		return aodReturnNo;
	}

	public void setAodReturnNo(String aodReturnNo) {
		this.aodReturnNo = aodReturnNo;
	}

	public Integer getStockAdjustmentId() {
		return stockAdjustmentId;
	}

	public void setStockAdjustmentId(Integer stockAdjustmentId) {
		this.stockAdjustmentId = stockAdjustmentId;
	}

	public String getStockAdjustmentCode() {
		return stockAdjustmentCode;
	}

	public void setStockAdjustmentCode(String stockAdjustmentCode) {
		this.stockAdjustmentCode = stockAdjustmentCode;
	}

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getStockTransactionDescription() {
		return stockTransactionDescription;
	}

	public void setStockTransactionDescription(String stockTransactionDescription) {
		this.stockTransactionDescription = stockTransactionDescription;
	}

	public BigDecimal getQtyMovement() {
		return qtyMovement;
	}

	public void setQtyMovement(BigDecimal qtyMovement) {
		this.qtyMovement = qtyMovement;
	}

	public BigDecimal getQtyOnHand() {
		return qtyOnHand;
	}

	public void setQtyOnHand(BigDecimal qtyOnHand) {
		this.qtyOnHand = qtyOnHand;
	}

	public BigDecimal getMinQty() {
		return minQty;
	}

	public void setMinQty(BigDecimal minQty) {
		this.minQty = minQty;
	}

	public List<StockHistoryDTO> getStockHistoryDTOs() {
		return stockHistoryDTOs;
	}

	public void setStockHistoryDTOs(List<StockHistoryDTO> stockHistoryDTOs) {
		this.stockHistoryDTOs = stockHistoryDTOs;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
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

	public List<StockNotificationDTO> getStockNotificationDTOs() {
		return stockNotificationDTOs;
	}

	public void setStockNotificationDTOs(List<StockNotificationDTO> stockNotificationDTOs) {
		this.stockNotificationDTOs = stockNotificationDTOs;
	}
 
}
