package com.codex.ecam.mappers.inventory.aod;

import com.codex.ecam.dto.inventory.aod.AODDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.aod.AOD;

public class AODMapper extends GenericMapper<AOD, AODDTO> {

    private static AODMapper instance = null;

    private AODMapper() { }

    public static AODMapper getInstance() {
        if (instance == null) {
            instance = new AODMapper();
        }
        return instance;
    }

    @Override
    public AODDTO domainToDto(AOD domain) throws Exception {
        AODDTO dto = new AODDTO();
        dto.setId(domain.getId());
        dto.setVersion(domain.getVersion());
        dto.setAodNo(domain.getAodNo()); 
        dto.setDate(domain.getDate());
        
        if (domain.getCustomerContactPerson() != null) {
            dto.setCustomerContactPerson(domain.getCustomerContactPerson());
        }
        if (domain.getBusiness() != null && domain.getBusiness().getId() > 0) {
            dto.setBusinessId(domain.getBusiness().getId());
            dto.setBusinessName(domain.getBusiness().getName());
        }
        if (domain.getSite() != null && domain.getSite().getId() > 0) {
            dto.setSiteId(domain.getSite().getId());
            dto.setSiteName(domain.getSite().getName());
        }
        if (domain.getAodStatus() != null) {
            dto.setStatusId(domain.getAodStatus().getId());
            dto.setStatusName(domain.getAodStatus().getName());
            dto.setAodStatus(domain.getAodStatus());
        }
        if (domain.getAodType() != null && domain.getAodType().getId() != null) {
            dto.setAodTypeId(domain.getAodType().getId());
            dto.setAodTypeName(domain.getAodType().getName());
            dto.setAodType(domain.getAodType());
        }
        if (domain.getCustomer() != null && domain.getCustomer().getId() != null) {
            dto.setAodCustomerId(domain.getCustomer().getId());
            dto.setAodCustomerName(domain.getCustomer().getName());
        }
        if (domain.getRequestedBy() != null && domain.getRequestedBy().getId() != null) {
            dto.setRequestedUserId(domain.getRequestedBy().getId());
            dto.setRequestedUserName(domain.getRequestedBy().getFullName());
        }
        if (domain.getAodItemList() != null && domain.getAodItemList().size() > 0) {
            dto.setAodItemList(AODItemMapper.getInstance().domainToDTOList(domain.getAodItemList()));
        } 
        if (domain.getAodType() != null) {
            dto.setAodType(domain.getAodType());
        }
        
        if (domain.getWorkOrder() != null && domain.getWorkOrder().getId() != null) {
            dto.setWoNo(domain.getWorkOrder().getCode());
            dto.setWoId(domain.getWorkOrder().getId());
        }

        setCommanDTOFields(dto, domain);
        return dto;
    }

    @Override
    public void dtoToDomain(AODDTO dto, AOD domain) throws Exception {
        domain.setAodNo(dto.getAodNo());
        domain.setAodStatus(dto.getAodStatus());
        domain.setDate(dto.getDate()); 
        domain.setCustomerContactPerson(dto.getCustomerContactPerson());
        domain.setAodType(dto.getAodType());
        setCommanDomainFields(dto, domain);
    }

    @Override
    public AODDTO domainToDtoForDataTable(AOD domain) throws Exception {
    	
        AODDTO dto = new AODDTO();
        dto.setId(domain.getId());
        dto.setAodNo(domain.getAodNo());
        dto.setDate(domain.getDate());
        
        if (domain.getAodType() != null && domain.getAodType().getId() != null) {
            dto.setAodTypeId(domain.getAodType().getId());
            dto.setAodTypeName(domain.getAodType().getName());
        }
        if (domain.getAodStatus() != null) {
            dto.setStatusId(domain.getAodStatus().getId());
            dto.setStatusName(domain.getAodStatus().getName());
            dto.setAodStatus(domain.getAodStatus());
        } 
        
        if (domain.getWorkOrder() != null && domain.getWorkOrder().getId() != null) {
            dto.setWoNo(domain.getWorkOrder().getCode());
        }
        
        return dto;
    }

}
