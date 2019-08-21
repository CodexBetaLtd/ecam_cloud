package com.neolith.focus.mappers.purchasing;

import com.neolith.focus.dto.inventory.purchaseOrder.PurchaseOrderDiscussionDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.inventory.purchaseOrder.PurchaseOrderDiscussion;

public class PurchaseOrderDiscussionMapper extends GenericMapper<PurchaseOrderDiscussion, PurchaseOrderDiscussionDTO> {

    private static PurchaseOrderDiscussionMapper instance = null;

    private PurchaseOrderDiscussionMapper() {
    }

    public static PurchaseOrderDiscussionMapper getInstance() {
        if (instance == null) {
            instance = new PurchaseOrderDiscussionMapper();
        }
        return instance;
    }

	@Override
	public PurchaseOrderDiscussionDTO domainToDto(PurchaseOrderDiscussion domain) throws Exception {
		PurchaseOrderDiscussionDTO dto=new PurchaseOrderDiscussionDTO();
		dto.setId(domain.getId());
		dto.setVersion(domain.getVersion());
		dto.setDiscusionDate(domain.getCreatedDate());
		dto.setComment(domain.getComment());
		dto.setUserId(domain.getCreatedUser().getId());
		dto.setUserName(domain.getCreatedUser().getUserCredential().getUserName());
		
		dto.setVersion(domain.getVersion());
		dto.setIsDeleted(domain.getIsDeleted());
		
		return dto;
	}

	@Override
	public void dtoToDomain(PurchaseOrderDiscussionDTO dto, PurchaseOrderDiscussion domain) throws Exception {

		
	}

    @Override
    public PurchaseOrderDiscussionDTO domainToDtoForDataTable(PurchaseOrderDiscussion domain) throws Exception {
		PurchaseOrderDiscussionDTO dto=new PurchaseOrderDiscussionDTO();
		dto.setId(domain.getId());
		dto.setDiscusionDate(domain.getCreatedDate());
		dto.setComment(domain.getComment());
		return dto;
    }

}
