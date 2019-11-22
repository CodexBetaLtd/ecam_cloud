package com.codex.ecam.model.inventory.mrnReturn;

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
import com.codex.ecam.model.inventory.mrn.MRNItem;

@Entity
@Table(name = "tbl_mrn_return_item")
public class MRNReturnItem extends BaseModel {

    private static final long serialVersionUID = -5566965874688332116L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "mrn_item_s")
    @SequenceGenerator(name = "mrn_item_s", sequenceName = "mrn_item_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "mrn_return_id")
    @ManyToOne(targetEntity = MRNReturn.class, fetch = FetchType.LAZY)
    private MRNReturn mrnReturn;

    @JoinColumn(name = "mrn_item_id")
    @ManyToOne(targetEntity = MRNItem.class, fetch = FetchType.LAZY)
    private MRNItem mrnItem;

    @Column(name = "return_quantity")
    private BigDecimal returnQuantity;
    
    @Column(name = "mrn_item_current_quantity")
    private BigDecimal mrnItemCurrentQuantity;

    @Column(name = "description")
    private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public MRNItem getMrnItem() {
		return mrnItem;
	}

	public void setMrnItem(MRNItem mrnItem) {
		this.mrnItem = mrnItem;
	}
	
	

	public MRNReturn getMrnReturn() {
		return mrnReturn;
	}

	public void setMrnReturn(MRNReturn mrnReturn) {
		this.mrnReturn = mrnReturn;
	}

	public BigDecimal getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(BigDecimal returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getMrnItemCurrentQuantity() {
		return mrnItemCurrentQuantity;
	}

	public void setMrnItemCurrentQuantity(BigDecimal mrnItemCurrentQuantity) {
		this.mrnItemCurrentQuantity = mrnItemCurrentQuantity;
	}



}
