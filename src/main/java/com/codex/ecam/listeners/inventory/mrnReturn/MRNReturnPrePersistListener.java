package com.codex.ecam.listeners.inventory.mrnReturn;

import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.model.inventory.mrnReturn.MRNReturn;
import com.codex.ecam.service.inventory.api.MRNReturnService; 

public class MRNReturnPrePersistListener {
	
	@Autowired
	private MRNReturnService mrnReturnService;

	@PrePersist
	public void fireMRNTriggers(MRNReturn mrnReturn) throws Exception {
		AutowireHelper.autowire(this, mrnReturnService);
		createMRNReturnNumber(mrnReturn);
	}

	private void createMRNReturnNumber(MRNReturn mrnReturn) throws Exception {
		String lastInsertMRNReturncode = mrnReturnService.getNextCode(mrnReturn.getBusiness().getId());
		mrnReturn.setMrnReturnNo(lastInsertMRNReturncode);
	}
	

	
}
