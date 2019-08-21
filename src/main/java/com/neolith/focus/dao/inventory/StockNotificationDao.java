package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.inventory.stock.StockNotification;
import com.neolith.focus.repository.FocusDataTableRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockNotificationDao extends FocusDataTableRepository<StockNotification, Integer> {

    @Query("FROM StockNotification WHERE stock.id = :id ")
    List<StockNotification> findByStockId(@Param("id") Integer id);

}
