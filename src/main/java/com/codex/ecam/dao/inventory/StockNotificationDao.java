package com.codex.ecam.dao.inventory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.stock.StockNotification;
import com.codex.ecam.repository.FocusDataTableRepository;

import java.util.List;

@Repository
public interface StockNotificationDao extends FocusDataTableRepository<StockNotification, Integer> {

    @Query("FROM StockNotification WHERE stock.id = :id ")
    List<StockNotification> findByStockId(@Param("id") Integer id);

}
