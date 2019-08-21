package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.admin.AccountDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.Account;

public class AccountMapper extends GenericMapper<Account, AccountDTO> {

	private static AccountMapper instance = null;

	private  AccountMapper() {
	}

	public static AccountMapper getInstance() {
		if (instance == null) {
			instance = new AccountMapper();
		}
		return instance;
	}

	@Override
	public AccountDTO domainToDto(Account  domain) throws Exception {
		AccountDTO dto = new AccountDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());

		if ( domain.getBusiness() != null ) {
			dto.setBusinessId(domain.getBusiness().getId());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(AccountDTO dto, Account domain) throws Exception {
		domain.setId(dto.getId());
		domain.setCode(dto.getCode());
		domain.setDescription(dto.getDescription());
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setVersion(dto.getVersion());
	}

	@Override
	public AccountDTO domainToDtoForDataTable(Account domain) throws Exception {
		AccountDTO dto = new AccountDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());
		
		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}

		return dto;
	}

}
