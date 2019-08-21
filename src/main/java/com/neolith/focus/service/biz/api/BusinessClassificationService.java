package com.neolith.focus.service.biz.api;

import java.util.List;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.biz.business.BusinessClassificationDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.BusinessClassificationResult;

public interface BusinessClassificationService {

	List<BusinessClassificationDTO> findAll();

	DataTablesOutput<BusinessClassificationDTO> findAllDataTable(FocusDataTablesInput input) throws Exception;

	BusinessClassificationDTO findById(Integer id) throws Exception;

	BusinessClassificationResult delete(Integer id);

	BusinessClassificationResult save(BusinessClassificationDTO businessClassificationDTO) throws Exception;

	void saveAll(List<BusinessClassificationDTO> allDummyData);

	void deleteAll();




}
