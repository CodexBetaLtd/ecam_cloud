package com.codex.ecam.listeners.inventory.receiptorder;

import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.service.inventory.api.ReceiptOrderService; 

public class ReceiptorderPrePersistListener {
	
	@Autowired
	private ReceiptOrderService receiptOrderService;

	@PrePersist
	public void fireReceiptorderTriggers(ReceiptOrder receiptOrder) throws Exception {
		AutowireHelper.autowire(this, receiptOrderService);
		createReceiptorderNumber(receiptOrder);
	}

	private void createReceiptorderNumber(ReceiptOrder receiptOrder) throws Exception {
		String lastInsertRFQcode = receiptOrderService.getNextCode(receiptOrder.getBusiness().getId());
		receiptOrder.setCode(lastInsertRFQcode);
	}
	

	
}
