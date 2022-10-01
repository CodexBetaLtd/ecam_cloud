package com.codex.ecam.dao.inventory;
 
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.stock.StockLog;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface StockLogDao extends FocusDataTableRepository<StockLog, Integer> {

}
