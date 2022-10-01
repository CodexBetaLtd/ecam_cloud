package com.codex.ecam.result.purchasing;

import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrder;
import com.codex.ecam.result.BaseResult;

public class PurchaseOrderResult  extends BaseResult<PurchaseOrder, PurchaseOrderDTO> {

	public PurchaseOrderResult(PurchaseOrder domain, PurchaseOrderDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
		
	}

}
