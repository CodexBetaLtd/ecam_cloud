package com.neolith.focus.mappers.purchasing;

import com.neolith.focus.dto.inventory.rfq.RFQNotificationDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.inventory.rfq.RFQNotification;

public class RFQNotificationMapper extends GenericMapper<RFQNotification, RFQNotificationDTO> {
	
	private static RFQNotificationMapper instance = null;
	
	private RFQNotificationMapper() {}
	
	public static RFQNotificationMapper getInstance () {
		if (instance == null) {
			instance = new RFQNotificationMapper();
		}
		return instance;
	}

	@Override
	public RFQNotificationDTO domainToDto(RFQNotification domain) throws Exception {
		RFQNotificationDTO dto = new RFQNotificationDTO();
		dto.setId(domain.getId());
		
		if (domain.getUser() != null) {
			dto.setUserId(domain.getUser().getId());
			dto.setUserName(domain.getUser().getFullName());
		}
		
		if (domain.getRfq() != null) {
			//dto.set(domain.getRfq().getId());
		}
		
		dto.setNotifyOnStatusChannged(domain.getNotifyOnStatusChannged());
		
		setCommanDTOFields(dto, domain);
		return dto;
	}

	@Override
	public RFQNotificationDTO domainToDtoForDataTable(RFQNotification domain) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dtoToDomain(RFQNotificationDTO dto, RFQNotification domain) throws Exception {
		 domain.setId(dto.getId());
		 domain.setNotifyOnStatusChannged(dto.getNotifyOnStatusChannged());
		 
		 setCommanDomainFields(dto, domain);
	}

}
