package com.neolith.focus.model.inventory.rfq;

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.asset.Asset;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tbl_rfq_queue")
public class RFQQueue extends BaseModel {

	private static final long serialVersionUID = -8110969685723306596L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "rfq_queue_s")
	@SequenceGenerator(name = "rfq_queue_s", sequenceName = "rfq_queue_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@Column(name = "date_added")
	private Date addedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

}
