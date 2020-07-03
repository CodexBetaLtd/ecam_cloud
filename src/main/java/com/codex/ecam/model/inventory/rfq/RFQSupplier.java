package com.codex.ecam.model.inventory.rfq;

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
import com.codex.ecam.model.biz.supplier.Supplier;


@Entity
@Table(name = "tbl_rfq_supplier")
public class RFQSupplier extends BaseModel {

	private static final long serialVersionUID = 3167763864035252289L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "rfq_supplier_s")
	@SequenceGenerator(name = "rfq_supplier_s", sequenceName = "rfq_supplier_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "supplier_id")
	@ManyToOne(targetEntity = Supplier.class, fetch = FetchType.LAZY)
	private Supplier supplier;
	
	@JoinColumn(name = "rfq_id")
	@ManyToOne(targetEntity = RFQ.class, fetch = FetchType.LAZY)
	private RFQ rfq;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public RFQ getRfq() {
		return rfq;
	}

	public void setRfq(RFQ rfq) {
		this.rfq = rfq;
	}



}
