package com.codex.ecam.dao.inventory;
 
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderChangeLog;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface PurchaseOrderChangeLogDao extends FocusDataTableRepository<PurchaseOrderChangeLog, Integer> {

}
