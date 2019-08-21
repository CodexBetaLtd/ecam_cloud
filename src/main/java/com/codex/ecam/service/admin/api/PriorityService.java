package com.codex.ecam.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.PriorityDTO;
import com.codex.ecam.model.maintenance.Priority;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.PriorityResult;

public interface PriorityService {

	DataTablesOutput<PriorityDTO> findAll(FocusDataTablesInput input) throws Exception;

	PriorityDTO findById(Integer id) throws Exception;

	PriorityResult delete(Integer id);

	PriorityResult save(PriorityDTO prioritiesDTO) throws Exception;

	void saveAll(List<PriorityDTO> allDummyData);

	void deleteAll();

	List<PriorityDTO> findAll();

	Priority findEntityById(Integer id);

	List<PriorityDTO> findAllByBusiness(Integer id);
}
