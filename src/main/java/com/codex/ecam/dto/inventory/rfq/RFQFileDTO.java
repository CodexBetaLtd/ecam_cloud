package com.codex.ecam.dto.inventory.rfq;

import java.util.Date;

import com.codex.ecam.dto.BaseDTO;

public class RFQFileDTO extends BaseDTO {

	private Integer id;
	private String itemDescription;
	private String fileLocation;
	private String fileType;
	private Date fileDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Date getFileDate() {
		return fileDate;
	}
	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}

	

}
