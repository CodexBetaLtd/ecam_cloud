package com.codex.ecam.dto.app;

import com.codex.ecam.constants.Widgets;

public class WigetDTO {

    private Integer Id;
    private String name;
    private Widgets widget;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

	public Widgets getWidget() {
		return widget;
	}

	public void setWidget(Widgets widget) {
		this.widget = widget;
	}

    
}
