package com.codex.ecam.dto.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.BaseDTO;

public class AppDTO extends BaseDTO {

	private Integer id;
	private String name;
	private BigDecimal rate;

	private List<AppMenuDTO> appMenus = new ArrayList<>();
	private List<RelatedAppDTO> relatedApps = new ArrayList<>();
	private List<AppWigetDTO> appWigets = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public List<AppMenuDTO> getAppMenus() {
		return appMenus;
	}

	public void setAppMenus(List<AppMenuDTO> appMenus) {
		this.appMenus = appMenus;
	}

	public List<RelatedAppDTO> getRelatedApps() {
		return relatedApps;
	}

	public void setRelatedApps(List<RelatedAppDTO> relatedApps) {
		this.relatedApps = relatedApps;
	}

	public List<AppWigetDTO> getAppWigets() {
		return appWigets;
	}

	public void setAppWigets(List<AppWigetDTO> appWigets) {
		this.appWigets = appWigets;
	}

	
}
