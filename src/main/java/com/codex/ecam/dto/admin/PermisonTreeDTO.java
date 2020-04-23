package com.codex.ecam.dto.admin;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.BaseDTO;

public class PermisonTreeDTO extends BaseDTO {
	private Integer id;
	private String text;
	private String continent;
	private String type;
	private Boolean anyChildren=Boolean.FALSE;
	private Boolean checkedFieldName=Boolean.FALSE;
	private List<PermisonTreeDTO> children=new ArrayList<>();


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getAnyChildren() {
		return anyChildren;
	}
	public void setAnyChildren(Boolean anyChildren) {
		this.anyChildren = anyChildren;
	}
	public List<PermisonTreeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<PermisonTreeDTO> children) {
		this.children = children;
	}
	public Boolean getCheckedFieldName() {
		return checkedFieldName;
	}
	public void setCheckedFieldName(Boolean checkedFieldName) {
		this.checkedFieldName = checkedFieldName;
	}



}
