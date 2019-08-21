package com.neolith.focus.service.inventory.api;
  
import com.neolith.focus.model.inventory.stock.Stock; 
 
public interface PartNotificationService {

	void publishStockAddNotifications(Stock domain) throws Exception;

	void publishStockRemoveNotifications(Stock domain) throws Exception;
	  
}
