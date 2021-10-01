package com.codex.ecam.service.admin.api;

import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.MeterReadingUnitDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.MeterReadingUnitResult;

public interface MeterReadingUnitService {

	DataTablesOutput<MeterReadingUnitDTO> findAll(FocusDataTablesInput input) throws Exception;

	MeterReadingUnitDTO findById(Integer id) throws Exception;

	MeterReadingUnitResult delete(Integer id);

	MeterReadingUnitResult save(MeterReadingUnitDTO meterReadingUnitsDTO) throws Exception;

	void saveAll(List<MeterReadingUnitDTO> allDummyData);

	List<MeterReadingUnitDTO> findAllMeterReadings();

	void deleteAll();

	MeterReadingUnitResult deleteMultiple(Integer[] ids) throws Exception;

}
