package com.codex.ecam.model.asset;

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

import com.codex.ecam.listeners.asset.AssetPersonalLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;

@Entity
@Table(name = "tbl_asset_user")
@EntityListeners({AssetPersonalLogListener.class})
public class AssetUser extends BaseModel {

	private static final long serialVersionUID = 3466843752790052309L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_user_s")
	@SequenceGenerator(name = "asset_user_s", sequenceName = "asset_user_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

	@JoinColumn(name = "user_id")
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
