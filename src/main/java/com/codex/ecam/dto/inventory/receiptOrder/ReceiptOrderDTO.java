package com.codex.ecam.dto.inventory.receiptOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.constants.inventory.ReceiptOrderStatus;
import com.codex.ecam.constants.inventory.ReceiptOrderType;
import com.codex.ecam.dto.BaseDTO;

public class ReceiptOrderDTO extends BaseDTO {

	private Integer id;
	private Integer supplierId;
	private Integer businessId;

	private Date dateOrdered;
	private Date dateReceived;

	private String statusName;
	private String supplierName;
	private String code;

	private ReceiptOrderStatus receiptOrderStatus=ReceiptOrderStatus.DRAFT;
	private ReceiptOrderType receiptOrderType=ReceiptOrderType.NORMAL;

	private List<ReceiptOrderItemDTO> items = new ArrayList<>();
	private List<ReceiptOrderTaxDTO> taxDTOs = new ArrayList<>();


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public Date getDateOrdered() {
		return dateOrdered;
	}
	public void setDateOrdered(Date dateOrdered) {
		this.dateOrdered = dateOrdered;
	}
	public Date getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public ReceiptOrderStatus getReceiptOrderStatus() {
		return receiptOrderStatus;
	}
	public void setReceiptOrderStatus(ReceiptOrderStatus receiptOrderStatus) {
		this.receiptOrderStatus = receiptOrderStatus;
	}
	public List<ReceiptOrderItemDTO> getItems() {
		return items;
	}
	public void setItems(List<ReceiptOrderItemDTO> items) {
		this.items = items;
	}

	public List<ReceiptOrderTaxDTO> getTaxDTOs() {
		return taxDTOs;
	}

	public void setTaxDTOs(List<ReceiptOrderTaxDTO> taxDTOs) {
		this.taxDTOs = taxDTOs;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public ReceiptOrderType getReceiptOrderType() {
		return receiptOrderType;
	}
	public void setReceiptOrderType(ReceiptOrderType receiptOrderType) {
		this.receiptOrderType = receiptOrderType;
	}


}
