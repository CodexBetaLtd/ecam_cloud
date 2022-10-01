package com.codex.ecam.dto.report.data.aod;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.dto.BaseReportDTO;

public class AODRepDTO extends BaseReportDTO {

    private String aodNo;
    private String customerName;
    private String customerNo;
    private Date date;
    private Double currencyRate;
    private String aodType;
    private String aodStatus;
    private String woNo;
    private String requestedBy;
    private String businessName;
    private String businessAddress;
    private List<AODRepItemDataDTO> aodItemRepDTOs=new ArrayList<>();    
    
	public String getAodNo() {
		return aodNo;
	}
	public void setAodNo(String aodNo) {
		this.aodNo = aodNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public String getAodType() {
		return aodType;
	}
	public void setAodType(String aodType) {
		this.aodType = aodType;
	}
	public String getAodStatus() {
		return aodStatus;
	}
	public void setAodStatus(String aodStatus) {
		this.aodStatus = aodStatus;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
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
	public List<AODRepItemDataDTO> getAodItemRepDTOs() {
		return aodItemRepDTOs;
	}
	public void setAodItemRepDTOs(List<AODRepItemDataDTO> aodItemRepDTOs) {
		this.aodItemRepDTOs = aodItemRepDTOs;
	}



}
