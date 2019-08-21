package com.codex.ecam.mappers.inventory.stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.inventory.stock.StockDTO;
import com.codex.ecam.dto.inventory.stock.StockNotificationDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.model.inventory.stock.StockNotification;

public class StockMapper extends GenericMapper<Stock, StockDTO> {

	private static StockMapper instance = null;

	private StockMapper() {
	}

	public static StockMapper getInstance() {
		if (instance == null) {
			instance = new StockMapper();
		}
		return instance;
	}

	@Override
	public StockDTO domainToDto(Stock domain) throws Exception {
		StockDTO dto = new StockDTO();
		dto.setId(domain.getId());
		dto.setStockNo(domain.getStockNo());
		
		dto.setBatchNo(domain.getBatchNo());
		dto.setBuyingPrice(domain.getBuyingPrice());
		dto.setSellingPrice(domain.getSellingPrice());
		dto.setDescription(domain.getDescription());
		dto.setDate(domain.getDate());

		if ((domain.getBusiness() != null)) {
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setBusinessRef(domain.getBusiness().getName());
		}
		if ((domain.getSite() != null) && (domain.getSite().getId() != null)) {
			dto.setSiteId(domain.getSite().getId());
			dto.setSite(domain.getSite().getName());
		}
		if ((domain.getPart() != null) && (domain.getPart().getId() != null)) {
			dto.setPartId(domain.getPart().getId());
			dto.setPartName(domain.getPart().getName());
		}
		if ((domain.getWarehouse() != null) && (domain.getWarehouse().getId() != null)) {
			dto.setWarehouseId(domain.getWarehouse().getId());
			dto.setWarehouseName(domain.getWarehouse().getName());
		}
		dto.setMinQty(domain.getMinQuantity());
		dto.setQtyOnHand(domain.getCurrentQuantity());

		if ((domain.getStockHistoryList() != null) && (domain.getStockHistoryList().size() > 0)) {
			dto.setStockHistoryDTOs(StockHistoryMapper.getInstance().domainToDTOList(domain.getStockHistoryList()));
		}
		
		setNotifications(dto, domain);
		setCommanDTOFields(dto, domain);
		return dto;
	}

	private void setNotifications(StockDTO dto, Stock domain) throws Exception {
		List<StockNotificationDTO> notifications = new ArrayList<>();
		for (StockNotification stockNotification : domain.getStockNotifications()) {
			StockNotificationDTO stockNotificationDTO = StockNotificationMapper.getInstance().domainToDto(stockNotification);
			notifications.add(stockNotificationDTO);
		}
		dto.setStockNotificationDTOs(notifications);
	}

	@Override
	public void dtoToDomain(StockDTO dto, Stock domain) throws Exception {
		domain.setId(dto.getId());
		domain.setIsDeleted(false);
		domain.setMinQuantity(dto.getMinQty());
        if (dto.getId() == null) {
            domain.setLastQuantity(BigDecimal.ZERO);
        } else {
            domain.setLastQuantity(domain.getCurrentQuantity());
        }
        domain.setCurrentQuantity(dto.getQtyOnHand());
		domain.setBatchNo(dto.getBatchNo());
		domain.setBuyingPrice(dto.getBuyingPrice());
		domain.setSellingPrice(dto.getSellingPrice());
		domain.setDescription(dto.getDescription());
		domain.setDate(dto.getDate());

	}

	@Override
	public StockDTO domainToDtoForDataTable(Stock domain) throws Exception {
		StockDTO dto = new StockDTO();
		dto.setId(domain.getId());
		if ((domain.getBusiness() != null)) {
			dto.setBusinessId(domain.getBusiness().getId());
			dto.setPartId(domain.getPart().getId());
			dto.setPartName(domain.getPart().getName());
			dto.setPartCode(domain.getPart().getCode());
		}
 
		if((domain.getWarehouse() != null) && (domain.getWarehouse().getId() != null)){
			dto.setWarehouseName(domain.getWarehouse().getName());
			dto.setWarehouseId(domain.getWarehouse().getId());
		}
		
		dto.setBatchNo(domain.getBatchNo());
		dto.setMinQty(domain.getMinQuantity()); 
		dto.setQtyOnHand(domain.getCurrentQuantity());
		dto.setDate(domain.getDate());
        dto.setBuyingPrice(domain.getBuyingPrice());
        dto.setSellingPrice(domain.getSellingPrice());
        return dto;
    }

}
