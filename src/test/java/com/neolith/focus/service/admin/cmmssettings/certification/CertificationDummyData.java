package com.neolith.focus.service.admin.cmmssettings.certification;

import com.neolith.focus.dto.admin.CertificationDTO;

import java.util.ArrayList;
import java.util.List;

public class CertificationDummyData {

	private static CertificationDummyData instance = null;
	
	public static CertificationDummyData getInstance() {
		if (instance == null) {
			instance = new CertificationDummyData();
		}
		return instance;
	}
	
	public CertificationDTO getDummyDTOCertificationOne() {
		
		CertificationDTO certificationDTO = new CertificationDTO();
		certificationDTO.setCertificationType("Certification type 1");
				
		return certificationDTO;
}
	
	public CertificationDTO getDummyDTOCertificationTwo() {
		
		CertificationDTO certificationDTO = new CertificationDTO();
		certificationDTO.setCertificationType("Certification type 2");	
		
				
		return certificationDTO;
}
	
	public List<CertificationDTO> getAllDummyData(){
		List<CertificationDTO> list = new ArrayList<>(2);
		
		list.add(getDummyDTOCertificationOne());
		list.add(getDummyDTOCertificationTwo());
			
		return list;
		
	}
}
