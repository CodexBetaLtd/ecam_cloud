package com.codex.ecam.model.asset;

import javax.persistence.*;

import com.codex.ecam.constants.LogType;
import com.codex.ecam.model.BaseModel;

@Entity
@Table( name = "tbl_asset_log")
public class AssetLog extends BaseModel {

	private static final long serialVersionUID = -424042165118627024L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_log_s")
	@SequenceGenerator(name = "asset_log_s", sequenceName = "asset_log_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	@JoinColumn(name = "asset_id")
	@ManyToOne( targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset asset;

    @Column(name = "log_type_id")
    private LogType logType;
	
	@Column(name="notes")
	private String notes;

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

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
