package com.codex.ecam.service.log.impl.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.LogType;
import com.codex.ecam.dao.inventory.mrn.MRNDao;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.inventory.mrn.MRN;
import com.codex.ecam.service.log.LogSupport;
import com.codex.ecam.service.log.api.inventory.MRNLogService;

@Service
@Qualifier("mrnLogService")
public class MRNLogServiceImpl implements LogSupport, MRNLogService {

	@Override
	public void createPersistLog(BaseModel model, String notes) {
		if (model instanceof MRN) {
			addMRNChangeLog((MRN) model, notes, LogType.CREATE);
		}
	}

	@Override
	public void createUpdateLog(BaseModel model, String notes) {
		if (model instanceof MRN) {
			addMRNChangeLog((MRN) model, notes, LogType.UPDATE);
		}
	}

	@Override
	public void createRemoveLog(BaseModel model, String notes) {
		if (model instanceof MRN) {
			addMRNChangeLog((MRN) model, notes, LogType.REMOVE);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void addMRNChangeLog(MRN mrn, String notes, LogType type) {

	}

}
