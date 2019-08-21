package com.codex.ecam.result.purchasing;

import com.codex.ecam.dto.inventory.receiptOrder.ReceiptOrderDTO;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.result.BaseResult;

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
