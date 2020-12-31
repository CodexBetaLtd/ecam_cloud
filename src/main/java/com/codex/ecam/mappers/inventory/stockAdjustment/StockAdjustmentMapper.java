package com.codex.ecam.mappers.inventory.stockAdjustment;

import com.codex.ecam.dto.inventory.stockAdjuestment.StockAdjustmentDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.stockAdjustment.StockAdjustment;

public class StockAdjustmentMapper extends GenericMapper<StockAdjustment, StockAdjustmentDTO> {

    private static StockAdjustmentMapper instance = null;

    private StockAdjustmentMapper() {
    }

    public static StockAdjustmentMapper getInstance() {
        if (instance == null) {
            instance = new StockAdjustmentMapper();
        }
        return instance;
    }

    @Override
    public StockAdjustmentDTO domainToDto(StockAdjustment domain) throws Exception {
        StockAdjustmentDTO dto = new StockAdjustmentDTO();
        dto.setId(domain.getId());
        dto.setVersion(domain.getVersion());
        dto.setAdjustmentCode(domain.getAdjustmentCode());
        if (domain.getStockAdjustmentStatus() != null) {
            dto.setAdjustmentStatus(domain.getStockAdjustmentStatus());
            dto.setAdjustmentStatusId(domain.getStockAdjustmentStatus().getId());
            dto.setAdjustmentStatusName(domain.getStockAdjustmentStatus().getName());
        }
        if (domain.getPart() != null) {
            dto.setPartId(domain.getPart().getId());
            dto.setPartName(domain.getPart().getName());
        }
        if (domain.getWarehouse() != null) {
            dto.setWarehouseId(domain.getWarehouse().getId());
            dto.setWarehouseName(domain.getWarehouse().getName());
        }
        if (domain.getStock() != null) {
            dto.setStockId(domain.getStock().getId());
           dto.setStockNo(domain.getStock().getBatchNo());
        }
        dto.setLastQuantity(domain.getLastQuantity());
        dto.setNewQuantity(domain.getNewQuantity());
        dto.setDescription(domain.getDescription());
        dto.setStockAdjustmentDate(domain.getStockAdjustmentDate());
        return dto;
    }

    @Override
    public void dtoToDomain(StockAdjustmentDTO dto, StockAdjustment domain) throws Exception {
        domain.setId(dto.getId());
        domain.setVersion(dto.getVersion());
        domain.setStockAdjustmentStatus(dto.getAdjustmentStatus());
        domain.setAdjustmentCode(dto.getAdjustmentCode());
        domain.setDescription(dto.getDescription());
        domain.setLastQuantity(dto.getLastQuantity());
        domain.setNewQuantity(dto.getNewQuantity());
        domain.setIsDeleted(dto.getIsDeleted());
        domain.setStockAdjustmentDate(dto.getStockAdjustmentDate());
    }

    @Override
    public StockAdjustmentDTO domainToDtoForDataTable(StockAdjustment domain) throws Exception {
        StockAdjustmentDTO dto = new StockAdjustmentDTO();
        dto.setId(domain.getId());
        dto.setAdjustmentStatusName(domain.getStockAdjustmentStatus().getName());
        dto.setAdjustmentCode(domain.getAdjustmentCode());
        dto.setPartName(domain.getPart().getName());
        dto.setNewQuantity(domain.getNewQuantity());
        dto.setLastQuantity(domain.getLastQuantity());

        dto.setStockAdjustmentDate(domain.getStockAdjustmentDate());
        return dto;
    }

}
