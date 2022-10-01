package com.codex.ecam.mappers.inventory.stock;

import com.codex.ecam.dto.inventory.stock.StockLedgerDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.stock.StockLog;

public class StockLedgerMapper extends GenericMapper<StockLog, StockLedgerDTO> {

    private static StockLedgerMapper instance = null;

    private StockLedgerMapper() {
    }

    public static StockLedgerMapper getInstance() {
        if (instance == null) {
            instance = new StockLedgerMapper();
        }
        return instance;
    }

    @Override
    public StockLedgerDTO domainToDto(StockLog domain) throws Exception {
    	StockLedgerDTO dto = new StockLedgerDTO();
        dto.setId(domain.getId());
        dto.setQuantity(domain.getQuantity());
        dto.setBeforeQuantity(domain.getBeforeQuantity());
        dto.setAfterQuantity(domain.getAfterQuantity());
      // dto.setLastPrice(domain.getLastPrice());
       //dto.setDate(domain.getDate());

        if ((domain.getStock() != null) && (domain.getStock().getId() != null)) {
            dto.setBatchNo(domain.getStock().getBatchNo());
            dto.setDate(domain.getStock().getDate());
        	dto.setStockNo(domain.getStock().getStockNo());
        	if(domain.getStock().getWarehouse() != null){
            	dto.setWarehouseName(domain.getStock().getWarehouse().getName());
        	}
            if (domain.getStock().getBusiness() != null) {
                dto.setBusinessName(domain.getStock().getBusiness().getName());
            }
            if (domain.getStock().getPart() != null) {
                dto.setItemName(domain.getStock().getPart().getName());
            }
            dto.setLastPrice(domain.getStock().getSellingPrice());
        }
        
        return dto;
    }

    @Override
    public void dtoToDomain(StockLedgerDTO dto, StockLog domain) throws Exception {
        domain.setId(dto.getId());
      //  domain.setDescription(dto.getDescription());
        domain.setQuantity(dto.getQuantity());
        domain.setBeforeQuantity(dto.getBeforeQuantity());
        domain.setAfterQuantity(dto.getAfterQuantity());
     //   domain.setLastPrice(dto.getLastPrice());
        domain.setIsDeleted(dto.getIsDeleted());
        domain.setVersion(dto.getVersion());
    }

    @Override
    public StockLedgerDTO domainToDtoForDataTable(StockLog domain) throws Exception {
    	StockLedgerDTO dto = new StockLedgerDTO();
        dto.setId(domain.getId());
        dto.setQuantity(domain.getQuantity());
        dto.setBeforeQuantity(domain.getBeforeQuantity());
        dto.setAfterQuantity(domain.getAfterQuantity());
      // dto.setLastPrice(domain.getLastPrice());
       //dto.setDate(domain.getDate());
        
        if((domain.getStock() != null) && (domain.getStock().getId() != null) ){
        	dto.setBatchNo(domain.getStock().getBatchNo());
        	dto.setDate(domain.getStock().getDate());
        	dto.setStockNo(domain.getStock().getStockNo());
        	if(domain.getStock().getWarehouse() != null){
            	dto.setWarehouseName(domain.getStock().getWarehouse().getName());
        	}
        	if(domain.getStock().getBusiness() != null){
        	dto.setBusinessName(domain.getStock().getBusiness().getName());
        	}
        	if(domain.getStock().getPart() != null){
        	dto.setItemName(domain.getStock().getPart().getName());
        	}
        	dto.setLastPrice(domain.getStock().getSellingPrice());
        }
        return dto;
}
}

