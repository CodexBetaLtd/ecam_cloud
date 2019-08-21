package com.neolith.focus.listeners.inventory.stock;

import com.neolith.focus.config.AutowireHelper;
import com.neolith.focus.model.inventory.stock.Stock;
import com.neolith.focus.service.inventory.api.PartNotificationService;
import com.neolith.focus.service.inventory.api.StockNotificationService;
import com.neolith.focus.service.inventory.api.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
