package com.codex.ecam.service.biz.api;

import java.util.List;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.BusinessClassificationResult;

public interface BusinessClassificationService {

	List<BusinessClassificationDTO> findAll();

	DataTablesOutput<BusinessClassificationDTO> findAllDataTable(FocusDataTablesInput input) throws Exception;

	BusinessClassificationDTO findById(Integer id) throws Exception;

	BusinessClassificationResult delete(Integer id);

	BusinessClassificationResult save(BusinessClassificationDTO businessClassificationDTO) throws Exception;

	void saveAll(List<BusinessClassificationDTO> allDummyData);

	void deleteAll();




}
