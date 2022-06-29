package com.codex.ecam.mappers.inventory.mrnReturn;

import com.codex.ecam.dto.inventory.mrnReturn.MRNReturnItemDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.mrnReturn.MRNReturnItem;

public class MRNReturnItemMapper extends GenericMapper<MRNReturnItem, MRNReturnItemDTO> {

    private static MRNReturnItemMapper instance = null;

    private MRNReturnItemMapper() {
    }

    public static MRNReturnItemMapper getInstance() {
        if (instance == null) {
            instance = new MRNReturnItemMapper();
        }
        return instance;
    }

    @Override
    public MRNReturnItemDTO domainToDto(MRNReturnItem domain) throws Exception {
    	MRNReturnItemDTO dto = new MRNReturnItemDTO();
    	dto.setId(domain.getId());
        dto.setVersion(domain.getVersion());
        dto.setItemQuantity(domain.getMrnItemCurrentQuantity());
        dto.setItemReturnQuantity(domain.getReturnQuantity());
        dto.setDescription(domain.getDescription());
        
        if (domain.getMrnItem()!= null && domain.getMrnItem().getId() != null) {
            dto.setMrnItemId(domain.getMrnItem().getId());
            if(domain.getMrnItem().getPart()!=null){
                dto.setPartName(domain.getMrnItem().getPart().getName());
	
            }
        }

        
        dto.setIsDeleted(domain.getIsDeleted());
        return dto;
    }

    @Override
    public void dtoToDomain(MRNReturnItemDTO dto, MRNReturnItem domain) throws Exception {
        domain.setId(dto.getId());
        domain.setIsDeleted(dto.getIsDeleted());
        domain.setVersion(dto.getVersion());
        domain.setReturnQuantity(dto.getItemReturnQuantity());
        domain.setMrnItemCurrentQuantity(dto.getItemQuantity());
        domain.setDescription(dto.getDescription());
    }


    @Override
    public MRNReturnItemDTO domainToDtoForDataTable(MRNReturnItem domain) throws Exception {
    	MRNReturnItemDTO dto = new MRNReturnItemDTO();

        return dto;
    }


}

