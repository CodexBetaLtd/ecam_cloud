package com.neolith.focus.listeners.workorder;

import com.neolith.focus.config.AutowireHelper;
import com.neolith.focus.listeners.LogListener;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.maintenance.workorder.WorkOrder;
import com.neolith.focus.service.log.LogSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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
