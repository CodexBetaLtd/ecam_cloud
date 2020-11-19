package com.codex.ecam.service.log.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.LogType;
import com.codex.ecam.dao.maintenance.WorkOrderDao;
import com.codex.ecam.dao.maintenance.WorkOrderLogDao;
import com.codex.ecam.dto.biz.notification.NotificationDTO;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderLogDTO;
import com.codex.ecam.mappers.maintenance.workorder.WorkOrderLogMapper;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.model.maintenance.workorder.WorkOrderLog;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.log.LogSupport;
import com.codex.ecam.service.log.api.WorkOrderLogService;
import com.codex.ecam.service.notification.api.NotificationService;
import com.codex.ecam.util.AuthenticationUtil;

@Service
@Qualifier("woLogService")
public class WorkOrderLogServiceImpl implements LogSupport, WorkOrderLogService {

	@Autowired
	private WorkOrderLogDao workOrderLogDao;

	@Autowired
	private WorkOrderDao workOrderDao;
	
	@Autowired
	private NotificationService notificationService;

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
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void notifyWorkorderUser (WorkOrder workorder,String note){
		NotificationDTO dto=new NotificationDTO();
		List<String> workorderUserList=new ArrayList<>();
		//if(workorder.getWorkOrderNotifications().!=null && workorder.getAssetUsers().size()>0){
		if(workorder.getCompletedByUser()!=null){
		workorderUserList.add(workorder.getCompletedByUser().getId().toString());
		}
		if(workorder.getCompletedByUser()!=null){
			workorderUserList.add(workorder.getCompletedByUser().getId().toString());
		}
		if(workorderUserList.size()>0){
				String res = String.join(",", workorderUserList);
				dto.setReceiverName(res);
				dto.setSubject(workorder.getCode()+" work order change identifyed ");
				dto.setContent(note+" Changes Excute by "+AuthenticationUtil.getAuthenticatedUser().getUserCredential().getUserName());
				dto.setIsSystemMessage(Boolean.TRUE);
				notificationService.saveNotification(dto);

		}
	}
}
