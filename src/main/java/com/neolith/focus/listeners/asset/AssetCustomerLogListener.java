package com.neolith.focus.listeners.asset;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.neolith.focus.config.AutowireHelper;
import com.neolith.focus.listeners.LogListener;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.asset.AssetBusiness;
import com.neolith.focus.service.log.LogSupport;

public class AssetCustomerLogListener extends LogListener<AssetBusiness> {

	@Autowired
	@Qualifier("assetLogService")
	private LogSupport logSupport;

	@Override
	public  LogSupport getService() {
		AutowireHelper.autowire(this, logSupport);
		return logSupport;
	}

	@Override
	public  String getNotes() {
		return "Asset Customer ";
	}

	@Override
	public  BaseModel getLogDomain(AssetBusiness domain) {
		return domain.getAsset();
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
