package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.inventory.receiptOrder.ReceiptOrderItem;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptOrderItemDao extends FocusDataTableRepository<ReceiptOrderItem, Integer> {

    List<ReceiptOrderItem> findByAsset(Asset asset);

}
