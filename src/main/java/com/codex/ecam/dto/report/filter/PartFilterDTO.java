package com.codex.ecam.dto.report.filter;

import com.codex.ecam.constants.inventory.PartType;
import com.codex.ecam.dto.BaseReportFilterDTO;

public class PartFilterDTO extends BaseReportFilterDTO {
	
	private static String templateName="PartView_1";
	private static String templatePath="/inventory/part/";
	private static String reportName="part-list-report";

	private PartType partType;
	

	public PartType getPartType() {
		return partType;
	}

	public void setPartType(PartType partType) {
		this.partType = partType;
	}

	public static String getTemplateName() {
		return templateName.concat(getTemplateType());
	}

	public static void setTemplateName(String templateName) {
		PartFilterDTO.templateName = templateName;
	}

	public static String getTemplatePath() {
		return getPathToTemplate().concat(templatePath);
	}

	public static void setTemplatePath(String templatePath) {
		PartFilterDTO.templatePath = templatePath;
	}

	public static String getReportName() {
		return reportName;
	}

	public static void setReportName(String reportName) {
		PartFilterDTO.reportName = reportName;
	}




}
