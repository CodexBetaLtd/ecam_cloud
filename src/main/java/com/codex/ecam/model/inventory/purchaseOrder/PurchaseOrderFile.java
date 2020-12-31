package com.codex.ecam.model.inventory.purchaseOrder;

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
@Table(name="tbl_purchase_order_file")
public class PurchaseOrderFile extends BaseModel {
	
	private static final long serialVersionUID = -3548797942204045837L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="purchase_order_file_s")
	@SequenceGenerator(name="purchase_order_file_s", sequenceName="purchase_order_file_s", allocationSize=1)
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

	@JoinColumn(name = "purchase_order_id")
	@ManyToOne(targetEntity = PurchaseOrder.class, fetch = FetchType.LAZY)
	private PurchaseOrder purchaseOrder;

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


	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
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
