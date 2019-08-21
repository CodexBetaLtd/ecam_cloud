package com.codex.ecam.mappers.purchasing;

import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderItemDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItem;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItemRFQItem;
import com.codex.ecam.util.DateUtil;

public class PurchaseOrderItemMapper extends GenericMapper<PurchaseOrderItem, PurchaseOrderItemDTO> {
	
	private static PurchaseOrderItemMapper instance = null;

    private PurchaseOrderItemMapper() {
    }

    public static PurchaseOrderItemMapper getInstance() {
        if (instance == null) {
            instance = new PurchaseOrderItemMapper();
        }
        return instance;
    }

	@Override
	public PurchaseOrderItemDTO domainToDto(PurchaseOrderItem domain) throws Exception {
		PurchaseOrderItemDTO dto = new PurchaseOrderItemDTO();
	 	
	 	dto.setItemId(domain.getId());
	 	
	 	if ( domain.getAccount() != null ) {
	 		dto.setItemAccountId(domain.getAccount().getId());
	 	}
	 	
	 	if ( domain.getAsset() != null ) {
	 		dto.setItemAssetId(domain.getAsset().getId());
	 		dto.setItemAssetName(domain.getAsset().getName());
	 	}
	 	
	 	if ( domain.getChargeDepartment() != null ) {
	 		dto.setItemChargeDepartmentId(domain.getChargeDepartment().getId());
	 	}
	 	
	 	if ( domain.getParentPurchaseOrderItem() != null ) {
	 		dto.setItemParentId(domain.getParentPurchaseOrderItem().getId());
	 	}
	 	
	 	if ( domain.getPurchaseOrder() != null ) {
	 		dto.setItemPurchaseOrderId(domain.getPurchaseOrder().getId());
	 	}
	 	
	 	if ( domain.getRequestedByUser() != null ) {
	 		dto.setItemRequestedByUserId(domain.getRequestedByUser().getId());
	 	}
	 	
	 	if ( domain.getSite() != null ) {
	 		dto.setItemSiteId(domain.getSite().getId());
	 		dto.setItemSiteName(domain.getSite().getName());
	 	}
	 	
	 	if ( domain.getSourceAsset() != null ) {
	 		dto.setItemSourceAssetId(domain.getSourceAsset().getId());
	 		dto.setItemSourceAssetName(domain.getSourceAsset().getName());
	 	}
	 	
	 	if ( domain.getSourceWorkOrder() != null ) {
	 		dto.setItemSourceWorkOrderId(domain.getSourceWorkOrder().getId());
	 		dto.setItemSourceWorkOrderCode(domain.getSourceWorkOrder().getCode());
	 	}
	 	
	 	if ( domain.getStockHistory() != null ) {
	 		dto.setItemStockHistoryId(domain.getStockHistory().getId());
	 	}
	 	
	 	if ( domain.getStock() != null ) {
	 		dto.setItemStockId(domain.getStock().getId());
	 	}
	 	
	 	if ( domain.getSupplier() != null ) {
	 		dto.setItemSupplierId(domain.getSupplier().getId());
	 	}

        if (domain.getPurchaseOrder() != null) {
            dto.setPurchaseOrderId(domain.getPurchaseOrder().getId());
            dto.setPurchaseOrderCode(domain.getPurchaseOrder().getCode());
        }
        setRfqItemData(domain, dto);

        dto.setItemBusinessAssetNo(domain.getBusinessAssetNo());
		dto.setItemDescription(domain.getDescription());
		dto.setItemRemoteOrgUnitPrice(domain.getRemoteOrgUnitPrice());
		dto.setItemTaxRate(domain.getTaxRate());
		dto.setItemTotalPrice(domain.getTotalPrice());
		dto.setItemUnitPrice(domain.getUnitPrice());
		dto.setItemQtyOnOrder(domain.getQtyOnOrder());
		dto.setIsAddedDirectlyToPurchaseOrder(domain.getIsAddedDirectlyToPurchaseOrder());
		dto.setIsProductionEquipmentDownWhileOnOrder(domain.getIsProductionEquipmentDownWhileOnOrder());
		dto.setIsSupplierConfirmed(domain.getIsSupplierConfirmed());
		dto.setItemRequiredByDate(domain.getRequiredByDate());
		
		dto.setVersion(domain.getVersion());
		dto.setIsDeleted(domain.getIsDeleted());
		
		return dto;
	}

	private void setRfqItemData(PurchaseOrderItem domain, PurchaseOrderItemDTO dto) throws Exception {
		StringBuilder rfqCodes = new StringBuilder();
		
		for ( PurchaseOrderItemRFQItem rfqItem : domain.getRfqItems()) {
			if (rfqCodes.toString().isEmpty()) {
				rfqCodes.append(rfqItem.getRfqItem().getRfq().getCode());
			} else {
				if(!rfqCodes.toString().contains(rfqItem.getRfqItem().getRfq().getCode())) {
					rfqCodes.append("," + rfqItem.getRfqItem().getRfq().getCode());
				}
			}
		}
		dto.setItemRfqCodes(rfqCodes.toString());
	}

	@Override
	public void dtoToDomain(PurchaseOrderItemDTO dto, PurchaseOrderItem domain) throws Exception {
		domain.setId(dto.getItemId());
		domain.setBusinessAssetNo(dto.getItemBusinessAssetNo());
		domain.setDescription(dto.getItemDescription());
		domain.setRemoteOrgUnitPrice(dto.getItemRemoteOrgUnitPrice());
		domain.setTaxRate(dto.getItemTaxRate());
		domain.setTotalPrice(dto.getItemTotalPrice());
		domain.setUnitPrice(dto.getItemUnitPrice());
		domain.setQtyOnOrder(dto.getItemQtyOnOrder());
		domain.setIsAddedDirectlyToPurchaseOrder(dto.getIsAddedDirectlyToPurchaseOrder());
		domain.setIsProductionEquipmentDownWhileOnOrder(dto.getIsProductionEquipmentDownWhileOnOrder());
		domain.setIsSupplierConfirmed(dto.getIsSupplierConfirmed());
		domain.setRequiredByDate(DateUtil.getDateObj(dto.getItemRequiredByDate()));
		
		domain.setVersion(dto.getVersion());
		domain.setIsDeleted(dto.getIsDeleted());
	}

    @Override
    public PurchaseOrderItemDTO domainToDtoForDataTable(PurchaseOrderItem domain) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}
