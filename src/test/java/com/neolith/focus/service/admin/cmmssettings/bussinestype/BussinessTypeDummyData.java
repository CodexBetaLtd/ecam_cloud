package com.neolith.focus.service.admin.cmmssettings.bussinestype;

import com.neolith.focus.dto.biz.business.BussinessTypeDTO;

import java.util.ArrayList;
import java.util.List;

public class BussinessTypeDummyData {

	private static BussinessTypeDummyData instance = null;
	
	public static BussinessTypeDummyData getInstance() {
		if (instance == null) {
			instance = new BussinessTypeDummyData();
		}
		return instance;
	}
	
	public BussinessTypeDTO getDummyDTOBussinessTypeOne() {
		
		BussinessTypeDTO bussinessTypeDTO = new BussinessTypeDTO();
		bussinessTypeDTO.setAllParent("Test All parent 1");
		bussinessTypeDTO.setBusinessTypeDefinitionName("Test Bussiness type 1");		
		bussinessTypeDTO.setBusinessTypeDefinitionShort("Test def short name 1");	
		bussinessTypeDTO.setDefaultParentId(false);		
		bussinessTypeDTO.setDefinable(true);		
				
		return bussinessTypeDTO;
}
	
	public BussinessTypeDTO getDummyDTOBussinessTypeTwo() {
		
		BussinessTypeDTO bussinessTypeDTO = new BussinessTypeDTO();
		bussinessTypeDTO.setAllParent("Test All parent 2");
		bussinessTypeDTO.setBusinessTypeDefinitionName("Test Bussiness type 2");		
		bussinessTypeDTO.setBusinessTypeDefinitionShort("Test def short name 2");		
		bussinessTypeDTO.setDefaultParentId(true);		
		bussinessTypeDTO.setDefinable(false);		
		
				
		return bussinessTypeDTO;
}
	
	public List<BussinessTypeDTO> getAllDummyData(){
		List<BussinessTypeDTO> list = new ArrayList<>(2);
		
		list.add( getDummyDTOBussinessTypeOne());
		list.add(getDummyDTOBussinessTypeTwo());
			
		return list;
		
	}
}
