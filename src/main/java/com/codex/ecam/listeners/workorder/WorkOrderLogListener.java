package com.codex.ecam.listeners.workorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.listeners.LogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.service.log.LogSupport;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class WorkOrderLogListener extends LogListener<WorkOrder>{
	
	@Autowired
	@Qualifier("woLogService")
	private LogSupport logSupport;

	@Override
	public  LogSupport getService() {
		AutowireHelper.autowire(this, this.logSupport);
		return logSupport;
	}

	@Override
	public  String getNotes() {
		return "WorkOrder ";
	}

	@Override
	public  BaseModel getLogDomain(WorkOrder domain) {
		return domain;
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
	}
	
}
