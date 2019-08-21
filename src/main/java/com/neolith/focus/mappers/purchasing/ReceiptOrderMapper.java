package com.neolith.focus.mappers.purchasing;

import com.neolith.focus.dto.inventory.receiptOrder.ReceiptOrderDTO;
import com.neolith.focus.mappers.GenericMapper;
import com.neolith.focus.model.inventory.receiptOrder.ReceiptOrder;
import com.neolith.focus.model.inventory.receiptOrder.ReceiptOrderItem;

public class ReceiptOrderMapper extends GenericMapper<ReceiptOrder, ReceiptOrderDTO> {
	
	private static ReceiptOrderMapper instance = null;

    private ReceiptOrderMapper() {
    }

    public static ReceiptOrderMapper getInstance() {
        if (instance == null) {
            instance = new ReceiptOrderMapper();
        }
        return instance;
    }

	@Override
	public ReceiptOrderDTO domainToDto(ReceiptOrder domain) throws Exception {
		ReceiptOrderDTO dto = new ReceiptOrderDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());
		dto.setDateOrdered(domain.getDateOrdered());
		dto.setDateReceived(domain.getDateReceived());
		dto.setReceiptOrderStatus(domain.getReceiptOrderStatus());
		dto.setStatusName(domain.getReceiptOrderStatus().getName());
		
		if ( domain.getSupplier() != null ) {
			dto.setSupplierId(domain.getSupplier().getId());
			dto.setSupplierName(domain.getSupplier().getName());
		}
		
		dto.setVersion(domain.getVersion());
		dto.setIsDeleted(domain.getIsDeleted());
		
		setReceiptOrderItem(domain, dto);
		
		return dto;
	}
	
	private void setReceiptOrderItem(ReceiptOrder domain, ReceiptOrderDTO dto) throws Exception {
		
		for ( ReceiptOrderItem item : domain.getReceiptOrderItems()) {
			dto.getItems().add(ReceiptOrderItemMapper.getInstance().domainToDto(item));
		}
		
	}

	@Override
	public void dtoToDomain(ReceiptOrderDTO dto, ReceiptOrder domain) throws Exception {
		domain.setId(dto.getId());
		domain.setCode(dto.getCode());
		domain.setDateOrdered(dto.getDateOrdered());
		domain.setDateReceived(dto.getDateReceived());
		domain.setReceiptOrderStatus(dto.getReceiptOrderStatus());
		domain.setVersion(dto.getVersion());
		domain.setIsDeleted(dto.getIsDeleted());

	}

    @Override
    public ReceiptOrderDTO domainToDtoForDataTable(ReceiptOrder domain) throws Exception {
    	ReceiptOrderDTO dto = new ReceiptOrderDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());		
		dto.setStatusName(domain.getReceiptOrderStatus().getName());
		if ( domain.getSupplier() != null ) {
			dto.setSupplierId(domain.getSupplier().getId());
			dto.setSupplierName(domain.getSupplier().getName());
		}
        return dto;
    }

}
