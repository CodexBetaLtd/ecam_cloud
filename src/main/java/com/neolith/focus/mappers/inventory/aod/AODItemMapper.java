package com.neolith.focus.mappers.inventory.aod;

import com.neolith.focus.dto.inventory.aod.AODItemDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.inventory.aod.AODItem;

public class AODItemMapper extends GenericMapper<AODItem, AODItemDTO> {

    private static AODItemMapper instance = null;

    private AODItemMapper() {
    }

    public static AODItemMapper getInstance() {
        if (instance == null) {
            instance = new AODItemMapper();
        }
        return instance;
    }

    @Override
    public AODItemDTO domainToDto(AODItem domain) throws Exception {
        AODItemDTO dto = new AODItemDTO();
        dto.setId(domain.getId());
        dto.setItemQuantity(domain.getQuantity());
        dto.setItemReturnQuantity(domain.getReturnQuantity());
        dto.setDescription(domain.getDescription());
        dto.setItemCost(domain.getItemCost()); 
        
        if (domain.getPart() != null && domain.getPart().getId() != null) {
            dto.setPartId(domain.getPart().getId());
            dto.setPartName(domain.getPart().getName());
        }
        if (domain.getWarehouse() != null && domain.getWarehouse().getId() != null) {
            dto.setWarehouseId(domain.getWarehouse().getId());
            dto.setWarehouseName(domain.getWarehouse().getName());
        }
        
        if (domain.getStock() != null && domain.getStock().getId() != null) {
            dto.setStockId(domain.getStock().getId());
            dto.setStockBatchNo(domain.getStock().getBatchNo());
            dto.setRemainingQuantity(domain.getStock().getCurrentQuantity());
        }
        
        dto.setIsDeleted(domain.getIsDeleted());
        return dto;
    }

    @Override
    public void dtoToDomain(AODItemDTO dto, AODItem domain) throws Exception {
        domain.setId(dto.getId());
        domain.setIsDeleted(dto.getIsDeleted());
    }


    @Override
    public AODItemDTO domainToDtoForDataTable(AODItem domain) throws Exception {
        AODItemDTO dto = new AODItemDTO();
        dto.setId(domain.getId());
        dto.setItemQuantity(domain.getQuantity());
        dto.setItemReturnQuantity(domain.getReturnQuantity());
        dto.setRemainingQuantity(domain.getQuantity().subtract(domain.getReturnQuantity()));
        if (domain.getWarehouse() != null && domain.getWarehouse().getId() != null) {
            dto.setWarehouseId(domain.getWarehouse().getId());
            dto.setWarehouseName(domain.getWarehouse().getName());
        }
        if (domain.getPart() != null && domain.getPart().getId() != null) {
            dto.setPartId(domain.getPart().getId());
            dto.setPartName(domain.getPart().getName());
            dto.setDescription(domain.getPart().getDescription());
        }
        dto.setIsDeleted(domain.getIsDeleted());
        return dto;
    }


}

