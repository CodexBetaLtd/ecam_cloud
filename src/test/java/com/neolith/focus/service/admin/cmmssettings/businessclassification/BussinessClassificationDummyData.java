package com.neolith.focus.service.admin.cmmssettings.businessclassification;

import com.neolith.focus.dto.biz.business.BusinessClassificationDTO;

import java.util.ArrayList;
import java.util.List;

public class BussinessClassificationDummyData {

	private static BussinessClassificationDummyData instance = null;
	
	public static BussinessClassificationDummyData getInstance() {
		if (instance == null) {
			instance = new BussinessClassificationDummyData();
		}
		return instance;
	}
	
	public BusinessClassificationDTO getDummyDTOBussinessClassificationOne() {
		
		BusinessClassificationDTO businessClassificationDTO = new BusinessClassificationDTO();
		businessClassificationDTO.setName("Test Bussiness Classification 1");
			
		return businessClassificationDTO;
}
	
	public BusinessClassificationDTO getDummyDTOBussinessClassificationTwo() {
		
		BusinessClassificationDTO businessClassificationDTO = new BusinessClassificationDTO();
		businessClassificationDTO.setName("Test Bussiness Classification 2");
			
		return businessClassificationDTO;
}
	
	public List<BusinessClassificationDTO> getAllDummyData(){
		List<BusinessClassificationDTO> list = new ArrayList<>(2);
		
		list.add(getDummyDTOBussinessClassificationOne());
		list.add(getDummyDTOBussinessClassificationTwo());
			
		return list;
		
	}
}
