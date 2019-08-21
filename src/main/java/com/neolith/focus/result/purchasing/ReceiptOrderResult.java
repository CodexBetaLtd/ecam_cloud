package com.neolith.focus.result.purchasing;

import com.neolith.focus.dto.inventory.receiptOrder.ReceiptOrderDTO;
import com.neolith.focus.model.inventory.receiptOrder.ReceiptOrder;
import com.neolith.focus.result.BaseResult;

public class ReceiptOrderResult extends BaseResult<ReceiptOrder, ReceiptOrderDTO> {

	public ReceiptOrderResult(ReceiptOrder domain, ReceiptOrderDTO dto) {
		super(domain, dto);
	}

	@Override
	public void updateDtoIdAndVersion() {
		getDtoEntity().setId(getDomainEntity().getId());
		getDtoEntity().setVersion(getDomainEntity().getVersion());
	}
}
