package com.codex.ecam.listeners.inventory.purchaseorder;

import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrder;
import com.codex.ecam.model.inventory.rfq.RFQ;
import com.codex.ecam.service.inventory.api.PurchaseOrderService;
import com.codex.ecam.service.inventory.api.RFQService; 

public class PurchaseOrderPrePersistListener {
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@PrePersist
	public void firePurchaseOrderTriggers(PurchaseOrder purchaseOrder) throws Exception {
		AutowireHelper.autowire(this, purchaseOrderService);
		createPurchaseOrderNumber(purchaseOrder);
	}

	private void createPurchaseOrderNumber(PurchaseOrder purchaseOrder) throws Exception {
		String lastInsertRFQcode = purchaseOrderService.getNextCode(purchaseOrder.getBusiness().getId());
		purchaseOrder.setCode(lastInsertRFQcode);
	}
	

	
}
