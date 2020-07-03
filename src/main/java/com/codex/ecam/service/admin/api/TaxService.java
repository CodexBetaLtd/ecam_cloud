package com.codex.ecam.service.admin.api;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.cmmssetting.tax.TaxDTO;
import com.codex.ecam.model.admin.Tax;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.TaxResult;

public interface TaxService {

	DataTablesOutput<TaxDTO> findAll(FocusDataTablesInput input) throws Exception;

	TaxDTO findById(Integer id) throws Exception;

	TaxResult delete (Integer id);

	TaxResult save(TaxDTO taxDTO) throws Exception;

	Tax findEntityById(Integer id) throws Exception;
	
	List<TaxDTO> findAll();

	List<TaxDTO> findAllByBusiness(Integer id);

}
