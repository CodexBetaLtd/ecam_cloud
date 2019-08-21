package com.codex.ecam.mappers.purchasing;

import com.codex.ecam.dto.inventory.receiptOrder.ReceiptOrderItemDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrderItem;

public class ReceiptOrderItemMapper extends GenericMapper<ReceiptOrderItem, ReceiptOrderItemDTO> {
	
	private static ReceiptOrderItemMapper instance = null;

    private ReceiptOrderItemMapper() {
    }

    public static ReceiptOrderItemMapper getInstance() {
        if (instance == null) {
            instance = new ReceiptOrderItemMapper();
        }
        return instance;
    }

	@Override
	public ReceiptOrderItemDTO domainToDto(ReceiptOrderItem domain) throws Exception {
		ReceiptOrderItemDTO dto = new ReceiptOrderItemDTO();
	 	dto.setItemId(domain.getId());
	 	if ( domain.getAsset() != null ) {
	 		dto.setItemAssetId(domain.getAsset().getId());
	 		dto.setItemAssetName(domain.getAsset().getName());
	 	}
		if (domain.getStock() != null) {
			dto.setItemStockId(domain.getStock().getId());
			dto.setItemStockName(domain.getStock().getSite().getName());
	 	}
	 	dto.setItemQtyReceived(domain.getQuantityReceived());
		dto.setItemUnitPrice(domain.getUnitPrice());
	 	dto.setItemDescription(domain.getDescription());
		dto.setVersion(domain.getVersion());
		dto.setIsDeleted(domain.getIsDeleted());
		return dto;
	}

	@Override
	public void dtoToDomain(ReceiptOrderItemDTO dto, ReceiptOrderItem domain) throws Exception {
		domain.setId(dto.getItemId());
		domain.setVersion(dto.getVersion());
		domain.setQuantityReceived(dto.getItemQtyReceived());
		domain.setUnitPrice(dto.getItemUnitPrice());
		domain.setDescription(dto.getItemDescription());
		domain.setIsDeleted(dto.getIsDeleted());
	}

    @Override
    public ReceiptOrderItemDTO domainToDtoForDataTable(ReceiptOrderItem domain) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}
