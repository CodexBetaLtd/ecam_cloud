package com.neolith.focus.dao.admin;

import com.neolith.focus.model.maintenance.Account;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends FocusDataTableRepository<Account, Integer> {

	Account findById(Integer id);

}
