package com.codex.ecam.service.biz.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.biz.business.BusinessDTO;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.BusinessResult;

import java.util.List;

public interface BusinessService {

	void saveAll(List<BusinessDTO> allDummyData) throws Exception;

	void deleteAll();

	List<BusinessDTO> findAll() throws Exception;

	List<BusinessDTO> findAllActualBusiness();

	List<BusinessDTO> findAllActualBusinessByLevel();
	List<BusinessDTO> findAllByLevelList() throws Exception ;

	Business findEntityById(Integer id);

	BusinessDTO findById(Integer id) throws Exception;

	BusinessResult delete(Integer id);

	BusinessResult save(BusinessDTO business) throws Exception;

	DataTablesOutput<BusinessDTO> findAll(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<BusinessDTO> findAllByLevel(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<BusinessDTO> findActualBusinesses(FocusDataTablesInput input) throws Exception;

	BusinessResult deleteMultiple(Integer[] ids) throws Exception;

}

