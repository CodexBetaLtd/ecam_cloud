package com.codex.ecam.service.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.LogType;
import com.codex.ecam.dao.inventory.PurchaseOrderChangeLogDao;
import com.codex.ecam.dao.inventory.PurchaseOrderDao;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrder;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderChangeLog;
import com.codex.ecam.service.log.LogSupport;
import com.codex.ecam.service.log.api.PurchaseOrderChangeLogService;

@Service
@Qualifier("purchaseOrderLogService")
public class PurchaseOrderChangeLogServiceImpl implements LogSupport, PurchaseOrderChangeLogService {

	@Autowired
	private PurchaseOrderChangeLogDao changeLogDao;

	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Override
	public void createPersistLog(BaseModel model, String notes) {
		if (model instanceof PurchaseOrder) {
			addPurchaseOrderChangeLog((PurchaseOrder) model, notes, LogType.CREATE);
		}
	}

	@Override
	public void createUpdateLog(BaseModel model, String notes) {
		if (model instanceof PurchaseOrder) {
			addPurchaseOrderChangeLog((PurchaseOrder) model, notes, LogType.UPDATE);
		}
	}

	@Override
	public void createRemoveLog(BaseModel model, String notes) {
		if (model instanceof PurchaseOrder) {
			addPurchaseOrderChangeLog((PurchaseOrder) model, notes, LogType.REMOVE);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void addPurchaseOrderChangeLog(PurchaseOrder purchaseOrder, String notes, LogType type) {
        PurchaseOrderChangeLog log = createLogRecord(purchaseOrder, notes, type);
        if (log != null) {
        	changeLogDao.save(log);
		}
	}

	private PurchaseOrderChangeLog createLogRecord(PurchaseOrder purchaseOrder, String notes, LogType type) {
        if ((purchaseOrder != null) && (purchaseOrder.getId() != null) && (purchaseOrder.getId() > 0)) {
        	PurchaseOrderChangeLog log = new PurchaseOrderChangeLog();
        	PurchaseOrder purchaseOrder2 = purchaseOrderDao.findOne(purchaseOrder.getId());
            if (purchaseOrder2 != null) {                   
            		log.setPurchaseOrder(purchaseOrder2);
                    log.setDescription(notes);
                    log.setStatus(purchaseOrder.getPurchaseOrderStatus());
                    log.setLogType(type);
                    log.setIsDeleted(false);
                }
                return log;
            }
        
        return null;
	}


}
