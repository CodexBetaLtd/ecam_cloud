package com.codex.ecam.mappers.inventory.part;

import com.codex.ecam.dto.biz.part.PartNotificationDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.biz.part.PartNotification;

public class PartNotificationMapper extends GenericMapper<PartNotification, PartNotificationDTO> {
	
	private static PartNotificationMapper instance = null;
	
	private PartNotificationMapper() {}
	
	public static PartNotificationMapper getInstance () {
		if (instance == null) {
			instance = new PartNotificationMapper();
		}
		return instance;
	}

	@Override
	public PartNotificationDTO domainToDto(PartNotification domain) throws Exception {
		PartNotificationDTO dto = new PartNotificationDTO();
		dto.setId(domain.getId());
		
		if (domain.getUser() != null) {
			dto.setUserId(domain.getUser().getId());
			dto.setUserName(domain.getUser().getFullName());
		}
		
		if (domain.getPart() != null) {
			dto.setPartId(domain.getPart().getId());
		}
		
		dto.setNotifyOnStockAdd(domain.getNotifyOnStockAdd());
		dto.setNotifyOnStockRemove(domain.getNotifyOnStockRemove());
		dto.setNotifyOnMinQty(domain.getNotifyOnMinQty());
		
		setCommanDTOFields(dto, domain);
		return dto;
	}

	@Override
	public PartNotificationDTO domainToDtoForDataTable(PartNotification domain) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dtoToDomain(PartNotificationDTO dto, PartNotification domain) throws Exception {
		 domain.setId(dto.getId());
		 domain.setNotifyOnStockAdd(dto.getNotifyOnStockAdd());
		 domain.setNotifyOnStockRemove(dto.getNotifyOnStockRemove());
		 domain.setNotifyOnMinQty(dto.getNotifyOnMinQty());
		 
		 setCommanDomainFields(dto, domain);
	}

}
