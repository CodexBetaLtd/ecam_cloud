package com.codex.ecam.mappers.inventory.mrn;

import com.codex.ecam.dto.inventory.mrn.MRNItemDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.mrn.MRNItem;

public class MRNItemMapper extends GenericMapper<MRNItem, MRNItemDTO> {

    private static MRNItemMapper instance = null;

    private MRNItemMapper() {
    }

    public static MRNItemMapper getInstance() {
        if (instance == null) {
            instance = new MRNItemMapper();
        }
        return instance;
    }

    @Override
    public MRNItemDTO domainToDto(MRNItem domain) throws Exception {
    	MRNItemDTO dto = new MRNItemDTO();
    	dto.setId(domain.getId());
        dto.setVersion(domain.getVersion());
        dto.setItemQuantity(domain.getQuantity());
    //    dto.setItemReturnQuantity(domain.get());
        dto.setDescription(domain.getDescription());
        dto.setItemCost(domain.getItemCost()); 
        dto.setApprovedQuantity(domain.getApprovedQuantity());
        dto.setRemainingQuantity(domain.getRemainQuantity());

        if (domain.getPart() != null && domain.getPart().getId() != null) {
            dto.setPartId(domain.getPart().getId());
            dto.setPartName(domain.getPart().getName());
        }
/*        if (domain.getWarehouse() != null && domain.getWarehouse().getId() != null) {
            dto.setWarehouseId(domain.getWarehouse().getId());
            dto.setWarehouseName(domain.getWarehouse().getName());
        }*/
        
/*        if (domain.getStock() != null && domain.getStock().getId() != null) {
            dto.setStockId(domain.getStock().getId());
            dto.setStockBatchNo(domain.getStock().getBatchNo());
            dto.setRemainingQuantity(domain.getStock().getCurrentQuantity());
        }*/
        
        dto.setIsDeleted(domain.getIsDeleted());
        return dto;
    }

    @Override
    public void dtoToDomain(MRNItemDTO dto, MRNItem domain) throws Exception {
        domain.setId(dto.getId());
        domain.setIsDeleted(dto.getIsDeleted());
        domain.setVersion(dto.getVersion());
        domain.setQuantity(dto.getItemQuantity());
        domain.setRemainQuantity(dto.getRemainingQuantity());
        domain.setApprovedQuantity(dto.getApprovedQuantity());
        domain.setDescription(dto.getDescription());
        domain.setItemCost(dto.getItemCost());
    }


    @Override
    public MRNItemDTO domainToDtoForDataTable(MRNItem domain) throws Exception {
    	MRNItemDTO dto = new MRNItemDTO();
    	dto.setId(domain.getId());
        dto.setVersion(domain.getVersion());
        dto.setItemQuantity(domain.getQuantity());
      //  dto.setItemReturnQuantity(domain.getRemainQuantity());
        dto.setRemainingQuantity(domain.getRemainQuantity());
        dto.setDescription(domain.getDescription());
        dto.setItemCost(domain.getItemCost()); 
        dto.setApprovedQuantity(domain.getApprovedQuantity());
        if (domain.getPart() != null && domain.getPart().getId() != null) {
            dto.setPartId(domain.getPart().getId());
            dto.setPartName(domain.getPart().getName());
        }
        return dto;
    }


}

