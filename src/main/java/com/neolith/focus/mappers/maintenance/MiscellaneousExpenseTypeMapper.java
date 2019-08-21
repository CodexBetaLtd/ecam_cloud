package com.neolith.focus.mappers.maintenance;

import com.neolith.focus.dto.maintenance.miscellaneousExpense.MiscellaneousExpenseTypeDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.maintenance.miscellaneous.MiscellaneousExpenseType;

public class MiscellaneousExpenseTypeMapper extends GenericMapper<MiscellaneousExpenseType, MiscellaneousExpenseTypeDTO> {

	private static MiscellaneousExpenseTypeMapper instance = null;

	private MiscellaneousExpenseTypeMapper(){
	}

	public static MiscellaneousExpenseTypeMapper getInstance() {
		if (instance == null) {
			instance = new MiscellaneousExpenseTypeMapper();
		}
		return instance;
	}

	@Override
	public MiscellaneousExpenseTypeDTO domainToDto(MiscellaneousExpenseType domain) throws Exception {
		MiscellaneousExpenseTypeDTO dto = new MiscellaneousExpenseTypeDTO();
		dto.setId(domain.getId());
		dto.setType(domain.getType());
		dto.setDescription(domain.getDescription());
		if(domain.getBusiness() != null){
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setBusinessName(domain.getBusiness().getName());
		}
		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(MiscellaneousExpenseTypeDTO dto, MiscellaneousExpenseType domain) throws Exception {
		domain.setId(dto.getId());
		domain.setVersion(dto.getVersion());
		domain.setType(dto.getType());
		domain.setDescription(dto.getDescription());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public MiscellaneousExpenseTypeDTO domainToDtoForDataTable(MiscellaneousExpenseType domain) throws Exception {
		MiscellaneousExpenseTypeDTO dto = new MiscellaneousExpenseTypeDTO();
		dto.setId(domain.getId());
		dto.setType(domain.getType());
		dto.setDescription(domain.getDescription());

		return dto;
	}

}
