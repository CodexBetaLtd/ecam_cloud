package com.codex.ecam.mappers.purchasing.purchaseorder;

import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderAdditionalCostDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderAdditionalCost;

public class PurchaseOrderAdditionalCostMapper extends GenericMapper<PurchaseOrderAdditionalCost, PurchaseOrderAdditionalCostDTO> {

	private static PurchaseOrderAdditionalCostMapper instance = null;

    private PurchaseOrderAdditionalCostMapper() {
    }

    public static PurchaseOrderAdditionalCostMapper getInstance() {
        if (instance == null) {
            instance = new PurchaseOrderAdditionalCostMapper();
        }
        return instance;
    }

	@Override
	public PurchaseOrderAdditionalCostDTO domainToDto(PurchaseOrderAdditionalCost domain) throws Exception {
		PurchaseOrderAdditionalCostDTO dto=new PurchaseOrderAdditionalCostDTO();
		dto.setId(domain.getId());
        dto.setAdditionalCostName(domain.getDescription());
        dto.setAdditionalCostTypeName(domain.getPurchaseOrderAdditionalCostType().getName());
        dto.setAdditionalCostTypeId(domain.getPurchaseOrderAdditionalCostType().getId());
        if (domain.getShippingType() == null) {
            dto.setShippingTypeId(null);
			dto.setShippingTypeName(null);
		} else{
			dto.setShippingTypeId(domain.getShippingType().getId());
			dto.setShippingTypeName(domain.getShippingType().getName());
		}
        dto.setIsOverridePoItemTax(domain.getIsOverridePoItemTax());
        ;
        dto.setAmount(domain.getPrice());
        dto.setTaxRate(domain.getTaxRate());

		dto.setVersion(domain.getVersion());
		dto.setIsDeleted(domain.getIsDeleted());
		
		return dto;
	}

	@Override
	public void dtoToDomain(PurchaseOrderAdditionalCostDTO dto, PurchaseOrderAdditionalCost domain) throws Exception {

		
	}

    @Override
    public PurchaseOrderAdditionalCostDTO domainToDtoForDataTable(PurchaseOrderAdditionalCost domain) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
