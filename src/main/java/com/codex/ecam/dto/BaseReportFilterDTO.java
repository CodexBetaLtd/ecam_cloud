package com.codex.ecam.dto;

import java.util.Date;

public abstract class BaseReportFilterDTO {

    private Date date;
    private Date fromDate;
    private Date toDate;
    private static String pathToTemplate="/resources/report";
    private static String templateType=".jrxml";

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

	public static String getPathToTemplate() {
		return pathToTemplate;
	}

	public static void setPathToTemplate(String pathToTemplate) {
		BaseReportFilterDTO.pathToTemplate = pathToTemplate;
	}

	public static String getTemplateType() {
		return templateType;
	}

	public static void setTemplateType(String templateType) {
		BaseReportFilterDTO.templateType = templateType;
	}
    
    
}