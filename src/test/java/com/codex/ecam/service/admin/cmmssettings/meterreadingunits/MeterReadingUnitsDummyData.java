package com.codex.ecam.service.admin.cmmssettings.meterreadingunits;

import org.springframework.stereotype.Component;

import com.codex.ecam.dto.admin.MeterReadingUnitDTO;

import java.util.ArrayList;
import java.util.List;
@Component
public class MeterReadingUnitsDummyData {

	private static MeterReadingUnitsDummyData instance = null;
	
	public static MeterReadingUnitsDummyData getInstance() {
		if (instance == null) {
			instance = new MeterReadingUnitsDummyData();
		}
		return instance;
	}

    public MeterReadingUnitDTO getDummyDTOMeterReadingUnitseOne() {

        MeterReadingUnitDTO meterReadingUnitsDTO = new MeterReadingUnitDTO();
        meterReadingUnitsDTO.setName("Test Meter Reading Units 1");
        meterReadingUnitsDTO.setPrecision(1.0);
		meterReadingUnitsDTO.setSymbol("symbol1");
				
		return meterReadingUnitsDTO;
}

    public MeterReadingUnitDTO getDummyDTOMeterReadingUnitsTwo() {

        MeterReadingUnitDTO meterReadingUnitsDTO = new MeterReadingUnitDTO();
        meterReadingUnitsDTO.setName("Test Meter Reading Units 2");
        meterReadingUnitsDTO.setPrecision(2.0);
		meterReadingUnitsDTO.setSymbol("symbol2");
		
				
		return meterReadingUnitsDTO;
}

    public List<MeterReadingUnitDTO> getAllDummyData() {
        List<MeterReadingUnitDTO> list = new ArrayList<>(2);

        list.add(getDummyDTOMeterReadingUnitseOne());
        list.add( getDummyDTOMeterReadingUnitsTwo());
			
		return list;
		
	}
}
