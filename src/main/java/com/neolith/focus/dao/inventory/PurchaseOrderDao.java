package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.inventory.purchaseOrder.PurchaseOrder;
import com.neolith.focus.repository.FocusDataTableRepository; 

import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderDao extends FocusDataTableRepository<PurchaseOrder, Integer> {

}
