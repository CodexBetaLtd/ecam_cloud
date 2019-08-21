package com.codex.ecam.service.admin.cmmssettings.certification;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.admin.CertificationDTO;

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
