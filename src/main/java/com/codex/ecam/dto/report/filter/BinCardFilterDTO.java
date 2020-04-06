package com.codex.ecam.dto.report.filter;



import com.codex.ecam.dto.BaseReportFilterDTO;

public class BinCardFilterDTO extends BaseReportFilterDTO {


	private Integer stockId;
	private String stockNo;
	private Integer partId;
	private String partCode;
	public Integer getStockId() {
		return stockId;
	}
	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	
}
