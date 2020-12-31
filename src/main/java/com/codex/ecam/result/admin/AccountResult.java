package com.codex.ecam.result.admin;

import com.codex.ecam.dto.admin.AccountDTO;
import com.codex.ecam.model.maintenance.Account;
import com.codex.ecam.result.BaseResult;

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
