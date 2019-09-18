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

import com.codex.ecam.constants.inventory.RFQStatus;
import com.codex.ecam.model.BaseModel;


@Entity
@Table(name = "tbl_rfq_status_change_log")
public class RFQStausChangeLog extends BaseModel {

	private static final long serialVersionUID = 3167763864035252289L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "rfq_status_change_log_s")
	@SequenceGenerator(name = "rfq_status_change_log_s", sequenceName = "rfq_status_change_log_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "rfq_id")
	@ManyToOne(targetEntity = RFQ.class, fetch = FetchType.LAZY)
	private RFQ rfq;

	@Column(name = "rfq_status_id")
	private RFQStatus rfqStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RFQ getRfq() {
		return rfq;
	}

	public void setRfq(RFQ rfq) {
		this.rfq = rfq;
	}

	public RFQStatus getRfqStatus() {
		return rfqStatus;
	}

	public void setRfqStatus(RFQStatus rfqStatus) {
		this.rfqStatus = rfqStatus;
	}

	

}
