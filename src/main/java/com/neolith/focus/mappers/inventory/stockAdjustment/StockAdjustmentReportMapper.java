package com.neolith.focus.mappers.inventory.stockAdjustment;

import com.neolith.focus.dto.inventory.stockAdjuestment.StockAdjustmentRepDTO;
import com.neolith.focus.mappers.GenericReportMapper;
import com.neolith.focus.model.inventory.stockAdjustment.StockAdjustment;

public class StockAdjustmentReportMapper extends GenericReportMapper<StockAdjustment, StockAdjustmentRepDTO> {

    private static StockAdjustmentReportMapper instance = null;

    private StockAdjustmentReportMapper() {
    }

    public static StockAdjustmentReportMapper getInstance() {
        if (instance == null) {
            instance = new StockAdjustmentReportMapper();
        }
        return instance;
    }

    @Override
    public StockAdjustmentRepDTO domainToRepDTO(StockAdjustment domain) throws Exception {
        StockAdjustmentRepDTO repDTO = new StockAdjustmentRepDTO();
        repDTO.setAdjustmentCode(domain.getAdjustmentCode());
        repDTO.setDate(domain.getStockAdjustmentDate());
        repDTO.setDescription(domain.getDescription());
        repDTO.setLastQuantity(domain.getLastQuantity());
        repDTO.setNewQuantity(domain.getNewQuantity());
        if (domain.getStockAdjustmentStatus() != null) {
            repDTO.setStatus(domain.getStockAdjustmentStatus());
            repDTO.setStatusId(domain.getStockAdjustmentStatus().getId());
            repDTO.setStatusName(domain.getStockAdjustmentStatus().getName());
        }
        if (domain.getStock() != null && domain.getStock().getId() != null)
//            repDTO.setStockNo(domain.getStock().getStockNo());
            if (domain.getPart() != null && domain.getPart().getId() != null)
                repDTO.setPartName(domain.getPart().getName());
        return repDTO;
    }


}
