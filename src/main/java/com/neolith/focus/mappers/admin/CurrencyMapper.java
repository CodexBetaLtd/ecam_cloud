package com.neolith.focus.mappers.admin;

import com.neolith.focus.dto.admin.CurrencyDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.admin.Currency;

public class CurrencyMapper extends GenericMapper<Currency, CurrencyDTO>{
	
	private static  CurrencyMapper instance = null;
	
	private  CurrencyMapper() {
		// TODO Auto-generated constructor stub
	}
	
	public static CurrencyMapper getInstance() {
		if (instance == null) {
			instance = new CurrencyMapper();
		}
		return instance;
	}
	
	@Override
	public CurrencyDTO domainToDto(Currency domain) throws Exception {
		CurrencyDTO dto = new CurrencyDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setSymbol(domain.getSymbol());
		dto.setVersion(domain.getVersion());
		
		return dto;
	}

	@Override
	public void dtoToDomain(CurrencyDTO dto, Currency domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setSymbol(dto.getSymbol());
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setVersion(dto.getVersion());
		
	}

    @Override
    public CurrencyDTO domainToDtoForDataTable(Currency domain) throws Exception {
		CurrencyDTO dto = new CurrencyDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setSymbol(domain.getSymbol());
		
        return dto;
    }

}
