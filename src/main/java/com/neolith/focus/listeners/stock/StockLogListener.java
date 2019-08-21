package com.neolith.focus.listeners.stock;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.neolith.focus.config.AutowireHelper;
import com.neolith.focus.listeners.LogListener;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.inventory.stock.Stock;
import com.neolith.focus.service.log.LogSupport;

public class StockLogListener extends LogListener<Stock> {

	@Autowired
	@Qualifier("stockLogService")
	private LogSupport logSupport;

	@Override
	public  LogSupport getService() {
		AutowireHelper.autowire(this, logSupport);
		return logSupport;
	}

	@Override
	public  String getNotes() {
		return "Stock ";
	}

	@Override
	public  BaseModel getLogDomain(Stock domain) {
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
