package com.codex.ecam.dao.inventory;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrder;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface PurchaseOrderDao extends FocusDataTableRepository<PurchaseOrder, Integer> {

}
