package com.codex.ecam.dto.report.data;


import java.math.BigDecimal;
import java.util.Date;

import com.codex.ecam.dto.BaseReportDTO;

public class BinCardRepDTO extends BaseReportDTO {

	private String binCardStockNo;
	private String binCardPartCode;
	private String binCardStockDescription;
	private BigDecimal binCardStockQty = BigDecimal.ZERO;
	private BigDecimal binCardAfterQty = BigDecimal.ZERO;
	private BigDecimal binCardBeforeQty = BigDecimal.ZERO;
	private BigDecimal binCardBalance = BigDecimal.ZERO;
	private BigDecimal binCardTotalCurrentQty = BigDecimal.ZERO;
	private Date binCardDate;
	
	public String getBinCardStockNo() {
		return binCardStockNo;
	}
	public void setBinCardStockNo(String binCardStockNo) {
		this.binCardStockNo = binCardStockNo;
	}
	public String getBinCardPartCode() {
		return binCardPartCode;
	}
	public void setBinCardPartCode(String binCardPartCode) {
		this.binCardPartCode = binCardPartCode;
	}
	public String getBinCardStockDescription() {
		return binCardStockDescription;
	}
	public void setBinCardStockDescription(String binCardStockDescription) {
		this.binCardStockDescription = binCardStockDescription;
	}
	public BigDecimal getBinCardStockQty() {
		return binCardStockQty;
	}
	public void setBinCardStockQty(BigDecimal binCardStockQty) {
		this.binCardStockQty = binCardStockQty;
	}
	public BigDecimal getBinCardAfterQty() {
		return binCardAfterQty;
	}
	public void setBinCardAfterQty(BigDecimal binCardAfterQty) {
		this.binCardAfterQty = binCardAfterQty;
	}
	public BigDecimal getBinCardBeforeQty() {
		return binCardBeforeQty;
	}
	public void setBinCardBeforeQty(BigDecimal binCardBeforeQty) {
		this.binCardBeforeQty = binCardBeforeQty;
	}
	public BigDecimal getBinCardBalance() {
		return binCardBalance;
	}
	public void setBinCardBalance(BigDecimal binCardBalance) {
		this.binCardBalance = binCardBalance;
	}
	public BigDecimal getBinCardTotalCurrentQty() {
		return binCardTotalCurrentQty;
	}
	public void setBinCardTotalCurrentQty(BigDecimal binCardTotalCurrentQty) {
		this.binCardTotalCurrentQty = binCardTotalCurrentQty;
	}
	public Date getBinCardDate() {
		return binCardDate;
	}
	public void setBinCardDate(Date binCardDate) {
		this.binCardDate = binCardDate;
	}

	







}
