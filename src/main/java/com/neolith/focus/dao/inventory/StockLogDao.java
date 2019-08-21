package com.neolith.focus.dao.inventory;
 
import org.springframework.stereotype.Repository;

import com.neolith.focus.model.inventory.stock.StockLog;
import com.neolith.focus.repository.FocusDataTableRepository;

@Repository
public interface StockLogDao extends FocusDataTableRepository<StockLog, Integer> {

}
