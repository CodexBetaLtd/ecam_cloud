package com.codex.ecam.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.CountryDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.CountryResult;

public interface CountryService {

	DataTablesOutput<CountryDTO> findAll(FocusDataTablesInput input) throws Exception;

	CountryDTO findById(Integer id) throws Exception;

	CountryResult delete(Integer id);

	CountryResult save(CountryDTO countryDTO) throws Exception;

	void saveAll(List<CountryDTO> allDummyData);

	void deleteAll();

	List<CountryDTO> findAll();

	List<CountryDTO> findAllCountries() throws Exception;
}
