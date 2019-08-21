package com.neolith.focus.service.asset.api;

import com.neolith.focus.dto.asset.AssetCustomerDTO;
import com.neolith.focus.dto.asset.CustomerDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.asset.CustomerResult;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface CustomerService {

	DataTablesOutput<AssetCustomerDTO> findByAsset(FocusDataTablesInput input, Integer assetId) throws Exception;
	
	DataTablesOutput<CustomerDTO> findAll(FocusDataTablesInput input) throws Exception;

    DataTablesOutput<CustomerDTO> findAllByBusiness(FocusDataTablesInput dataTablesInput, Integer id) throws Exception;

	CustomerDTO findById(Integer id);

	CustomerResult delete(Integer id);

	CustomerResult save(CustomerDTO customer) throws Exception;

}
