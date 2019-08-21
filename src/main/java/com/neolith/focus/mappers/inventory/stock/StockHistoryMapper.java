package com.neolith.focus.mappers.inventory.stock;

import com.neolith.focus.dto.inventory.stock.StockHistoryDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.inventory.stock.StockHistory;

public class StockHistoryMapper extends GenericMapper<StockHistory, StockHistoryDTO> {

    private static StockHistoryMapper instance = null;

    private StockHistoryMapper() {
    }

    public static StockHistoryMapper getInstance() {
        if (instance == null) {
            instance = new StockHistoryMapper();
        }
        return instance;
    }

    @Override
    public StockHistoryDTO domainToDto(StockHistory domain) throws Exception {
        StockHistoryDTO dto = new StockHistoryDTO();
        dto.setId(domain.getId());
        if (domain.getUser() != null && domain.getUser().getId() != null) {
            dto.setUserName(domain.getUser().getFullName());
        }
        dto.setDescription(domain.getDescription());
        dto.setQuantity(domain.getQuantity());
        dto.setBeforeQuantity(domain.getBeforeQuantity());
        dto.setAfterQuantity(domain.getAfterQuantity());
        dto.setLastPrice(domain.getLastPrice());
        dto.setDate(domain.getDate());
        
        dto.setIsDeleted(domain.getIsDeleted());
        dto.setVersion(domain.getVersion());
        
        return dto;
    }

    @Override
    public void dtoToDomain(StockHistoryDTO dto, StockHistory domain) throws Exception {
        domain.setId(dto.getId());
        domain.setDescription(dto.getDescription());
        domain.setQuantity(dto.getQuantity());
        domain.setBeforeQuantity(dto.getBeforeQuantity());
        domain.setAfterQuantity(dto.getAfterQuantity());
        domain.setLastPrice(dto.getLastPrice());
        domain.setIsDeleted(dto.getIsDeleted());
        domain.setVersion(dto.getVersion());
    }

    @Override
    public StockHistoryDTO domainToDtoForDataTable(StockHistory domain) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}

