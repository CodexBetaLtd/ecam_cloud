package com.codex.ecam.dto.report.data.aodReturn;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.dto.BaseReportDTO;

public class AODReturnRepDTO extends BaseReportDTO {

	private String preparedBy;
	private String authorisedBy;

	private String aodCustomerName;
	private String aodCustomerCode;
	private String aodCustomerAddress01;
	private String aodCustomerAddress02;
	private String aodCustomerAddress03;

	private String aodJobNo;
	private String aodRequestBy;

	private String aodReturnNo;
	private String aodReturnRefNo; // AOD NO
	private Date aodReturnDate;
	private String aodReturnStatus;
    private String businessName;
    private String businessAddress;
    private List<AODReturnRepItemDTO> aodReturnItemRepDTOs=new ArrayList<>();
	public String getPreparedBy() {
		return preparedBy;
	}
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}
	public String getAuthorisedBy() {
		return authorisedBy;
	}
	public void setAuthorisedBy(String authorisedBy) {
		this.authorisedBy = authorisedBy;
	}
	public String getAodCustomerName() {
		return aodCustomerName;
	}
	public void setAodCustomerName(String aodCustomerName) {
		this.aodCustomerName = aodCustomerName;
	}
	public String getAodCustomerCode() {
		return aodCustomerCode;
	}
	public void setAodCustomerCode(String aodCustomerCode) {
		this.aodCustomerCode = aodCustomerCode;
	}
	public String getAodCustomerAddress01() {
		return aodCustomerAddress01;
	}
	public void setAodCustomerAddress01(String aodCustomerAddress01) {
		this.aodCustomerAddress01 = aodCustomerAddress01;
	}
	public String getAodCustomerAddress02() {
		return aodCustomerAddress02;
	}
	public void setAodCustomerAddress02(String aodCustomerAddress02) {
		this.aodCustomerAddress02 = aodCustomerAddress02;
	}
	public String getAodCustomerAddress03() {
		return aodCustomerAddress03;
	}
	public void setAodCustomerAddress03(String aodCustomerAddress03) {
		this.aodCustomerAddress03 = aodCustomerAddress03;
	}
	public String getAodJobNo() {
		return aodJobNo;
	}
	public void setAodJobNo(String aodJobNo) {
		this.aodJobNo = aodJobNo;
	}
	public String getAodRequestBy() {
		return aodRequestBy;
	}
	public void setAodRequestBy(String aodRequestBy) {
		this.aodRequestBy = aodRequestBy;
	}
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
	public Date getAodReturnDate() {
		return aodReturnDate;
	}
	public void setAodReturnDate(Date aodReturnDate) {
		this.aodReturnDate = aodReturnDate;
	}
	public String getAodReturnStatus() {
		return aodReturnStatus;
	}
	public void setAodReturnStatus(String aodReturnStatus) {
		this.aodReturnStatus = aodReturnStatus;
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
	public List<AODReturnRepItemDTO> getAodReturnItemRepDTOs() {
		return aodReturnItemRepDTOs;
	}
	public void setAodReturnItemRepDTOs(List<AODReturnRepItemDTO> aodReturnItemRepDTOs) {
		this.aodReturnItemRepDTOs = aodReturnItemRepDTOs;
	}    


    
	



}
