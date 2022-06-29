package com.codex.ecam.mappers.inventory.mrnReturn;

import com.codex.ecam.dto.inventory.mrnReturn.MRNReturnDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.mrnReturn.MRNReturn;

public class MRNReturnMapper extends GenericMapper<MRNReturn, MRNReturnDTO> {

    private static MRNReturnMapper instance = null;

    private MRNReturnMapper() { }

    public static MRNReturnMapper getInstance() {
        if (instance == null) {
            instance = new MRNReturnMapper();
        }
        return instance;
    }

    @Override
    public MRNReturnDTO domainToDto(MRNReturn domain) throws Exception {
    	MRNReturnDTO dto = new MRNReturnDTO();
  
    	dto.setId(domain.getId());
    	dto.setVersion(domain.getVersion());
        dto.setMrnReturnStatus(domain.getMrnReturnStatus());
        if(domain.getMrn()!=null && domain != null){
        	dto.setMrnNo(domain.getMrn().getMrnNo()); 
            dto.setMrnId(domain.getMrn().getId()); 
        }
        
        dto.setMrnReturnNo(domain.getMrnReturnNo());
        dto.setDate(domain.getDate());  
  
        if (domain.getBusiness() != null && domain.getBusiness().getId() > 0) {
            dto.setBusinessId(domain.getBusiness().getId());
            dto.setBusinessName(domain.getBusiness().getName());
        }
        if (domain.getSite() != null && domain.getSite().getId() > 0) {
            dto.setSiteId(domain.getSite().getId());
            dto.setSiteName(domain.getSite().getName());
        }

        if (domain.getRequestedBy() != null && domain.getRequestedBy().getId() != null) {
            dto.setRequestedUserId(domain.getRequestedBy().getId());
            dto.setRequestedUserName(domain.getRequestedBy().getFullName());
        }

        if (domain.getReturnItems() != null && domain.getReturnItems().size() > 0) {
            dto.setMrnReturnItemDTOs(MRNReturnItemMapper.getInstance().domainToDTOList(domain.getReturnItems()));
        } 
        setCommanDTOFields(dto, domain);
        return dto;
    }

    @Override
    public void dtoToDomain(MRNReturnDTO dto, MRNReturn domain) throws Exception {
    	domain.setMrnReturnNo(dto.getMrnReturnNo());
    	domain.setMrnReturnStatus(dto.getMrnReturnStatus());
    	domain.setDate(dto.getDate());
        setCommanDomainFields(dto, domain);
    }
    

    @Override
    public MRNReturnDTO domainToDtoForDataTable(MRNReturn domain) throws Exception {
    	
    	MRNReturnDTO dto = new MRNReturnDTO();
    	dto.setId(domain.getId());
        dto.setVersion(domain.getVersion());
        
        dto.setMrnNo(domain.getMrn().getMrnNo()); 
        dto.setMrnReturnNo(domain.getMrnReturnNo());

        if(domain.getMrn()!=null && domain != null){
            dto.setMrnNo(domain.getMrn().getMrnNo()); 
        }
        dto.setMrnReturnNo(domain.getMrnReturnNo());
        dto.setMrnReturnStatus(domain.getMrnReturnStatus());
        dto.setDate(domain.getDate());
        return dto;
    }

}
