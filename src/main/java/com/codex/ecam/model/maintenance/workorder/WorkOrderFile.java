package com.codex.ecam.model.maintenance.workorder;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;

@Entity
@Table(name="tbl_wo_file")
public class WorkOrderFile extends BaseModel {
	
	private static final long serialVersionUID = -3548797942204045837L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="wo_file_s")
	@SequenceGenerator(name="wo_file_s", sequenceName="wo_file_s", allocationSize=1)
	@Column(name="id")
	private Integer id;	
	
	@Column(name="item_description")
	private String itemDescription;
	
	@Column(name="file_location")
	private String fileLocation;

	@JoinColumn(name = "wo_id")
	@ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
	private WorkOrder workOrder;
	
    @Column(name = "file_type")
    private String fileType;
    
    @Column(name = "date")
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

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
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
