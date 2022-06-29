package com.codex.ecam.dto.report.data.mrn;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.dto.BaseReportDTO;

public class MRNRepDTO extends BaseReportDTO {

    private String mrnNo;
    private String customerName;
    private String customerNo;
    private Date date;
    private Double currencyRate;
    private String mrnType;
    private String mrnStatus;
    private String woNo;
    private String requestedBy;
    private String businessName;
    private String businessAddress;
    private List<MRNRepItemDTO> mrnRepItemDTOs=new ArrayList<>();
    
	public String getMrnNo() {
		return mrnNo;
	}
	public void setMrnNo(String mrnNo) {
		this.mrnNo = mrnNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}
	public String getMrnType() {
		return mrnType;
	}
	public void setMrnType(String mrnType) {
		this.mrnType = mrnType;
	}
	public String getMrnStatus() {
		return mrnStatus;
	}
	public void setMrnStatus(String mrnStatus) {
		this.mrnStatus = mrnStatus;
	}
	public String getWoNo() {
		return woNo;
	}
	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessAddress() {
		return businessAddress;
	}
	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	public List<MRNRepItemDTO> getMrnRepItemDTOs() {
		return mrnRepItemDTOs;
	}
	public void setMrnRepItemDTOs(List<MRNRepItemDTO> mrnRepItemDTOs) {
		this.mrnRepItemDTOs = mrnRepItemDTOs;
	}    
    





}
