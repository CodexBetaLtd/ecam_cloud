package com.codex.ecam.model.asset;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.listeners.asset.AssetCustomerLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.biz.customer.Customer;

@Entity
@Table(name="tbl_asset_customer")
@EntityListeners({AssetCustomerLogListener.class})
public class AssetCustomer extends BaseModel {

	private static final long serialVersionUID = -4742026916418181279L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="asset_customer_s")
	@SequenceGenerator(name="asset_customer_s", sequenceName="asset_customer_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn( name="asset_id" )
	@ManyToOne( targetEntity = Asset.class, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE } )
	private Asset asset;

	@JoinColumn( name="customer_id" )
	@ManyToOne( targetEntity = Customer.class, fetch = FetchType.LAZY, cascade ={ CascadeType.MERGE} )
	private Customer customer;

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
