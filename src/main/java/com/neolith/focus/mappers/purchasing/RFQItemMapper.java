package com.neolith.focus.mappers.purchasing;

import com.neolith.focus.dto.inventory.rfq.RFQItemDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.inventory.purchaseOrder.PurchaseOrderItemRFQItem;
import com.neolith.focus.model.inventory.rfq.RFQItem;

public class RFQItemMapper extends GenericMapper<RFQItem, RFQItemDTO> {
	
	private static RFQItemMapper instance = null;

    private RFQItemMapper() {
    }

    public static RFQItemMapper getInstance() {
        if (instance == null) {
            instance = new RFQItemMapper();
        }
        return instance;
    }

	@Override
	public RFQItemDTO domainToDto(RFQItem domain) throws Exception {
		RFQItemDTO dto = new RFQItemDTO();
	 	
	 	dto.setItemId(domain.getId());
	 	
	 	if ( domain.getAsset() != null ) {
	 		dto.setItemAssetId(domain.getAsset().getId());
	 		dto.setItemAssetName(domain.getAsset().getName());
	 	}
	 	
	 	dto.setItemQtyRequested(domain.getRequested());
	 	dto.setItemQuotedQty(domain.getQuoted());
	 	dto.setItemQuotedUnitPrice(domain.getQuotedPricePerUnit());
	 	dto.setItemQuotedTotalPrice(domain.getQuotedPriceTotal());
	 	dto.setItemDescription(domain.getDescription());
	 	
	 	sePoItemData(domain, dto);
		
		dto.setVersion(domain.getVersion());
		dto.setIsDeleted(domain.getIsDeleted());
		
		return dto;
	}
	
	private void sePoItemData(RFQItem domain,  RFQItemDTO dto) throws Exception {
		StringBuilder poCodes = new StringBuilder();
		
		for ( PurchaseOrderItemRFQItem rfqItem : domain.getPurchaseOrderItems()) {
			if (poCodes.toString().isEmpty()) {
				poCodes.append(rfqItem.getPurchaseOrderItem().getPurchaseOrder().getCode());
			} else {
				if(!poCodes.toString().contains(rfqItem.getPurchaseOrderItem().getPurchaseOrder().getCode())) {
					poCodes.append("," + rfqItem.getPurchaseOrderItem().getPurchaseOrder().getCode());
				}
			}
		}
		dto.setItemPurchaseOrderCodes(poCodes.toString());
	}

	@Override
	public void dtoToDomain(RFQItemDTO dto, RFQItem domain) throws Exception {
		domain.setId(dto.getItemId());
		domain.setRequested(dto.getItemQtyRequested());;
		domain.setQuoted(dto.getItemQuotedQty());
	 	domain.setQuotedPricePerUnit(dto.getItemQuotedUnitPrice());
	 	domain.setQuotedPriceTotal(dto.getItemQuotedTotalPrice());
	 	domain.setDescription(dto.getItemDescription());
		
		domain.setVersion(dto.getVersion());
		domain.setIsDeleted(dto.getIsDeleted());
	}

    @Override
    public RFQItemDTO domainToDtoForDataTable(RFQItem domain) throws Exception {
		RFQItemDTO dto = new RFQItemDTO();	 	
	 	dto.setItemId(domain.getId());	 	
	 	if ( domain.getAsset() != null ) {
	 		dto.setItemAssetId(domain.getAsset().getId());
	 		dto.setItemAssetName(domain.getAsset().getName());
	 	}	 	
	 	dto.setItemQtyRequested(domain.getRequested());
	 	dto.setItemQuotedQty(domain.getQuoted());
	 	dto.setItemQuotedUnitPrice(domain.getQuotedPricePerUnit());
	 	dto.setItemQuotedTotalPrice(domain.getQuotedPriceTotal());
		return dto;
    }
}
