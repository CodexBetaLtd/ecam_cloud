package com.codex.ecam.listeners.inventory.mrn;

import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.model.inventory.mrn.MRN;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.service.inventory.api.MRNService;
import com.codex.ecam.service.inventory.api.ReceiptOrderService; 

public class MRNPrePersistListener {
	
	@Autowired
	private MRNService mrnService;

	@PrePersist
	public void fireMRNTriggers(MRN mrn) throws Exception {
		AutowireHelper.autowire(this, mrnService);
		createMRNNumber(mrn);
	}

	private void createMRNNumber(MRN mrn) throws Exception {
		String lastInsertMRNcode = mrnService.getNextCode(mrn.getBusiness().getId());
		mrn.setMrnNo(lastInsertMRNcode);
	}
	

	
}
