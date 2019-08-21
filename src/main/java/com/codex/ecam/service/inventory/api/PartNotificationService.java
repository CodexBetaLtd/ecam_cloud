package com.codex.ecam.service.inventory.api;
  
import com.codex.ecam.model.inventory.stock.Stock; 
 
public interface PartNotificationService {

	void publishStockAddNotifications(Stock domain) throws Exception;

	void publishStockRemoveNotifications(Stock domain) throws Exception;
	  
}
