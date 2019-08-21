package com.codex.ecam.mappers.inventory.stock;

import com.codex.ecam.dto.inventory.stock.StockNotificationDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.stock.StockNotification;

public class StockNotificationMapper extends GenericMapper<StockNotification, StockNotificationDTO> {
	
	private static StockNotificationMapper instance = null;
	
	private StockNotificationMapper () {}
	
	public static StockNotificationMapper getInstance() {
		if (instance == null) {
			instance = new StockNotificationMapper();
		}
		return instance;
	}

	@Override
	public StockNotificationDTO domainToDto(StockNotification domain) throws Exception {
		StockNotificationDTO dto = new StockNotificationDTO();
		dto.setId(domain.getId());
		
		if (domain.getUser() != null) {
			dto.setUserId(domain.getUser().getId());
			dto.setUserName(domain.getUser().getFullName());
		}
		
		if (domain.getStock() != null) {
			dto.setStockId(domain.getStock().getId());
		}
		
		dto.setNotifyOnStockAdd(domain.getNotifyOnStockAdd());
		dto.setNotifyOnStockRemove(domain.getNotifyOnStockRemove());
		dto.setNotifyOnMinQty(domain.getNotifyOnMinQty());
		
		setCommanDTOFields(dto, domain);
		return dto;
	}

	@Override
	public StockNotificationDTO domainToDtoForDataTable(StockNotification domain) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dtoToDomain(StockNotificationDTO dto, StockNotification domain) throws Exception {
		domain.setId(dto.getId());
		domain.setNotifyOnStockAdd(dto.getNotifyOnStockAdd());
		domain.setNotifyOnStockRemove(dto.getNotifyOnStockRemove());
		domain.setNotifyOnMinQty(dto.getNotifyOnMinQty());
		 
		setCommanDomainFields(dto, domain);
	}
}
