package com.codex.ecam.model.inventory.rfq;
/*package com.codex.ecam.model.purchasing;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;


@Entity
@Table(name = "tbl_rfq_status")
public class RFQStatus extends BaseModel {

	private static final long serialVersionUID = 3167763864035252289L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "rfq_status_s")
	@SequenceGenerator(name = "rfq_status_s", sequenceName = "rfq_status_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@OneToMany(mappedBy = "rfqStatus", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<RFQ> rfqStatusList;

	@OneToMany(mappedBy = "fromStatus", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<RFQStatusTransition> rfqFromStatusList;

	@OneToMany(mappedBy = "toStatus", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<RFQStatusTransition> rfqToStatusList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<RFQ> getRfqStatusList() {
		return rfqStatusList;
	}

	public void setRfqStatusList(List<RFQ> rfqStatusList) {
		this.rfqStatusList = rfqStatusList;
	}

	public List<RFQStatusTransition> getRfqFromStatusList() {
		return rfqFromStatusList;
	}

	public void setRfqFromStatusList(List<RFQStatusTransition> rfqFromStatusList) {
		this.rfqFromStatusList = rfqFromStatusList;
	}

	public List<RFQStatusTransition> getRfqToStatusList() {
		return rfqToStatusList;
	}

	public void setRfqToStatusList(List<RFQStatusTransition> rfqToStatusList) {
		this.rfqToStatusList = rfqToStatusList;
	}

}
*/