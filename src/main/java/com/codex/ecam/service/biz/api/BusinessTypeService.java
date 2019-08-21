package com.codex.ecam.service.biz.api;

import java.util.List;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.biz.business.BussinessTypeDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.BusinessTypeResult;

public interface BusinessTypeService {

	DataTablesOutput<BussinessTypeDTO> findAll(FocusDataTablesInput input) throws Exception;

	BussinessTypeDTO findById(Integer id) throws Exception;

	BusinessTypeResult delete(Integer id);

	BusinessTypeResult save(BussinessTypeDTO bussinessTypeDTO) throws Exception;

	List<BussinessTypeDTO> findAll();

	void saveAll(List<BussinessTypeDTO> allDummyData);

	void deleteAll();

}
