package com.codex.ecam.model.inventory.mrn;

import java.math.BigDecimal;

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
import com.codex.ecam.model.asset.Asset;

@Entity
@Table(name = "tbl_mrn_item")
public class MRNItem extends BaseModel {

    private static final long serialVersionUID = -5566965874688332116L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "mrn_item_s")
    @SequenceGenerator(name = "mrn_item_s", sequenceName = "mrn_item_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "mrn_id")
    @ManyToOne(targetEntity = MRN.class, fetch = FetchType.LAZY)
    private MRN mrn;

    @JoinColumn(name = "part_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset part;

    @Column(name = "batch_no")
    private String batchNo;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "remain_quantity")
    private BigDecimal remainQuantity;
    
    @Column(name = "approved_quantity")
    private BigDecimal approvedQuantity;

    @Column(name = "description")
    private String description;

    @Column(name = "item_cost")
    private BigDecimal itemCost;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Asset getPart() {
        return part;
    }

    public void setPart(Asset part) {
        this.part = part;
    }


    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }



    public BigDecimal getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(BigDecimal remainQuantity) {
		this.remainQuantity = remainQuantity;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

	public MRN getMrn() {
		return mrn;
	}

	public void setMrn(MRN mrn) {
		this.mrn = mrn;
	}

	public BigDecimal getApprovedQuantity() {
		return approvedQuantity;
	}

	public void setApprovedQuantity(BigDecimal approvedQuantity) {
		this.approvedQuantity = approvedQuantity;
	}




}
