package com.codex.ecam.service.notification.impl;

import java.util.ArrayList;
import java.util.List; 
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.dto.biz.part.PartDTO;
import com.codex.ecam.dto.inventory.stock.StockDTO;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.service.notification.api.NotificationRecipientBuilderService;

@Service
public class NotificationRecipientBuilderServiceImpl implements NotificationRecipientBuilderService {

	@Override
	public List<Integer> getStockAddNotifyRecipients(BaseModel model) {
		
		if (model instanceof Asset) {
			Asset asset = (Asset) model; 
			if (asset.getPartNotifications() != null && asset.getPartNotifications().size() > 0 ) {				
				List<Integer> recipients = asset.getPartNotifications().stream()
						.filter(p -> p.getNotifyOnStockAdd() == Boolean.TRUE)
						.map(p -> p.getUser().getId())
						.collect(Collectors.toList()); 
				
				return recipients;
			}
			
		} else if(model instanceof Stock) {
			Stock stock = (Stock) model; 
			if (stock.getStockNotifications() != null && stock.getStockNotifications().size() > 0 ) {				
				List<Integer> recipients = stock.getStockNotifications().stream()
						.filter(p -> p.getNotifyOnStockAdd() == Boolean.TRUE)
						.map(p -> p.getUser().getId())
						.collect(Collectors.toList()); 
				
				return recipients;
			}
		}
		return new ArrayList<>();
	}

	@Override
	public List<Integer> getStockAddNotifyRecipients(BaseDTO dto) {
		
		if (dto instanceof PartDTO) {
			PartDTO partDTO = (PartDTO) dto; 
			if (partDTO.getPartNotificationDTOs() != null && partDTO.getPartNotificationDTOs().size() > 0 ) {				
				List<Integer> recipients = partDTO.getPartNotificationDTOs().stream()
						.filter(p -> p.getNotifyOnStockAdd() == Boolean.TRUE)
						.map(p -> p.getUserId())
						.collect(Collectors.toList()); 
				
				return recipients;
			}
			
		} else if(dto instanceof StockDTO) {
			StockDTO stockDTO = (StockDTO) dto; 
			if (stockDTO.getStockNotificationDTOs() != null && stockDTO.getStockNotificationDTOs().size() > 0 ) {				
				List<Integer> recipients = stockDTO.getStockNotificationDTOs().stream()
						.filter(p -> p.getNotifyOnStockAdd() == Boolean.TRUE)
						.map(p -> p.getUserId())
						.collect(Collectors.toList()); 
				
				return recipients;
			}
		}
		return new ArrayList<>();
	}

	@Override
	public List<Integer> getStockRemoveNotifyRecipients(BaseModel model) {
		if (model instanceof Asset) {
			Asset asset = (Asset) model; 
			if (asset.getPartNotifications() != null && asset.getPartNotifications().size() > 0 ) {				
				List<Integer> recipients = asset.getPartNotifications().stream()
						.filter(p -> p.getNotifyOnStockRemove() == Boolean.TRUE)
						.map(p -> p.getUser().getId())
						.collect(Collectors.toList()); 
				
				return recipients;
			}
			
		} else if(model instanceof Stock) {
			Stock stockDTO = (Stock) model; 
			if (stockDTO.getStockNotifications() != null && stockDTO.getStockNotifications().size() > 0 ) {				
				List<Integer> recipients = stockDTO.getStockNotifications().stream()
						.filter(p -> p.getNotifyOnStockRemove() == Boolean.TRUE)
						.map(p -> p.getUser().getId())
						.collect(Collectors.toList()); 
				
				return recipients;
			}
		}
		return new ArrayList<>();
	}

	@Override
	public List<Integer> getStockRemoveNotifyRecipients(BaseDTO dto) {
				
		if (dto instanceof PartDTO) {
			PartDTO partDTO = (PartDTO) dto; 
			if (partDTO.getPartNotificationDTOs() != null && partDTO.getPartNotificationDTOs().size() > 0 ) {				
				List<Integer> recipients = partDTO.getPartNotificationDTOs().stream()
						.filter(p -> p.getNotifyOnStockRemove() == Boolean.TRUE)
						.map(p -> p.getUserId())
						.collect(Collectors.toList()); 
				
				return recipients;
			}
			
		} else if(dto instanceof StockDTO) {
			StockDTO stockDTO = (StockDTO) dto; 
			if (stockDTO.getStockNotificationDTOs() != null && stockDTO.getStockNotificationDTOs().size() > 0 ) {				
				List<Integer> recipients = stockDTO.getStockNotificationDTOs().stream()
						.filter(p -> p.getNotifyOnStockRemove() == Boolean.TRUE)
						.map(p -> p.getUserId())
						.collect(Collectors.toList()); 
				
				return recipients;
			}
		}
		return new ArrayList<>();
	}

	@Override
	public List<Integer> getStockMinQtyNotifyRecipients(BaseModel model) {
		if (model instanceof Asset) {
			Asset asset = (Asset) model; 
			if (asset.getPartNotifications() != null && asset.getPartNotifications().size() > 0 ) {				
				List<Integer> recipients = asset.getPartNotifications().stream()
						.filter(p -> p.getNotifyOnMinQty() == Boolean.TRUE)
						.map(p -> p.getUser().getId())
						.collect(Collectors.toList()); 
				
				return recipients;
			}
			
		} else if(model instanceof Stock) {
			Stock stockDTO = (Stock) model; 
			if (stockDTO.getStockNotifications() != null && stockDTO.getStockNotifications().size() > 0 ) {				
				List<Integer> recipients = stockDTO.getStockNotifications().stream()
						.filter(p -> p.getNotifyOnMinQty() == Boolean.TRUE)
						.map(p -> p.getUser().getId())
						.collect(Collectors.toList()); 
				
				return recipients;
			}
		}
		return new ArrayList<>();
	}

	@Override
	public List<Integer> getStockMinQtyNotifyRecipients(BaseDTO dto) {
		if (dto instanceof PartDTO) {
			PartDTO partDTO = (PartDTO) dto; 
			if (partDTO.getPartNotificationDTOs() != null && partDTO.getPartNotificationDTOs().size() > 0 ) {				
				List<Integer> recipients = partDTO.getPartNotificationDTOs().stream()
						.filter(p -> p.getNotifyOnMinQty() == Boolean.TRUE)
						.map(p -> p.getUserId())
						.collect(Collectors.toList()); 
				
				return recipients;
			}
			
		} else if(dto instanceof StockDTO) {
			StockDTO stockDTO = (StockDTO) dto; 
			if (stockDTO.getStockNotificationDTOs() != null && stockDTO.getStockNotificationDTOs().size() > 0 ) {				
				List<Integer> recipients = stockDTO.getStockNotificationDTOs().stream()
						.filter(p -> p.getNotifyOnMinQty() == Boolean.TRUE)
						.map(p -> p.getUserId())
						.collect(Collectors.toList()); 
				
				return recipients;
			}
		}
		return new ArrayList<>();
	}

}
