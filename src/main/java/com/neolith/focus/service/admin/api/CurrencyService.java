package com.neolith.focus.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.admin.CurrencyDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.CurrencyResult;


public interface CurrencyService {

	DataTablesOutput<CurrencyDTO> findAll(FocusDataTablesInput input) throws Exception;

	CurrencyDTO findById(Integer id) throws Exception;

	CurrencyResult delete(Integer id);

	CurrencyResult save(CurrencyDTO currencyDTO) throws Exception;

	List<CurrencyDTO> findAll();

	void saveAll(List<CurrencyDTO> allDummyData);

	void deleteAll();

}
