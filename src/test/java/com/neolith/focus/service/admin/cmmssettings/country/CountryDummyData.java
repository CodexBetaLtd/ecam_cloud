package com.neolith.focus.service.admin.cmmssettings.country;

import com.neolith.focus.dto.admin.CountryDTO;

import java.util.ArrayList;
import java.util.List;

public class CountryDummyData {

	private static CountryDummyData instance = null;
	
	public static CountryDummyData getInstance() {
		if (instance == null) {
			instance = new CountryDummyData();
		}
		return instance;
	}
	
	public CountryDTO getDummyDTOCountryOne() {
		
		CountryDTO countryDTO = new CountryDTO();
		countryDTO.setName("Test Country 1");
		countryDTO.setShortName("Test Short name 1");		
			
		return countryDTO;
}
	
	public CountryDTO getDummyDTOCountryTwo() {
		
		CountryDTO countryDTO = new CountryDTO();
		countryDTO.setName("Test Country 2");
		countryDTO.setShortName("Test Short name 2");		
			
		return countryDTO;
}
	
	public List<CountryDTO> getAllDummyData(){
		List<CountryDTO> list = new ArrayList<>(2);
		
		list.add(getDummyDTOCountryOne());
		list.add(getDummyDTOCountryTwo() );
			
		return list;
		
	}
}
