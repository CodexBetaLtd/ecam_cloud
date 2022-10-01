package com.codex.ecam.mappers.purchasing.purchaseorder;

import com.codex.ecam.constants.PurchaseOrderStatus;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.rfq.RFQ;

public class PurchaseOrderRFQMapper extends GenericMapper<RFQ, PurchaseOrderDTO> {
	
	private static PurchaseOrderRFQMapper instance = null;

    private PurchaseOrderRFQMapper() {
    }

    public static PurchaseOrderRFQMapper getInstance() {
        if (instance == null) {
            instance = new PurchaseOrderRFQMapper();
        }
        return instance;
    }

	@Override
	public PurchaseOrderDTO domainToDto(RFQ domain) throws Exception {
		PurchaseOrderDTO dto = new PurchaseOrderDTO();
		dto.setPurchaseOrderstatus(PurchaseOrderStatus.DRAFT);
	//	dto.setBillingTermId();		
		dto.setExpectedDeliveryDate(domain.getDateExpectedDelivery());
		
//		dto.setSupplierAddress(domain.getSupplierAddress());
//		dto.setSupplierCity(domain.getSupplierCity());
//		dto.setSupplierPostalCode(domain.getSupplierPostalCode());
//		dto.setSupplierProvince(domain.getSupplierProvince());		
		
		dto.setShipToAddress(domain.getShippingAddress());
		dto.setShippingCity(domain.getShippingCity());
		dto.setShippingPostalCode(domain.getShippingPostalCode());
		dto.setShippingProvince(domain.getShippingAddress());		
		
		dto.setBillToAddress(domain.getBillingAddress());
		dto.setBillingCity(domain.getBillingCity());
		dto.setBillingPostalCode(domain.getBillingPostalCode());
		dto.setBillingProvince(domain.getBillingProvince());
		
		if ( domain.getSupplier() != null ) {
			dto.setSupplierId(domain.getSupplier().getId());
			dto.setSupplierName(domain.getSupplier().getName());
		}
		if ( domain.getBusiness() != null ) {
			dto.setBusinessId(domain.getBusiness().getId());
		}
		
		
		if ( domain.getSupplierCountry() != null ) {
			dto.setSupplierCountry(domain.getSupplierCountry().getId());
		}
		
		if ( domain.getShippingCountry()!= null ) {
			dto.setShipToCountry(domain.getShippingCountry().getId());
		}
		
		if ( domain.getBillingCountry() != null ) {
			dto.setBillToCountry(domain.getBillingCountry().getId());
		}


		dto.setVersion(domain.getVersion());
		dto.setIsDeleted(domain.getIsDeleted());
	
		
		return dto;
	}


	@Override
	public void dtoToDomain(PurchaseOrderDTO dto, RFQ domain) throws Exception {

	}

    @Override
    public PurchaseOrderDTO domainToDtoForDataTable(RFQ domain) throws Exception {
    	PurchaseOrderDTO dto = new PurchaseOrderDTO();
	
        return dto;
    }

}
