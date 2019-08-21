package com.neolith.focus.mappers.biz;

import com.neolith.focus.dto.asset.ContactDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.asset.Contact;

public class ContactMapper extends GenericMapper<Contact, ContactDTO> {

	private static ContactMapper instance = null;

	private ContactMapper() {
	}

	public static ContactMapper getInstance() {
		if (instance == null) {
			instance = new ContactMapper();
		}
		return instance;
	}

	@Override
	public ContactDTO domainToDto(Contact domain) throws Exception {
		ContactDTO dto = new ContactDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDesignation(domain.getDesignation());
		dto.setEmail(domain.getEmail());
		dto.setTelephone(domain.getTelephone());
		dto.setVersion(domain.getVersion());
		return dto;
	}

	@Override
	public ContactDTO domainToDtoForDataTable(Contact domain) throws Exception {
		ContactDTO dto = new ContactDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDesignation(domain.getDesignation());
		dto.setEmail(domain.getEmail());
		dto.setTelephone(domain.getTelephone());
		return dto;
	}

	@Override
	public void dtoToDomain(ContactDTO dto, Contact domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setDesignation(dto.getDesignation());
		domain.setEmail(dto.getEmail());
		domain.setTelephone(dto.getTelephone());		
		domain.setVersion(dto.getVersion());
		domain.setIsDeleted(false);
	}

}
