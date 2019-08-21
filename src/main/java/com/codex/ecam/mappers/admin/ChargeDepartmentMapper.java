package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.admin.ChargeDepartmentDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.ChargeDepartment;

public class ChargeDepartmentMapper extends GenericMapper<ChargeDepartment, ChargeDepartmentDTO> {

	private static ChargeDepartmentMapper instance = null;

	private  ChargeDepartmentMapper() {
	}

	public static ChargeDepartmentMapper getInstance() {
		if (instance == null) {
			instance = new ChargeDepartmentMapper();
		}
		return instance;
	}
	@Override
	public ChargeDepartmentDTO domainToDto(ChargeDepartment  domain) throws Exception {
		ChargeDepartmentDTO dto = new ChargeDepartmentDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());

		if (domain.getBusiness() != null) {
			dto.setBusinessId(domain.getBusiness().getId());
		}

		setCommanDTOFields(dto, domain);

		return dto;
	}

	@Override
	public void dtoToDomain(ChargeDepartmentDTO dto, ChargeDepartment domain) throws Exception {
		domain.setId(dto.getId());
		domain.setCode(dto.getCode());
		domain.setDescription(dto.getDescription());

		setCommanDomainFields(dto, domain);
	}

	@Override
	public ChargeDepartmentDTO domainToDtoForDataTable(ChargeDepartment domain) throws Exception {
		ChargeDepartmentDTO dto = new ChargeDepartmentDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setDescription(domain.getDescription());

		if (domain.getBusiness() != null) {
			dto.setBusinessName(domain.getBusiness().getName());
		}
		return dto;
	}

}
