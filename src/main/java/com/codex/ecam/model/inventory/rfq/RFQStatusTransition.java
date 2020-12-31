package com.codex.ecam.model.inventory.rfq;

import javax.persistence.*;

import com.codex.ecam.constants.inventory.RFQStatus;
import com.codex.ecam.model.BaseModel;


@Entity
@Table(name = "tbl_rfq_status_transition")
public class RFQStatusTransition extends BaseModel {

	private static final long serialVersionUID = -8110969685723306596L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "rfq_status_transition_s")
	@SequenceGenerator(name = "rfq_status_transition_s", sequenceName = "rfq_status_transition_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name="from_status_id")
	private RFQStatus fromStaus;
	
	@Column(name="to_status_id")
	private RFQStatus toStatus;

	@Column(name = "default_lable")
	private String defaultLable;

	@Column(name = "name")
	private String name;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDefaultLable() {
		return defaultLable;
	}

	public void setDefaultLable(String defaultLable) {
		this.defaultLable = defaultLable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RFQStatus getFromStaus() {
		return fromStaus;
	}

	public void setFromStaus(RFQStatus fromStaus) {
		this.fromStaus = fromStaus;
	}

	public RFQStatus getToStatus() {
		return toStatus;
	}

	public void setToStatus(RFQStatus toStatus) {
		this.toStatus = toStatus;
	}
	
	

}
