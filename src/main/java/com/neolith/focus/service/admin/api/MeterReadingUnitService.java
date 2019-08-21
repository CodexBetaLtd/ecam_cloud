package com.neolith.focus.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.admin.MeterReadingUnitDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.MeterReadingUnitResult;

public interface MeterReadingUnitService {

	DataTablesOutput<MeterReadingUnitDTO> findAll(FocusDataTablesInput input) throws Exception;

	MeterReadingUnitDTO findById(Integer id) throws Exception;

	MeterReadingUnitResult delete(Integer id);

	MeterReadingUnitResult save(MeterReadingUnitDTO meterReadingUnitsDTO) throws Exception;

	void saveAll(List<MeterReadingUnitDTO> allDummyData);

	List<MeterReadingUnitDTO> findAllMeterReadings();

	void deleteAll();

}
