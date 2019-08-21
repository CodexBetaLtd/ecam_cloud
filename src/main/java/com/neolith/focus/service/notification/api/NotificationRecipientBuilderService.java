package com.neolith.focus.service.notification.api;

import java.util.List;

import com.neolith.focus.dto.BaseDTO;
import com.neolith.focus.model.BaseModel;

public interface NotificationRecipientBuilderService {
	
	public List<Integer> getStockAddNotifyRecipients(BaseModel model);
	
	public List<Integer> getStockAddNotifyRecipients(BaseDTO dto);
	
	public List<Integer> getStockRemoveNotifyRecipients(BaseModel model);
	
	public List<Integer> getStockRemoveNotifyRecipients(BaseDTO dto);
	
	public List<Integer> getStockMinQtyNotifyRecipients(BaseModel model);
	
	public List<Integer> getStockMinQtyNotifyRecipients(BaseDTO dto);
	
}
