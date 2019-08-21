package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.inventory.stock.StockHistory;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockHistoryDao extends FocusDataTableRepository<StockHistory, Integer> {

    @Query("select stockHistory from StockHistory stockHistory " +
            "left join stockHistory.stock as stock " +
            "where stock.id = :stockId order by stockHistory.createdDate asc ")
    List<StockHistory> findStockHistoryByStock(@Param("stockId") Integer stockId);
}
