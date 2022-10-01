package com.codex.ecam.dao.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.stock.StockPurchaseItem;

@Repository
public interface StockPurchaseItemDao extends JpaRepository<StockPurchaseItem, Integer> {

}
