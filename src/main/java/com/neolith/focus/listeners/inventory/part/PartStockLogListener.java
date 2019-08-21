package com.neolith.focus.listeners.inventory.part;

import com.neolith.focus.config.AutowireHelper;
import com.neolith.focus.listeners.LogListener;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.inventory.stock.Stock;
import com.neolith.focus.service.log.LogSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class PartStockLogListener extends LogListener<Stock> {

    @Autowired
    @Qualifier("assetLogService")
    private LogSupport logSupport;

    @Override
    public LogSupport getService() {
        AutowireHelper.autowire(this, logSupport);
        return logSupport;
    }

    @Override
    public String getNotes() {
        return "Stock ";
    }

    @Override
    public BaseModel getLogDomain(Stock domain) {
        return domain.getPart();
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
