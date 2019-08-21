package com.neolith.focus.service.biz.api;

import com.neolith.focus.dto.biz.business.BusinessDTO;
import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.BusinessResult; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface BusinessService { 
	
	void saveAll(List<BusinessDTO> allDummyData) throws Exception;
	
	void deleteAll();

	List<BusinessDTO> findAll() throws Exception;
	
	List<BusinessDTO> findAllActualBusiness();
	
	List<BusinessDTO> findAllActualBusinessByLevel();
	
	Business findEntityById(Integer id);

	BusinessDTO findById(Integer id) throws Exception;

	BusinessResult delete(Integer id);

	BusinessResult save(BusinessDTO business) throws Exception;

    DataTablesOutput<BusinessDTO> findAll(FocusDataTablesInput input) throws Exception;

    DataTablesOutput<BusinessDTO> findAllByLevel(FocusDataTablesInput input) throws Exception;

    DataTablesOutput<BusinessDTO> findActualBusinesses(FocusDataTablesInput input) throws Exception;

}

