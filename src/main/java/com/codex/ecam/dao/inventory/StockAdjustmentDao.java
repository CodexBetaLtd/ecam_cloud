package com.codex.ecam.dao.inventory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.stockAdjustment.StockAdjustment;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface StockAdjustmentDao extends FocusDataTableRepository<StockAdjustment, Integer> { 

    @Query("select stockAdjustment from StockAdjustment stockAdjustment where stockAdjustment.id=(select max(id) from StockAdjustment)")
    StockAdjustment findLastDomain();


}
