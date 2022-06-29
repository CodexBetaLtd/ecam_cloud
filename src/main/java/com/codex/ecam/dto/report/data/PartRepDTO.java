package com.codex.ecam.dto.report.data;

import com.codex.ecam.constants.inventory.PartType;
import com.codex.ecam.dto.BaseReportDTO;

public class PartRepDTO extends BaseReportDTO {

	private String partReportItemCode;
	private String partReportItemDescription;
	private String partReportPartNo;

	private PartType partType;

	public String getPartReportItemCode() {
		return partReportItemCode;
	}

	public String getPartReportItemDescription() {
		return partReportItemDescription;
	}

	public String getPartReportPartNo() {
		return partReportPartNo;
	}

	public void setPartReportItemCode(String partReportItemCode) {
		this.partReportItemCode = partReportItemCode;
	}

	public void setPartReportItemDescription(String partReportItemDescription) {
		this.partReportItemDescription = partReportItemDescription;
	}

	public void setPartReportPartNo(String partReportPartNo) {
		this.partReportPartNo = partReportPartNo;
	}

	public PartType getPartType() {
		return partType;
	}

	public void setPartType(PartType partType) {
		this.partType = partType;
	}


}
