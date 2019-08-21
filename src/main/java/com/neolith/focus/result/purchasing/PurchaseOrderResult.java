package com.neolith.focus.result.purchasing;

import com.neolith.focus.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.neolith.focus.model.inventory.purchaseOrder.PurchaseOrder;
import com.neolith.focus.result.BaseResult;

public class PurchaseOrderResult  extends BaseResult<PurchaseOrder, PurchaseOrderDTO> {

	public PurchaseOrderResult(PurchaseOrder domain, PurchaseOrderDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		// TODO Auto-generated method stub
		
	}

}
