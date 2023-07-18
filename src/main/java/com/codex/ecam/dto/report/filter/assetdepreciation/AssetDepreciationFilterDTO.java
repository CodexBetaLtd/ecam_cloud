package com.codex.ecam.dto.report.filter.assetdepreciation;



import java.util.Date;

import com.codex.ecam.dto.BaseReportFilterDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetDepreciationFilterDTO extends BaseReportFilterDTO {

	private String templateName="AssetDepreciationReport";
	private String templatePath="/assetdepreciation/";
	private String reportName="Asset_Depreciation_Report";

	private Integer businessId;
	private Integer assetCategoryId;
	private Integer mainLocationId;
	private Integer subLocationId;
	private Integer subLocation2Id;
	private Integer departmentId;

	private String assetCategoryName;
	private String mainLocationName;
	private String subLocationName;
	private String subLocation2Name;
	private String departmentName;

	private Date fromDate;
	private Date toDate;

	public String getTemplateName() {
		return this.templateName.concat(getTemplateType());
	}

	public String getTemplatePath() {
		return getPathToTemplate().concat(this.templatePath);
	}
}
