package com.codex.ecam.service.admin.api;

import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.CurrencyDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.CurrencyResult;


public interface CurrencyService {

	DataTablesOutput<CurrencyDTO> findAll(FocusDataTablesInput input) throws Exception;

	CurrencyDTO findById(Integer id) throws Exception;

	CurrencyResult delete(Integer id);

	CurrencyResult save(CurrencyDTO currencyDTO) throws Exception;

	List<CurrencyDTO> findAll();

	void saveAll(List<CurrencyDTO> allDummyData);

	void deleteAll();

	CurrencyResult deleteMultiple(Integer[] ids) throws Exception;

}
