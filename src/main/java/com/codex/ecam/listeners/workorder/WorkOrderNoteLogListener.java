package com.codex.ecam.listeners.workorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.listeners.LogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.maintenance.workorder.WorkOrderNotes;
import com.codex.ecam.service.log.LogSupport;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class WorkOrderNoteLogListener extends LogListener<WorkOrderNotes> {

	@Autowired
	@Qualifier("woLogService")
	private LogSupport logSupport;
	
	@Override
	public LogSupport getService() {
		AutowireHelper.autowire(this, this.logSupport);
		return logSupport;
	}

	@Override
	public String getNotes() {
		return "Workorder Notes";
	}

	@Override
	public BaseModel getLogDomain(WorkOrderNotes domain) {
		return domain.getWorkOrder();
	}

	@PostPersist
	@Override
	public void postPersist(BaseModel model) {
		super.postPersist(model);
	}

	@PostUpdate
	@Override
	public void postUpdate(BaseModel model) {
		super.postUpdate(model);
	}

	@PostRemove
	@Override
	public void postRemove(BaseModel model) {
		super.postRemove(model);
	}

}
