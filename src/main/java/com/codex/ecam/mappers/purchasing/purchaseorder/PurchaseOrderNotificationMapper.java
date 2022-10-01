package com.codex.ecam.mappers.purchasing.purchaseorder;

import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderNotificationDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderNotification;

public class PurchaseOrderNotificationMapper extends GenericMapper<PurchaseOrderNotification, PurchaseOrderNotificationDTO> {

    private static PurchaseOrderNotificationMapper instance = null;

    private PurchaseOrderNotificationMapper() {
    }

    public static PurchaseOrderNotificationMapper getInstance() {
        if (instance == null) {
            instance = new PurchaseOrderNotificationMapper();
        }
        return instance;
    }

    @Override
    public PurchaseOrderNotificationDTO domainToDto(PurchaseOrderNotification domain) throws Exception {
        PurchaseOrderNotificationDTO dto = new PurchaseOrderNotificationDTO();
        dto.setId(domain.getId());
        dto.setVersion(domain.getVersion());
        if (domain.getUser() != null && domain.getUser().getId() != null) {
            dto.setUserId(domain.getUser().getId());
            dto.setUserName(domain.getUser().getFullName());
        }
        dto.setNotifyOnAssignment(domain.getNotifyOnAssignment());
        dto.setNotifyOnStatusChange(domain.getNotifyOnStatusChange());
        dto.setNotifyOnCompletion(domain.getNotifyOnCompletion());
        dto.setNotifyOnTaskCompleted(domain.getNotifyOnTaskCompleted());
        dto.setNotifyOnOnlineOffline(domain.getNotifyOnOnlineOffline());
        return dto;
    }

    @Override
    public void dtoToDomain(PurchaseOrderNotificationDTO dto, PurchaseOrderNotification domain) throws Exception {
        domain.setId(dto.getId());
        domain.setVersion(dto.getVersion());
        domain.setNotifyOnAssignment(dto.getNotifyOnAssignment());
        domain.setNotifyOnStatusChange(dto.getNotifyOnStatusChange());
        domain.setNotifyOnCompletion(dto.getNotifyOnCompletion());
        domain.setNotifyOnTaskCompleted(dto.getNotifyOnTaskCompleted());
        domain.setNotifyOnOnlineOffline(dto.getNotifyOnOnlineOffline());
    }

    @Override
    public PurchaseOrderNotificationDTO domainToDtoForDataTable(PurchaseOrderNotification domain) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


}
