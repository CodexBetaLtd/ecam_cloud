package com.codex.ecam.dto.app;

import java.math.BigDecimal;

import com.codex.ecam.dto.BaseDTO;

public class RelatedAppDTO extends BaseDTO {

	private Integer id;
	private Integer appId;
	private Integer relatedAppId;
	private String relatedAppName;

	private BigDecimal rate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getRelatedAppId() {
		return relatedAppId;
	}

	public void setRelatedAppId(Integer relatedAppId) {
		this.relatedAppId = relatedAppId;
	}

	public String getRelatedAppName() {
		return relatedAppName;
	}

	public void setRelatedAppName(String relatedAppName) {
		this.relatedAppName = relatedAppName;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

}
