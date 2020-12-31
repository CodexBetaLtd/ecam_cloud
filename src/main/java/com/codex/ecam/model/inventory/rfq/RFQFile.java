package com.codex.ecam.model.inventory.rfq;

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
@Table(name="tbl_rfq_file")
public class RFQFile extends BaseModel {
	
	private static final long serialVersionUID = -3548797942204045837L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="asset_file_s")
	@SequenceGenerator(name="asset_file_s", sequenceName="asset_file_s", allocationSize=1)
	@Column(name="id")
	private Integer id;	
	
	@Column(name="item_description")
	private String itemDescription;
	
	@Column(name="file_location")
	private String fileLocation;
  
	@Column(name = "file_type")
	private String fileType;
	
    @Column(name = "date")
    private Date fileDate;

	@JoinColumn(name = "rfq_id")
	@ManyToOne(targetEntity = RFQ.class, fetch = FetchType.LAZY)
	private RFQ rfq;

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

	public RFQ getRfq() {
		return rfq;
	}

	public void setRfq(RFQ rfq) {
		this.rfq = rfq;
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
