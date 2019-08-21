package com.codex.ecam.mappers.inventory.aodReturn;

import java.util.Set;

import com.codex.ecam.dto.inventory.aodReturn.AODReturnDTO;
import com.codex.ecam.dto.inventory.aodReturn.AODReturnItemDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.aodRetun.AODReturn;


public class AODReturnMapper extends GenericMapper<AODReturn, AODReturnDTO> {

	private static AODReturnMapper instance = null;

	private AODReturnMapper() {
	}

	public static AODReturnMapper getInstance() {
		if (instance == null) {
			instance = new AODReturnMapper();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AODReturnDTO domainToDto(AODReturn domain) throws Exception {
		AODReturnDTO dto = new AODReturnDTO();
		dto.setId(domain.getId());
		dto.setReturnNo(domain.getReturnNo());
		dto.setReturnRefNo(domain.getReturnRefNo());
		dto.setReturnDate(domain.getReturnDate());
		if (domain.getAodReturnStatus() != null) {
			dto.setSiteId(domain.getAodReturnStatus().getId());
			dto.setStatusName(domain.getAodReturnStatus().getName());
			dto.setAodReturnStatus(domain.getAodReturnStatus());
		}
		if ((domain.getBusiness() != null) && (domain.getBusiness().getId() > 0)) {
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setBusinessName(domain.getBusiness().getName());
		}
		if ((domain.getSite() != null) && (domain.getSite().getId() > 0)) {
			dto.setSiteId(domain.getSite().getId());
			dto.setSiteName(domain.getSite().getName());
		}
		if ((domain.getAod() != null) && (domain.getAod().getId() > 0)) {
			dto.setAodNo(domain.getAod().getAodNo());
			dto.setAodId(domain.getAod().getId());
			if ((domain.getAod().getCustomer() != null) && (domain.getAod().getCustomer().getId() > 0)) {
				dto.setAodCustomerName(domain.getAod().getCustomer().getName());
				//                dto.setAodCustomerAddress(domain.getAod().getCustomer().getAddressLine1()+','+domain.getAod().getCustomer().getAddressLine2()+','+domain.getAod().getCustomer().getAddressLine3());
			}
		}
		//        dto.setDescription("");
		//todo : Change Generic Mapper
		if ((domain.getAodReturnItems() != null) && (domain.getAodReturnItems().size() > 0)) {
			dto.setAodReturnItemList((Set<AODReturnItemDTO>) AODReturnItemMapper.getInstance().domainToDTOList(domain.getAodReturnItems()));
		}
		dto.setIsDeleted(domain.getIsDeleted());
		return dto;
	}

	@Override
	public void dtoToDomain(AODReturnDTO dto, AODReturn domain) throws Exception {
		domain.setReturnNo(dto.getReturnNo());
		domain.setReturnRefNo(dto.getReturnRefNo());
		domain.setReturnDate(dto.getReturnDate());
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setAodReturnStatus(dto.getAodReturnStatus());
	}

	@Override
	public AODReturnDTO domainToDtoForDataTable(AODReturn domain) throws Exception {
		AODReturnDTO dto = new AODReturnDTO();
		dto.setId(domain.getId());
		dto.setReturnNo(domain.getReturnNo());
		dto.setReturnRefNo(domain.getReturnRefNo());
		dto.setReturnDate(domain.getReturnDate());
		if (domain.getAodReturnStatus() != null) {
			dto.setStatusName(domain.getAodReturnStatus().getName());
		}
		return dto;
	}

}

