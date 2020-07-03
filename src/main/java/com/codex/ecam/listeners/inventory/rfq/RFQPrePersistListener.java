package com.codex.ecam.listeners.inventory.rfq;

import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.model.inventory.rfq.RFQ;
import com.codex.ecam.service.inventory.api.RFQService; 

public class RFQPrePersistListener {
	
	@Autowired
	private RFQService rfqService;

	@PrePersist
	public void fireRFQTriggers(RFQ rfq) throws Exception {
		AutowireHelper.autowire(this, rfqService);
		createRFQNumber(rfq);
	}

	private void createRFQNumber(RFQ rfq) throws Exception {
		String lastInsertRFQcode = rfqService.getNextCode(rfq.getBusiness().getId());
		rfq.setCode(lastInsertRFQcode);
	}
	

	
}
