package com.codex.ecam.service.admin.cmmssettings.maintainacetype;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.admin.MaintenanceTypeDTO;

public class MaintainanceTypeDummyData {

	private static MaintainanceTypeDummyData instance = null;
	
	public static MaintainanceTypeDummyData getInstance() {
		if (instance == null) {
			instance = new MaintainanceTypeDummyData();
		}
		return instance;
	}

    public MaintenanceTypeDTO getDummyDTOMaintainanceOne() {

        MaintenanceTypeDTO maintainanceTypesDTO = new MaintenanceTypeDTO();
        maintainanceTypesDTO.setName("Test Maintainance Type 1");
        maintainanceTypesDTO.setDescription("Description 1");
		maintainanceTypesDTO.setColor("Color 1");		
				
		return maintainanceTypesDTO;
}

    public MaintenanceTypeDTO getDummyDTOMaintainanceTwo() {

        MaintenanceTypeDTO maintainanceTypesDTO = new MaintenanceTypeDTO();
        maintainanceTypesDTO.setName("Test Maintainance Type 2");
        maintainanceTypesDTO.setDescription("Description 2");
		maintainanceTypesDTO.setColor("Color 2");		
				
		return maintainanceTypesDTO;
}

    public List<MaintenanceTypeDTO> getAllDummyData() {
        List<MaintenanceTypeDTO> list = new ArrayList<>(2);

        list.add(getDummyDTOMaintainanceOne());
        list.add(getDummyDTOMaintainanceTwo());
			
		return list;
		
	}
}
