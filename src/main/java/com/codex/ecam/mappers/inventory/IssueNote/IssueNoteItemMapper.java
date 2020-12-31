package com.codex.ecam.mappers.inventory.IssueNote;

import com.codex.ecam.dto.inventory.issuenote.IssueNoteItemDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.aod.AODItem;

public class IssueNoteItemMapper extends GenericMapper<AODItem, IssueNoteItemDTO> {

    private static IssueNoteItemMapper instance = null;

    private IssueNoteItemMapper() {
    }

    public static IssueNoteItemMapper getInstance() {
        if (instance == null) {
            instance = new IssueNoteItemMapper();
        }
        return instance;
    }

    @Override
    public IssueNoteItemDTO domainToDto(AODItem domain) throws Exception {
        IssueNoteItemDTO dto = new IssueNoteItemDTO();
        dto.setId(domain.getId());
        dto.setItemQuantity(domain.getQuantity());
        dto.setItemReturnQuantity(domain.getReturnQuantity());
        dto.setDescription(domain.getDescription());
        dto.setItemCost(domain.getItemCost()); 
        
        if (domain.getPart() != null && domain.getPart().getId() != null) {
            dto.setPartId(domain.getPart().getId());
            dto.setPartName(domain.getPart().getName());
        }
        
        if(domain.getAod()!=null && domain.getAod().getId()!=null){
        	dto.setIssueNoteId(domain.getAod().getId());
        	dto.setIssueNoteNo(domain.getAod().getAodNo());
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
    public void dtoToDomain(IssueNoteItemDTO dto, AODItem domain) throws Exception {
        domain.setId(dto.getId());
        domain.setIsDeleted(dto.getIsDeleted());
    }


    @Override
    public IssueNoteItemDTO domainToDtoForDataTable(AODItem domain) throws Exception {
    	IssueNoteItemDTO dto = new IssueNoteItemDTO();
        dto.setId(domain.getId());
        dto.setItemQuantity(domain.getQuantity());
        dto.setItemReturnQuantity(domain.getReturnQuantity());
        if(domain.getQuantity()!=null && domain.getReturnQuantity()!=null){
            dto.setRemainingQuantity(domain.getQuantity().subtract(domain.getReturnQuantity()));
        }
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

