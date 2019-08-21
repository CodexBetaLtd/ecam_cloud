package com.neolith.focus.service.log.impl;

import com.neolith.focus.constants.LogType;
import com.neolith.focus.dao.maintenance.WorkOrderDao;
import com.neolith.focus.dao.maintenance.WorkOrderLogDao;
import com.neolith.focus.dto.maintenance.workOrder.WorkOrderLogDTO;
import com.neolith.focus.mappers.maintenance.workorder.WorkOrderLogMapper;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.maintenance.workorder.WorkOrder;
import com.neolith.focus.model.maintenance.workorder.WorkOrderLog;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.log.LogSupport;
import com.neolith.focus.service.log.api.WorkOrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("woLogService")
public class WorkOrderLogServiceImpl implements LogSupport, WorkOrderLogService {

	@Autowired
	private WorkOrderLogDao workOrderLogDao;

	@Autowired
	private WorkOrderDao workOrderDao;

	@Override
	public void createPersistLog(BaseModel model, String notes) {
		if (model instanceof WorkOrder) {
			addWorkOrderLog((WorkOrder) model, notes, LogType.CREATE);
		}
	}

	@Override
	public void createUpdateLog(BaseModel model, String notes) {
		if (model instanceof WorkOrder) {
			addWorkOrderLog((WorkOrder) model, notes, LogType.UPDATE);
		}
	}

	@Override
	public void createRemoveLog(BaseModel model, String notes) {
		if (model instanceof WorkOrder) {
			addWorkOrderLog((WorkOrder) model, notes, LogType.REMOVE);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void addWorkOrderLog(WorkOrder workOrder, String notes, LogType type) {
        WorkOrderLog log = createLogRecord(workOrder, notes, type);
        if (log != null) {
            workOrderLogDao.save(log);
		}
	}

	private WorkOrderLog createLogRecord(WorkOrder workOrder, String notes, LogType type) {
        if ((workOrder != null) && (workOrder.getId() != null) && (workOrder.getId() > 0)) {
            WorkOrderLog log = new WorkOrderLog();
            WorkOrder wod = workOrderDao.findOne(workOrder.getId());
            if (wod != null) {
                log.setWorkOrder(wod);
                log.setNotes(notes);
                log.setLogType(type);
                log.setIsDeleted(false);
                return log;
            }
        }
        return null;
	}

	@Override
	public DataTablesOutput<WorkOrderLogDTO> findAllByWorkOrderId(FocusDataTablesInput input, Integer id) throws Exception {
		final Specification<WorkOrderLog> specification = (root, query, cb) -> cb.equal(root.get("workOrder").get("id"), id);
		final DataTablesOutput<WorkOrderLog> domainOut = workOrderLogDao.findAll(input, specification);
		final DataTablesOutput<WorkOrderLogDTO> out = WorkOrderLogMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

}
