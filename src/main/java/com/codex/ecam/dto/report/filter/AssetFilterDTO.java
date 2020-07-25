package com.codex.ecam.dto.report.filter;

import com.codex.ecam.constants.inventory.PartType;
import com.codex.ecam.dto.BaseReportFilterDTO;

public class AssetFilterDTO extends BaseReportFilterDTO {
	
	private static String templateName="AssetView";
	private static String templatePath="/asset/";
	private static String reportName="asset-list-report";

	private  Integer locationId;
	private String locationName;
	private  Integer assetModelId;
	private String assetModelName;



	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getAssetModelId() {
		return assetModelId;
	}

	public void setAssetModelId(Integer assetModelId) {
		this.assetModelId = assetModelId;
	}

	public String getAssetModelName() {
		return assetModelName;
	}

	public void setAssetModelName(String assetModelName) {
		this.assetModelName = assetModelName;
	}

	public static String getTemplateName() {
		return templateName.concat(getTemplateType());
	}

	public static void setTemplateName(String templateName) {
		AssetFilterDTO.templateName = templateName;
	}

	public static String getTemplatePath() {
		return getPathToTemplate().concat(templatePath);
	}

	public static void setTemplatePath(String templatePath) {
		AssetFilterDTO.templatePath = templatePath;
	}

	public static String getReportName() {
		return reportName;
	}

	public static void setReportName(String reportName) {
		AssetFilterDTO.reportName = reportName;
	}




}
