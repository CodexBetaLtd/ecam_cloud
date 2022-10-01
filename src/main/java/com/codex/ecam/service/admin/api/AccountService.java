package com.codex.ecam.service.admin.api;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.AccountDTO;
import com.codex.ecam.model.maintenance.Account;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AccountResult;

public interface AccountService {

	DataTablesOutput<AccountDTO> findAll(FocusDataTablesInput input) throws Exception;

	AccountDTO findById(Integer id) throws Exception;

	AccountResult delete (Integer id);

	AccountResult save(AccountDTO accountDTO) throws Exception;

	void saveAll(List<AccountDTO> list);

	void deleteAll();

	Account findEntityById(Integer id) throws Exception;

	List<AccountDTO> findAll();

	List<AccountDTO> findAllByBusiness(Integer id);

	AccountResult deleteMultiple(Integer[] ids) throws Exception;

}
