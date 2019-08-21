package com.neolith.focus.model.asset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.neolith.focus.model.BaseModel;

@Entity
@Table(name = "tbl_asset_owner")
public class AssetOwner extends BaseModel {

	private static final long serialVersionUID = -7632353374231316004L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_owner_s")
	@SequenceGenerator(name = "asset_owner_s", sequenceName = "asset_owner_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "email")
	private String email;

	@Column(name = "telephone")
	private Integer telephone;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTelephone() {
		return telephone;
	}

	public void setTelephone(Integer telephone) {
		this.telephone = telephone;
	}

}
