package com.codex.ecam.dao.inventory;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItem;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface PurchaseOrderItemDao extends FocusDataTableRepository<PurchaseOrderItem, Integer> {


    @Modifying
    @Transactional
    @Query("select poi from PurchaseOrderItem poi left join fetch poi.purchaseOrder po  where poi.asset.id = :partId and po.id = :poId")
    List<PurchaseOrderItem> findOpenPOItemListByAsset(@Param("partId") Integer partId, @Param("poId") Integer poId);
}
