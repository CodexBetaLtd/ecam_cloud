package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.inventory.stock.StockPurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPurchaseItemDao extends JpaRepository<StockPurchaseItem, Integer> {

}
