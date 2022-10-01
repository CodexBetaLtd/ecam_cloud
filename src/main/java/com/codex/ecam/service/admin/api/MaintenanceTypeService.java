package com.codex.ecam.service.admin.api;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.MaintenanceTypeDTO;
import com.codex.ecam.model.maintenance.MaintenanceType;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.MaintenanceTypeResult;


public interface MaintenanceTypeService {

	DataTablesOutput<MaintenanceTypeDTO> findAll(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<MaintenanceTypeDTO> findByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception;

	MaintenanceTypeDTO findById(Integer id) throws Exception;

	MaintenanceTypeResult delete(Integer id);

	MaintenanceTypeResult save(MaintenanceTypeDTO maintenanceTypesDTO) throws Exception;

	void saveAll(List<MaintenanceTypeDTO> allDummyData);

	void deleteAll();

	List<MaintenanceTypeDTO> findAll();

	MaintenanceType findEntityById(Integer id);

	List<MaintenanceTypeDTO> findAllByBusiness(Integer id);

	MaintenanceTypeResult deleteMultiple(Integer[] ids) throws Exception;

}
