package com.neolith.focus.service.inventory.api; 

import com.neolith.focus.model.inventory.stock.Stock;

public interface StockNotificationService { 

	void publishStockAddNotifications(Stock domain)throws Exception;

	void publishStockRemoveNotifications(Stock domain) throws Exception;

    void publishMinQtyNotifications(Stock domain) throws Exception;

}
