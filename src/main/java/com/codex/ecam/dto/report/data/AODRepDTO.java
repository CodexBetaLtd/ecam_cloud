package com.codex.ecam.dto.report.data;


import java.util.Date;

import com.codex.ecam.dto.BaseReportDTO;

public class AODRepDTO extends BaseReportDTO {

    private String aodNo;
    private String customerName;
    private String customerNo;
    private Date date;
    private Double currencyRate;
    private String aodType;
    private String aodStatus;
    
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



}
