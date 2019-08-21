package com.codex.ecam.mappers.inventory.InventoryGroup;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.inventory.inventoryGroup.InventoryGroupDTO;
import com.codex.ecam.dto.inventory.inventoryGroup.InventoryGroupPartDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.inventoryGroup.InventoryGroup;
import com.codex.ecam.model.inventory.inventoryGroup.InventoryGroupPart;

public class InventoryGroupMapper extends GenericMapper<InventoryGroup, InventoryGroupDTO> {

	private static InventoryGroupMapper instance = null;

	private InventoryGroupMapper() {
	}

	public static InventoryGroupMapper getInstance() {
		if (instance == null) {
			instance = new InventoryGroupMapper();
		}
		return instance;
	}

	@Override
	public InventoryGroupDTO domainToDto(InventoryGroup domain) throws Exception {
		InventoryGroupDTO dto = new InventoryGroupDTO();
		dto.setId(domain.getId());
		dto.setVersion(domain.getVersion());
		if(domain.getBusiness()!=null){
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setBusinessName(domain.getBusiness().getName());
		}
		if(domain.getSite()!=null){
			dto.setSiteId(domain.getSite().getId());
			dto.setSiteName(domain.getSite().getName());
		}
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setInventoryGroupNo(domain.getInventoryGroupNo());
		dto.setIsDeleted(domain.getIsDeleted());
		setPart(dto, domain);
		return dto;
	}

	@Override
	public void dtoToDomain(InventoryGroupDTO dto, InventoryGroup domain) throws Exception {
		domain.setId(dto.getId());
		domain.setVersion(dto.getVersion());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		domain.setInventoryGroupNo(dto.getInventoryGroupNo());
		domain.setIsDeleted(dto.getIsDeleted());
	}

	private void setPart(InventoryGroupDTO dto, InventoryGroup domain){
		if ((domain.getInventoryGroupParts() != null) && (domain.getInventoryGroupParts().size() > 0)) {
			List<InventoryGroupPartDTO> inventoryGroupPartDTOs = new ArrayList<>();
			for (InventoryGroupPart inventoryGroupPart : domain.getInventoryGroupParts()) {
				InventoryGroupPartDTO inventoryGroupPartDTO = new InventoryGroupPartDTO();
				inventoryGroupPartDTO.setId(inventoryGroupPart.getId());
				inventoryGroupPartDTO.setVersion(inventoryGroupPart.getVersion());
				if(inventoryGroupPartDTO!=null){
					inventoryGroupPartDTO.setPartCode(inventoryGroupPart.getPart().getCode());
					inventoryGroupPartDTO.setPartName(inventoryGroupPart.getPart().getName());
					inventoryGroupPartDTO.setPartId(inventoryGroupPart.getPart().getId());
				}
				inventoryGroupPartDTOs.add(inventoryGroupPartDTO);
			}
			dto.setInventoryGroupPartDTOs(inventoryGroupPartDTOs);
		}
	}

	@Override
	public InventoryGroupDTO domainToDtoForDataTable(InventoryGroup domain) throws Exception {
		InventoryGroupDTO dto = new InventoryGroupDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setInventoryGroupNo(domain.getInventoryGroupNo());
		dto.setDescription(domain.getDescription());

		return dto;
	}


}
