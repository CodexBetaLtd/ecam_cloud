package com.codex.ecam.service.asset.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.asset.AssetCustomerDTO;
import com.codex.ecam.dto.asset.CustomerDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.asset.CustomerResult;

public interface CustomerService {

	DataTablesOutput<AssetCustomerDTO> findByAsset(FocusDataTablesInput input, Integer assetId) throws Exception;

	DataTablesOutput<CustomerDTO> findAll(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<CustomerDTO> findAllByBusiness(FocusDataTablesInput dataTablesInput, Integer id) throws Exception;

	CustomerDTO findById(Integer id);

	CustomerResult delete(Integer id);

	CustomerResult save(CustomerDTO customer) throws Exception;

	CustomerResult deleteMultiple(Integer[] ids) throws Exception;

}
