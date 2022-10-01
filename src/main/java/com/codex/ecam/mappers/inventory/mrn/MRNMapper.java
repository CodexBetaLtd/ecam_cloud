package com.codex.ecam.mappers.inventory.mrn;

import com.codex.ecam.dto.inventory.mrn.MRNDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.mrn.MRN;

public class MRNMapper extends GenericMapper<MRN, MRNDTO> {

    private static MRNMapper instance = null;

    private MRNMapper() { }

    public static MRNMapper getInstance() {
        if (instance == null) {
            instance = new MRNMapper();
        }
        return instance;
    }

    @Override
    public MRNDTO domainToDto(MRN domain) throws Exception {
    	MRNDTO dto = new MRNDTO();
  
    	dto.setId(domain.getId());
        dto.setVersion(domain.getVersion());
        dto.setMrnNo(domain.getMrnNo()); 
        dto.setDate(domain.getDate());
        
        if (domain.getCustomerContactPerson() != null) {
            dto.setMrnContactPerson(domain.getCustomerContactPerson());
        }
        if (domain.getBusiness() != null && domain.getBusiness().getId() > 0) {
            dto.setBusinessId(domain.getBusiness().getId());
            dto.setBusinessName(domain.getBusiness().getName());
        }
        if (domain.getSite() != null && domain.getSite().getId() > 0) {
            dto.setSiteId(domain.getSite().getId());
            dto.setSiteName(domain.getSite().getName());
        }
        if (domain.getMrnStatus() != null) {
            dto.setStatusId(domain.getMrnStatus().getId());
            dto.setStatusName(domain.getMrnStatus().getName());
            dto.setMrnStatus(domain.getMrnStatus());
        }
        if (domain.getMrnType() != null && domain.getMrnType().getId() != null) {
            dto.setMrnType(domain.getMrnType());
          //  dto.setAodTypeName(domain.getMrnType().getName());
           // dto.setAodType(domain.getMrnType());
        }

        if (domain.getRequestedBy() != null && domain.getRequestedBy().getId() != null) {
            dto.setRequestedUserId(domain.getRequestedBy().getId());
            dto.setRequestedUserName(domain.getRequestedBy().getFullName());
        }
        if (domain.getMrnItems() != null && domain.getMrnItems().size() > 0) {
            dto.setMrnItemDTOs(MRNItemMapper.getInstance().domainToDTOList(domain.getMrnItems()));
        } 
        if (domain.getMrnType() != null) {
            dto.setMrnType(domain.getMrnType());
        }
        
        if (domain.getWorkOrder() != null && domain.getWorkOrder().getId() != null) {
            dto.setWoNo(domain.getWorkOrder().getCode());
            dto.setWoId(domain.getWorkOrder().getId());
        }

        setCommanDTOFields(dto, domain);
        return dto;
    }

    @Override
    public void dtoToDomain(MRNDTO dto, MRN domain) throws Exception {
    	domain.setMrnNo(dto.getMrnNo());
    	domain.setMrnStatus(dto.getMrnStatus());
    	domain.setCustomerContactPerson(dto.getMrnContactPerson());
    	domain.setMrnType(dto.getMrnType());
    	domain.setDate(dto.getDate());
        setCommanDomainFields(dto, domain);
    }

    @Override
    public MRNDTO domainToDtoForDataTable(MRN domain) throws Exception {
    	
    	MRNDTO dto = new MRNDTO();
    	dto.setId(domain.getId());
        dto.setVersion(domain.getVersion());
        dto.setMrnNo(domain.getMrnNo());
        dto.setMrnStatus(domain.getMrnStatus());
        dto.setMrnType(domain.getMrnType());
        dto.setDate(domain.getDate());
        return dto;
    }

}
