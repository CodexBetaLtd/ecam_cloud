package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.inventory.receiptOrder.ReceiptOrder;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptOrderDao extends FocusDataTableRepository<ReceiptOrder, Integer> {

}
