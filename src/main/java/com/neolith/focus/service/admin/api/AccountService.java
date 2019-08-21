package com.neolith.focus.service.admin.api;

import java.util.List;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.admin.AccountDTO;
import com.neolith.focus.model.maintenance.Account;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.AccountResult;

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

}
