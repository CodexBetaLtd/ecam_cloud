package com.codex.ecam.dto.app;

import com.codex.ecam.dto.BaseDTO;

public class AppWigetDTO extends BaseDTO {

    private Integer id;
    private Integer appId;
    private Integer wigetId;
    private String wigetName;

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

	public Integer getWigetId() {
		return wigetId;
	}

	public void setWigetId(Integer wigetId) {
		this.wigetId = wigetId;
	}

	public String getWigetName() {
		return wigetName;
	}

	public void setWigetName(String wigetName) {
		this.wigetName = wigetName;
	}



}
