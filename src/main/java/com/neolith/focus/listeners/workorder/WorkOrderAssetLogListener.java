package com.neolith.focus.listeners.workorder;

import com.neolith.focus.config.AutowireHelper;
import com.neolith.focus.listeners.LogListener;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.maintenance.workorder.WorkOrderAsset;
import com.neolith.focus.service.log.LogSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class WorkOrderAssetLogListener extends LogListener<WorkOrderAsset>{
	
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
		return "Workorder Asset";
	}

	@Override
	public  BaseModel getLogDomain(WorkOrderAsset domain) {
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
