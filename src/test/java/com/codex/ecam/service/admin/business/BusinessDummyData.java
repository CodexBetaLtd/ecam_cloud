package com.codex.ecam.service.admin.business;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.biz.business.BusinessDTO;

public class BusinessDummyData {
	
	private static BusinessDummyData instance = null;
	
	private BusinessDummyData(){
	}
	
	public static BusinessDummyData getInstance() {
		if (instance == null) {
			instance = new BusinessDummyData();
		}
		return instance;
	}
	
	public BusinessDTO getDummyDTOBusinessOne(){
		BusinessDTO businessDto = new BusinessDTO();
		
		businessDto.setName("Business Test_1");
		businessDto.setCode("B_00003_1");
		
		businessDto.setPhone("0178565656");
		businessDto.setPhone2("0123456789");
		businessDto.setFax("0332544855");
		businessDto.setWebSite("www.google.lk");
		businessDto.setPrimaryEmail("sss@sss.com");
		businessDto.setSecondaryEmail("nnnn@nnn.com");
		businessDto.setNotes("sdadasdasd sdca sdadsi asdo aspdias[d asda");
		
		businessDto.setBusinessClassficationId(1);
		businessDto.setCurrencyId(1);
		
		businessDto.setAddress("Koswaththa, Udagaladeniya, Rambukkana");
		businessDto.setCity("Kegalle");
		businessDto.setProvince("Sambaragamuwa");
		businessDto.setPostalCode("71100");
		businessDto.setCountryId(1);
		
		return businessDto;
	}
	
	public BusinessDTO getDummyDTOBusinessTwo(){
		BusinessDTO businessDto = new BusinessDTO();
		
		businessDto.setName("Business Test_2");
		businessDto.setCode("B_00003_2");
		
		businessDto.setPhone("0178565656");
		businessDto.setPhone2("0123456789");
		businessDto.setFax("0332544855");
		businessDto.setWebSite("www.google.lk");
		businessDto.setPrimaryEmail("sss@sss.com");
		businessDto.setSecondaryEmail("nnnn@nnn.com");
		businessDto.setNotes("sdadasdasd sdca sdadsi asdo aspdias[d asda");
		
		businessDto.setBusinessClassficationId(1);
		businessDto.setCurrencyId(1);
		
		businessDto.setAddress("Koswaththa, Udagaladeniya, Rambukkana");
		businessDto.setCity("Kegalle");
		businessDto.setProvince("Sambaragamuwa");
		businessDto.setPostalCode("71100");
		businessDto.setCountryId(1);
		
		return businessDto;
	}
	
	public List<BusinessDTO> getAllDummyData(){
		List<BusinessDTO> list = new ArrayList<>(2);
		
		list.add(getDummyDTOBusinessOne());
		list.add(getDummyDTOBusinessTwo());
			
		return list;
		
	}

}
