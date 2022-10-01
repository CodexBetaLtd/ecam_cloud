package com.codex.ecam.service.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.LogType;
import com.codex.ecam.dao.maintenance.ScheduledMaintenanceDao;
import com.codex.ecam.dao.maintenance.ScheduledMaintenanceLogDao;
import com.codex.ecam.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceLogDTO;
import com.codex.ecam.mappers.maintenance.schedulemaintenance.ScheduledMaintenanceLogMapper;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenance;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceLog;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.log.LogSupport;
import com.codex.ecam.service.log.api.ScheduledMaintenanceLogService;

@Service
@Qualifier("smLogService")
public class ScheduledMaintenanceLogServiceImpl implements LogSupport, ScheduledMaintenanceLogService {

	@Autowired
	private ScheduledMaintenanceLogDao smLogDao;

	@Autowired
	private ScheduledMaintenanceDao smDao;

	@Override
	public void createPersistLog(BaseModel model, String notes) {
		if (model instanceof ScheduledMaintenance) {
			addScheduledMaintenanceLog((ScheduledMaintenance) model, notes, LogType.CREATE);
		}
	}

	@Override
	public void createUpdateLog(BaseModel model, String notes) {
		if (model instanceof ScheduledMaintenance) {
			addScheduledMaintenanceLog((ScheduledMaintenance) model, notes, LogType.UPDATE);
		}
	}

	@Override
	public void createRemoveLog(BaseModel model, String notes) {
		if (model instanceof ScheduledMaintenance) {
			addScheduledMaintenanceLog((ScheduledMaintenance) model, notes, LogType.REMOVE);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void addScheduledMaintenanceLog(ScheduledMaintenance sm, String notes, LogType type) {
		ScheduledMaintenanceLog log = createLogRecord(sm, notes, type);
		if (log != null) {
			smLogDao.save(log);
		}
	}

	private ScheduledMaintenanceLog createLogRecord(ScheduledMaintenance sm, String notes, LogType type) {
		if ((sm != null) && (sm.getId() != null) && (sm.getId() > 0)) {
			ScheduledMaintenanceLog log = new ScheduledMaintenanceLog();
			ScheduledMaintenance currentSm = smDao.findOne(sm.getId());
			if (currentSm != null) {
				log.setScheduledMaintenance(currentSm);
				log.setNotes(notes);
				log.setLogType(type);
				log.setIsDeleted(false);
				return log;
			}
		}
		return null;
	}

	@Override
	public DataTablesOutput<ScheduledMaintenanceLogDTO> findAllByScheduledMaintenanceId(FocusDataTablesInput input, Integer id) throws Exception {
		final Specification<ScheduledMaintenanceLog> specification = (root, query, cb) -> cb.equal(root.get("scheduledMaintenance").get("id"), id);
		final DataTablesOutput<ScheduledMaintenanceLog> domainOut = smLogDao.findAll(input, specification);
		final DataTablesOutput<ScheduledMaintenanceLogDTO> out = ScheduledMaintenanceLogMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

}
