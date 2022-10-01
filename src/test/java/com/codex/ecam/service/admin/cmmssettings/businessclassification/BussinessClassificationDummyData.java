package com.codex.ecam.service.admin.cmmssettings.businessclassification;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;

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
