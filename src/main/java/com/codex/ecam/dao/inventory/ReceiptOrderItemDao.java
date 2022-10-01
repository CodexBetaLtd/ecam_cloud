package com.codex.ecam.dao.inventory;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrderItem;
import com.codex.ecam.repository.FocusDataTableRepository;

import java.util.List;

@Repository
public interface ReceiptOrderItemDao extends FocusDataTableRepository<ReceiptOrderItem, Integer> {

    List<ReceiptOrderItem> findByAsset(Asset asset);

}
