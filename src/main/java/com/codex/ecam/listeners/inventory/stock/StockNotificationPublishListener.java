package com.codex.ecam.listeners.inventory.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.service.inventory.api.PartNotificationService;
import com.codex.ecam.service.inventory.api.StockNotificationService;
import com.codex.ecam.service.inventory.api.StockService;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class StockNotificationPublishListener {

	@Autowired
    private StockNotificationService stockNotificationService;

    @Autowired
    private PartNotificationService partNotificationService;

    @Autowired
    private StockService stockService;

    @PostPersist
    public void publishStockAddNotification(Stock entity) throws Exception {
        AutowireHelper.autowire(this, stockNotificationService, partNotificationService, stockService);
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    try {
                        partNotificationService.publishStockAddNotifications(entity);
                        stockNotificationService.publishStockAddNotifications(entity);
                        publishMinQtyNotification(entity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

	@PostRemove
	public void publishStockRemoveNotification(Stock entity) throws Exception {
		AutowireHelper.autowire(this, stockNotificationService);
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    try {
                        stockNotificationService.publishStockRemoveNotifications(entity);
                        partNotificationService.publishStockRemoveNotifications(entity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @PostUpdate
    public void publishStockMinQtyNotification(Stock entity) throws Exception {
        AutowireHelper.autowire(this, stockNotificationService);
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    try {
                        publishMinQtyNotification(entity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

	}

	private void publishMinQtyNotification(Stock entity) throws Exception {
        if ((entity.getCurrentQuantity().compareTo(entity.getMinQuantity()) == 0) || (entity.getCurrentQuantity().compareTo(entity.getMinQuantity()) == -1)) {
            stockNotificationService.publishMinQtyNotifications(entity);
        }
	}

}
