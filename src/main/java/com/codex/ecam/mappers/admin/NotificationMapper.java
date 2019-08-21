package com.codex.ecam.mappers.admin;

import com.codex.ecam.dto.biz.notification.NotificationDTO;
import com.codex.ecam.exception.setting.notification.NotificationException;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.biz.notification.Notification;

public class NotificationMapper extends GenericMapper<Notification, NotificationDTO> {
	
	private static NotificationMapper instance = null;
	
	private NotificationMapper(){
	}
	
	public static NotificationMapper getInstance() {
		if (instance == null) {
			instance = new NotificationMapper();
		}
		return instance;
	}


	@Override
	public NotificationDTO domainToDto(Notification domain) throws Exception {
		NotificationDTO dto = new NotificationDTO();
		if(domain != null){
			dto.setId(domain.getId());
			dto.setVersion(domain.getId()); 
			dto.setSubject(domain.getSubject());
			dto.setContent(domain.getContent());
			dto.setIsOpen(domain.getIsOpen());
			dto.setIsPopup(domain.getIsPopup());
            dto.setNotificationType(domain.getNotificationType());
            dto.setNotificationDateTime(domain.getCreatedDate());

            if (domain.getSystemMessage() != null && domain.getSystemMessage()) {
                dto.setSenderName(" System Generated Message ");
                dto.setSystemMessage(Boolean.TRUE);
            } else {
                dto.setSenderName(domain.getSender().getFullName());
                dto.setSystemMessage(Boolean.FALSE);
            }
            if (domain.getReceiver() != null) {
                dto.setReceivedUserId(domain.getReceiver().getId());
                dto.setReceiverName(domain.getReceiver().getFullName());
            }
            if (domain.getSender() != null) {
                dto.setSentUserId(domain.getSender().getId());
                dto.setSenderName(domain.getSender().getFullName());
            }
        }
		return dto;
	}

	@Override
    public void dtoToDomain(NotificationDTO dto, Notification domain) throws NotificationException {
        domain.setId(dto.getId());
        domain.setVersion(dto.getVersion());
        domain.setIsDeleted(dto.getIsDeleted());

        domain.setSubject(dto.getSubject());
        domain.setContent(dto.getContent());
        domain.setIsOpen(dto.getIsOpen());
        domain.setIsPopup(dto.getIsPopup());
        domain.setIsTrashed(dto.getTrash());
        domain.setSystemMessage(dto.getSystemMessage());
    }

    @Override
    public NotificationDTO domainToDtoForDataTable(Notification domain) throws Exception {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(domain.getId());
        dto.setSubject(domain.getSubject());
        dto.setContent(domain.getContent());
        dto.setIsOpen(domain.getIsOpen());
        dto.setIsPopup(domain.getIsPopup());
        dto.setNotificationDateTime(domain.getCreatedDate());
        if (domain.getSystemMessage() != null && domain.getSystemMessage()) {
            dto.setSenderName(" System Generated Message ");
            dto.setSystemMessage(Boolean.TRUE);
        } else {
            dto.setSenderName(domain.getSender().getFullName());
            dto.setSystemMessage(Boolean.FALSE);
        }
        if (domain.getReceiver() != null) {
            dto.setReceivedUserId(domain.getReceiver().getId());
            dto.setReceiverName(domain.getReceiver().getFullName());
        }
        if (domain.getSender() != null) {
            dto.setSentUserId(domain.getSender().getId());
            dto.setSenderName(domain.getSender().getFullName());
        }
        return dto;
    }

}
