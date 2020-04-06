package com.codex.ecam.dto.report.data;


import java.util.Date;

import com.codex.ecam.dto.BaseReportDTO;

public class AODReturnRepDTO extends BaseReportDTO {

    private String aodReturnNo;
    private String aodReturnRefNo;
    private String customerNo;
    private Date aodReturnDate;
    private Double currencyRate;
    private String aodType;
    private String aodReturnStatus;
    
	public String getAodReturnNo() {
		return aodReturnNo;
	}
	public void setAodReturnNo(String aodReturnNo) {
		this.aodReturnNo = aodReturnNo;
	}
	public String getAodReturnRefNo() {
		return aodReturnRefNo;
	}
	public void setAodReturnRefNo(String aodReturnRefNo) {
		this.aodReturnRefNo = aodReturnRefNo;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public Date getAodReturnDate() {
		return aodReturnDate;
	}
	public void setAodReturnDate(Date aodReturnDate) {
		this.aodReturnDate = aodReturnDate;
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
	public String getAodReturnStatus() {
		return aodReturnStatus;
	}
	public void setAodReturnStatus(String aodReturnStatus) {
		this.aodReturnStatus = aodReturnStatus;
	}
    
	



}
