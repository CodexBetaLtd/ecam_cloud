package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.admin.CountryDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.admin.Country;

public class CountryMapper extends GenericMapper<Country, CountryDTO> {
	
	private static CountryMapper instance = null;
	
	private CountryMapper(){
	}
	
	public static CountryMapper getInstance() {
		if (instance == null) {
			instance = new CountryMapper();
		}
		return instance;
	}

	@Override
	public CountryDTO domainToDto(Country domain) throws Exception {
		CountryDTO dto = new CountryDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setShortName(domain.getShortName());
		dto.setVersion(domain.getVersion());
		
		return dto;
	}

	@Override
	public void dtoToDomain(CountryDTO dto, Country domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setShortName(dto.getShortName());
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setVersion(dto.getVersion());
	}

    @Override
    public CountryDTO domainToDtoForDataTable(Country domain) throws Exception {
		CountryDTO dto = new CountryDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setShortName(domain.getShortName());
        return dto;
    }

}
