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

import com.codex.ecam.constants.LogType;
import com.codex.ecam.constants.inventory.RFQStatus;
import com.codex.ecam.model.BaseModel;


@Entity
@Table(name = "tbl_rfq_change_log")
public class RFQChangeLog extends BaseModel {

	private static final long serialVersionUID = 3167763864035252289L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "rfq_change_log_s")
	@SequenceGenerator(name = "rfq_change_log_s", sequenceName = "rfq_change_log_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "rfq_id")
	@ManyToOne(targetEntity = RFQ.class, fetch = FetchType.LAZY)
	private RFQ rfq;

	@Column(name = "rfq_status_id")
	private RFQStatus rfqStatus;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "log_type_id")
	private LogType logType;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	

}
