package com.codex.ecam.listeners.inventory.receiptorder;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.listeners.LogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.service.log.LogSupport;

public class ReceiptOrderLogListener extends LogListener<ReceiptOrder> {

    @Autowired
    @Qualifier("rfqLogService")
    private LogSupport logSupport;

    @Override
    public LogSupport getService() {
        AutowireHelper.autowire(this, logSupport);
        return logSupport;
    }

    @Override
    public String getNotes() {
        return "ReceiptOrder";
    }

    @Override
    public BaseModel getLogDomain(ReceiptOrder domain) {
        return domain;
    }

    @PrePersist
    @Override
    public void postPersist(BaseModel model) {
        super.postPersist(model);
    }

    @PreUpdate
    @Override
    public void postUpdate(BaseModel model) {
        super.postUpdate(model);
    }

    @PreRemove
    @Override
    public void postRemove(BaseModel model) {
        super.postRemove(model);
    }

}
