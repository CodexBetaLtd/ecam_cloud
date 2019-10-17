package com.codex.ecam.mappers.inventory.mrn;

import com.codex.ecam.dto.inventory.mrn.MRNDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.mappers.inventory.aod.AODItemMapper;
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
/*        dto.setId(domain.getId());
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
            dto.setMrnType(domain.getAodType());
        }
        
        if (domain.getWorkOrder() != null && domain.getWorkOrder().getId() != null) {
            dto.setWoNo(domain.getWorkOrder().getCode());
            dto.setWoId(domain.getWorkOrder().getId());
        }*/
        setCommanDTOFields(dto, domain);
        return dto;
    }

    @Override
    public void dtoToDomain(MRNDTO dto, MRN domain) throws Exception {

        setCommanDomainFields(dto, domain);
    }

    @Override
    public MRNDTO domainToDtoForDataTable(MRN domain) throws Exception {
    	
    	MRNDTO dto = new MRNDTO();

        
        return dto;
    }

}
