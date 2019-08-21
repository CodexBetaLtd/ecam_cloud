package com.neolith.focus.result.admin;

import com.neolith.focus.dto.admin.AccountDTO;
import com.neolith.focus.model.maintenance.Account;
import com.neolith.focus.result.BaseResult;

public class AccountResult extends BaseResult<Account, AccountDTO>{

	public AccountResult(Account domain, AccountDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}

}
