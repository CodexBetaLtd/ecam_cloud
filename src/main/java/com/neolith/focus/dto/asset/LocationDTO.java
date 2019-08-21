package com.neolith.focus.dto.asset;

import com.neolith.focus.dto.BaseDTO;

public class LocationDTO extends BaseDTO {

	private String longitude="79.8612";
	private String latitude="6.9270";
	private String locationTitel;
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLocationTitel() {
		return locationTitel;
	}
	public void setLocationTitel(String locationTitel) {
		this.locationTitel = locationTitel;
	}



}
