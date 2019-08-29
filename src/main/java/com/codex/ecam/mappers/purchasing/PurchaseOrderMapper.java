package com.codex.ecam.mappers.purchasing;

import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrder;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderAdditionalCost;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderDiscussion;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItem;

public class PurchaseOrderMapper extends GenericMapper<PurchaseOrder, PurchaseOrderDTO> {
	
	private static PurchaseOrderMapper instance = null;

    private PurchaseOrderMapper() {
    }

    public static PurchaseOrderMapper getInstance() {
        if (instance == null) {
            instance = new PurchaseOrderMapper();
        }
        return instance;
    }

	@Override
	public PurchaseOrderDTO domainToDto(PurchaseOrder domain) throws Exception {
		PurchaseOrderDTO dto = new PurchaseOrderDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());		
		
		dto.setStatusName(domain.getPurchaseOrderStatus().getName());
		dto.setPurchaseOrderstatus(domain.getPurchaseOrderStatusId());
		dto.setExpectedDeliveryDate(domain.getExpectedDeliveryDate());
		dto.setBillingTermId(domain.getBillingTermId());		
		dto.setExpectedDeliveryDate(domain.getExpectedDeliveryDate());
		
		dto.setSupplierAddress(domain.getSupplierAddress());
		dto.setSupplierCity(domain.getSupplierCity());
		dto.setSupplierPostalCode(domain.getSupplierPostalCode());
		dto.setSupplierProvince(domain.getSupplierProvince());		
		
		dto.setShipToAddress(domain.getShipToAddress());
		dto.setShippingCity(domain.getShipToCity());
		dto.setShippingPostalCode(domain.getShipToPostalCode());
		dto.setShippingProvince(domain.getShipToProvince());		
		
		dto.setBillToAddress(domain.getBillToAddress());
		dto.setBillingCity(domain.getBillToCity());
		dto.setBillingPostalCode(domain.getBillToPostalCode());
		dto.setBillingProvince(domain.getBillToProvince());
		
		if ( domain.getSupplier() != null ) {
			dto.setSupplierId(domain.getSupplier().getId());
			dto.setSupplierName(domain.getSupplier().getName());
		}
		
		if ( domain.getShipToFacility() != null ) {
			dto.setShipToId(domain.getShipToFacility().getId());
		}
		
		if ( domain.getBillToFaciltiy() != null ) {
			dto.setBillToId(domain.getBillToFaciltiy().getId());
		}
		
		if ( domain.getSupplierCountry() != null ) {
			dto.setSupplierCountry(domain.getSupplierCountry().getId());
		}
		
		if ( domain.getShipToCountry() != null ) {
			dto.setShipToCountry(domain.getShipToCountry().getId());
		}
		
		if ( domain.getBillCountry() != null ) {
			dto.setBillToCountry(domain.getBillCountry().getId());
		}
		
		if ( domain.getChargeDepartment() != null ) {
			dto.setChargeDepartmentId(domain.getChargeDepartment().getId());
		}
		
		if ( domain.getPurchaseCurrency() != null ) {
			dto.setPurchaseCurrencyId(domain.getPurchaseCurrency().getId());
		}
		
		if ( domain.getAccount() != null ) {
			dto.setAccountId(domain.getAccount().getId());	
		}

        if (domain.getPurchaseOrderNotifications() != null && domain.getPurchaseOrderNotifications().size() > 0) {
            dto.setNotificationDTOs(PurchaseOrderNotificationMapper.getInstance().domainToDTOList(domain.getPurchaseOrderNotifications()));
        }

		dto.setVersion(domain.getVersion());
		dto.setIsDeleted(domain.getIsDeleted());
		
		setAdditionalCost(domain,dto);
		setPurchaseOrderItem(domain, dto);
		setDiscussion(domain,dto);
		
		return dto;
	}
	
	private void setPurchaseOrderItem(PurchaseOrder domain, PurchaseOrderDTO dto) throws Exception {
		
		for ( PurchaseOrderItem item : domain.getPurchaseOrderItems()) {
			dto.getItems().add(PurchaseOrderItemMapper.getInstance().domainToDto(item));
		}
		
	}

	private void setAdditionalCost(PurchaseOrder domain,PurchaseOrderDTO dto) throws Exception {

		for(PurchaseOrderAdditionalCost purchaseOrderAdditionalCost : domain.getPurchaseOrderAdditionalCosts()){
			dto.getAdditionalCostDTOs().add(PurchasOrderAdditionalCostMapper.getInstance().domainToDto(purchaseOrderAdditionalCost));
		}
		
	}
	private void setDiscussion(PurchaseOrder domain,PurchaseOrderDTO dto) throws Exception {

		for(PurchaseOrderDiscussion orderDiscussion:domain.getPurchaseOrderDiscussions()){
			dto.getDiscussionDTOs().add(PurchaseOrderDiscussionMapper.getInstance().domainToDto(orderDiscussion));
		}
		
	}

	@Override
	public void dtoToDomain(PurchaseOrderDTO dto, PurchaseOrder domain) throws Exception {
		domain.setId(dto.getId());
		domain.setCode(dto.getCode());
		domain.setPurchaseOrderStatus(dto.getPurchaseOrderstatus());
		domain.setExpectedDeliveryDate(dto.getExpectedDeliveryDate());
		domain.setBillingTermId(dto.getBillingTermId());

		domain.setSupplierAddress(dto.getSupplierAddress());
		domain.setSupplierCity(dto.getSupplierCity());
		domain.setSupplierPostalCode(dto.getSupplierPostalCode());
		domain.setSupplierProvince(dto.getSupplierProvince());
		
		domain.setShipToAddress(dto.getShipToAddress());
		domain.setShipToCity(dto.getShippingCity());
		domain.setShipToPostalCode(dto.getShippingPostalCode());
		domain.setShipToProvince(dto.getShippingProvince());
		
		domain.setBillToAddress(dto.getBillToAddress());
		domain.setBillToCity(dto.getBillingCity());
		domain.setBillToPostalCode(dto.getBillingPostalCode());
		domain.setBillToProvince(dto.getBillingProvince());
		domain.setPurchaseOrderStatus(dto.getPurchaseOrderstatus());
		
		domain.setVersion(dto.getVersion());
		domain.setIsDeleted(dto.getIsDeleted());
	}

    @Override
    public PurchaseOrderDTO domainToDtoForDataTable(PurchaseOrder domain) throws Exception {
    	PurchaseOrderDTO dto = new PurchaseOrderDTO();
		dto.setId(domain.getId());
		dto.setCode(domain.getCode());		
		dto.setStatusName(domain.getPurchaseOrderStatus().getName());
		if ( domain.getSupplier() != null ) {
			dto.setSupplierId(domain.getSupplier().getId());
			dto.setSupplierName(domain.getSupplier().getName());
		}
        return dto;
    }

}