package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.inventory.stockAdjustment.StockAdjustment;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockAdjustmentDao extends FocusDataTableRepository<StockAdjustment, Integer> { 

    @Query("select stockAdjustment from StockAdjustment stockAdjustment where stockAdjustment.id=(select max(id) from StockAdjustment)")
    StockAdjustment findLastDomain();


}
