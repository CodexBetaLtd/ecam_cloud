package com.codex.ecam.service.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.LogType;
import com.codex.ecam.dao.inventory.RFQChangeLogDao;
import com.codex.ecam.dao.inventory.RFQDao;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.inventory.rfq.RFQ;
import com.codex.ecam.model.inventory.rfq.RFQChangeLog;
import com.codex.ecam.service.log.LogSupport;
import com.codex.ecam.service.log.api.RFQLogService;

@Service
@Qualifier("rfqLogService")
public class RFQLogServiceImpl implements LogSupport, RFQLogService {

	@Autowired
	private RFQChangeLogDao changeLogDao;

	@Autowired
	private RFQDao rfqDao;

	@Override
	public void createPersistLog(BaseModel model, String notes) {
		if (model instanceof RFQ) {
			addRFQChangeLog((RFQ) model, notes, LogType.CREATE);
		}
	}

	@Override
	public void createUpdateLog(BaseModel model, String notes) {
		if (model instanceof RFQ) {
			addRFQChangeLog((RFQ) model, notes, LogType.UPDATE);
		}
	}

	@Override
	public void createRemoveLog(BaseModel model, String notes) {
		if (model instanceof RFQ) {
			addRFQChangeLog((RFQ) model, notes, LogType.REMOVE);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void addRFQChangeLog(RFQ rfq, String notes, LogType type) {
        RFQChangeLog log = createLogRecord(rfq, notes, type);
        if (log != null) {
        	changeLogDao.save(log);
		}
	}

	private RFQChangeLog createLogRecord(RFQ rfq, String notes, LogType type) {
        if ((rfq != null) && (rfq.getId() != null) && (rfq.getId() > 0)) {
            RFQChangeLog log = new RFQChangeLog();
            RFQ rfq2 = rfqDao.findOne(rfq.getId());
            if (rfq2 != null) {                    log.setRfq(rfq2);
                    log.setDescription(notes);
                    log.setRfqStatus(rfq.getRfqStatus());
                    log.setLogType(type);
                    log.setIsDeleted(false);
                }
                return log;
            }
        
        return null;
	}


}
