package com.neolith.focus.util.type;

import java.util.List;

public class GenericCheckBox<Main, Sub> {
	
	private Main main;
	private Sub sub;
	
	private Boolean isSelected;
	private List<GenericCheckBox<Main, Sub>> childList = null;
	
	public GenericCheckBox(Main main, Sub sub, Boolean isSelected){
		setMain(main);
		setSub(sub);
		setIsSelected(isSelected);
	}
	
	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Sub getSub() {
		return sub;
	}

	public void setSub(Sub sub) {
		this.sub = sub;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}
	
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public List<GenericCheckBox<Main, Sub>> getChildList() {
		return childList;
	}
	
	public void setChildList(List<GenericCheckBox<Main, Sub>> childList) {
		this.childList = childList;
	}

}
