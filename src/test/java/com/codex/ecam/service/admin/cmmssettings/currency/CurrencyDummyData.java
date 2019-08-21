package com.codex.ecam.service.admin.cmmssettings.currency;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.admin.CurrencyDTO;

public class CurrencyDummyData {

	private static CurrencyDummyData instance = null;
	
	public static CurrencyDummyData getInstance() {
		if (instance == null) {
			instance = new CurrencyDummyData();
		}
		return instance;
	}
	
	public CurrencyDTO getDummyDTOCurrencyOne() {
		
		CurrencyDTO currencyDTO = new CurrencyDTO();
		currencyDTO.setName("Test Currency 1");
		currencyDTO.setSymbol("Test$1");	

				
		return currencyDTO;
}
	
	public CurrencyDTO getDummyDTOCurrencyTwo() {
		
		CurrencyDTO currencyDTO = new CurrencyDTO();
		currencyDTO.setName("Test Currency 2");
		currencyDTO.setSymbol("Test$2");	
		
				
		return currencyDTO;
}
	
	public List<CurrencyDTO> getAllDummyData(){
		List<CurrencyDTO> list = new ArrayList<>(2);
		
		list.add(getDummyDTOCurrencyOne());
		list.add(getDummyDTOCurrencyTwo() );
			
		return list;
		
	}
}
