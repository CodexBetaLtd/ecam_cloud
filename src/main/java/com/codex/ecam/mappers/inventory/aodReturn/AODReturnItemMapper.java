package com.codex.ecam.mappers.inventory.aodReturn;

import com.codex.ecam.dto.inventory.aodReturn.AODReturnItemDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.aodRetun.AODReturnItem;


public class AODReturnItemMapper extends GenericMapper<AODReturnItem, AODReturnItemDTO> {

    private static AODReturnItemMapper instance = null;

    private AODReturnItemMapper() {
    }

    public static AODReturnItemMapper getInstance() {
        if (instance == null) {
            instance = new AODReturnItemMapper();
        }
        return instance;
    }

    @Override
    public AODReturnItemDTO domainToDto(AODReturnItem domain) throws Exception {
        AODReturnItemDTO dto = new AODReturnItemDTO();
        dto.setId(domain.getId());
        dto.setReturnQty(domain.getReturnQty());
        dto.setDescription(domain.getDescription());
        if (domain.getAodItem() != null && domain.getAodItem().getId() != null) {
            dto.setAodItemId(domain.getAodItem().getId());
            if (domain.getAodItem().getPart() != null && domain.getAodItem().getPart().getId() != null) {
                dto.setAodItemPartNo(domain.getAodItem().getPart().getName());
                dto.setAodItemPartId(domain.getAodItem().getPart().getId());
            }
            if (domain.getAodItem().getAod() != null && domain.getAodItem().getAod().getId() != null) {
                dto.setAodId(domain.getAodItem().getAod().getId());
                dto.setAodNo(domain.getAodItem().getAod().getAodNo());
                if (domain.getAodItem().getAod().getCustomer() != null) {
                    dto.setAodCustomerName(domain.getAodItem().getAod().getCustomer().getName());
//                    dto.setAodCustomerAddress(domain.getAodItem().getAod().getCustomer().getAddressLine1() + "," + domain.getAodItem().getAod().getCustomer().getAddressLine2() + "," + domain.getAodItem().getAod().getCustomer().getAddressLine3());
                }
            }

        }
        dto.setIsDeleted(domain.getIsDeleted());
        return dto;
    }

    @Override
    public void dtoToDomain(AODReturnItemDTO dto, AODReturnItem domain) throws Exception {
        domain.setId(dto.getId());
        domain.setReturnQty(dto.getReturnQty());
        domain.setDescription(dto.getDescription());
        domain.setIsDeleted(dto.getIsDeleted());
    }

    @Override
    public AODReturnItemDTO domainToDtoForDataTable(AODReturnItem domain) throws Exception {
        return domainToDto(domain);
    }


}

