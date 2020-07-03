package com.codex.ecam.dto.inventory.rfq;

import com.codex.ecam.dto.BaseDTO;

public class RFQSupplierDTO extends BaseDTO {

	private Integer id;
	private Integer supplierId;
	private String supplierName;
	private String supplierAddress;
	private String supplierCity;
	private String supplierProvince;
	private String supplierPostalCode;
	private String supplierCountry;
	
	
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
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierAddress() {
		return supplierAddress;
	}
	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}
	public String getSupplierCity() {
		return supplierCity;
	}
	public void setSupplierCity(String supplierCity) {
		this.supplierCity = supplierCity;
	}
	public String getSupplierProvince() {
		return supplierProvince;
	}
	public void setSupplierProvince(String supplierProvince) {
		this.supplierProvince = supplierProvince;
	}
	public String getSupplierPostalCode() {
		return supplierPostalCode;
	}
	public void setSupplierPostalCode(String supplierPostalCode) {
		this.supplierPostalCode = supplierPostalCode;
	}
	public String getSupplierCountry() {
		return supplierCountry;
	}
	public void setSupplierCountry(String supplierCountry) {
		this.supplierCountry = supplierCountry;
	}
	

	

}
