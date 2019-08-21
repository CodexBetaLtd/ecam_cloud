package com.neolith.focus.mappers.inventory;

import com.neolith.focus.dto.inventory.bom.BOMGroupPartDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.inventory.bom.BOMGroupPart;

public class BOMGroupPartMapper extends GenericMapper<BOMGroupPart, BOMGroupPartDTO> {

    private static BOMGroupPartMapper instance = null;

    private BOMGroupPartMapper() {
    }

    public static BOMGroupPartMapper getInstance() {
        if (instance == null) {
            instance = new BOMGroupPartMapper();
        }
        return instance;
    }

	@Override
	public BOMGroupPartDTO domainToDto(BOMGroupPart domain) throws Exception {
		BOMGroupPartDTO dto = new BOMGroupPartDTO();
        dto.setId(domain.getId());
        dto.setIsDeleted(domain.getIsDeleted());
        dto.setVersion(domain.getVersion());
        
        if ( domain.getPart() != null ) {
        	dto.setPartId(domain.getPart().getId());
        	dto.setPartName(domain.getPart().getName());
        }
        
        if ( domain.getBomGroup() != null ) {
        	dto.setBomGroupId(domain.getBomGroup().getId());
        	dto.setBomGroupName(domain.getBomGroup().getName());
        }
        
        return dto;
	}

	@Override
	public void dtoToDomain(BOMGroupPartDTO dto, BOMGroupPart domain) throws Exception {
		domain.setId(dto.getId());
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setVersion(dto.getVersion());
	}

    @Override
    public BOMGroupPartDTO domainToDtoForDataTable(BOMGroupPart domain) throws Exception {
		BOMGroupPartDTO dto = new BOMGroupPartDTO();

        if ( domain.getPart() != null ) {
         	dto.setPartName(domain.getPart().getName());
        }
        
        if ( domain.getBomGroup() != null ) {
        	dto.setBomGroupName(domain.getBomGroup().getName());
        }
        return dto;
    }
}
