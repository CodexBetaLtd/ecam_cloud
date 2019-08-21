package com.neolith.focus.service.admin.cmmssettings.priorities;

import com.neolith.focus.dto.admin.PriorityDTO;

import java.util.ArrayList;
import java.util.List;

public class PrioritiesDummyData {

	private static PrioritiesDummyData instance = null;
	
	public static PrioritiesDummyData getInstance() {
		if (instance == null) {
			instance = new PrioritiesDummyData();
		}
		return instance;
	}

    public PriorityDTO getDummyDTOPrioritiesOne() {

        PriorityDTO prioritiesDTO = new PriorityDTO();
        prioritiesDTO.setName("Test Priority 1");
        prioritiesDTO.setDescription("Description 1");
		prioritiesDTO.setColor("color1");
				
		return prioritiesDTO;
}

    public PriorityDTO getDummyDTOPrioritiesTwo() {
        PriorityDTO prioritiesDTO = new PriorityDTO();
        prioritiesDTO.setName("Test Priority 2");
        prioritiesDTO.setDescription("Description 2");
		prioritiesDTO.setColor("color2");
				
		return prioritiesDTO;

}

    public List<PriorityDTO> getAllDummyData() {
        List<PriorityDTO> list = new ArrayList<>(2);

        list.add(getDummyDTOPrioritiesOne());
        list.add( getDummyDTOPrioritiesTwo() );
			
		return list;
		
	}
}
