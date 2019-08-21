package com.neolith.focus.listeners.scheduledmaintenance;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.neolith.focus.config.AutowireHelper;
import com.neolith.focus.listeners.LogListener;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenanceAsset;
import com.neolith.focus.service.log.LogSupport;

public class ScheduledMaintenanceAssetLogListener extends LogListener<ScheduledMaintenanceAsset>{

	@Autowired
	@Qualifier("smLogService")
	private LogSupport logSupport;

	@Override
	public  LogSupport getService() {
		AutowireHelper.autowire(this, logSupport);
		return logSupport;
	}

	@Override
	public  String getNotes() {
		return "Scheduled Maintenance Asset";
	}

	@Override
	public  BaseModel getLogDomain(ScheduledMaintenanceAsset domain) {
		return domain.getScheduledMaintenance();
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
