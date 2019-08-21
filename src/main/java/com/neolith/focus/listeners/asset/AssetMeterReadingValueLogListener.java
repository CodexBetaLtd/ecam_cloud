package com.neolith.focus.listeners.asset;

import com.neolith.focus.config.AutowireHelper;
import com.neolith.focus.listeners.LogListener;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.asset.AssetMeterReadingValue;
import com.neolith.focus.service.log.LogSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class AssetMeterReadingValueLogListener extends LogListener<AssetMeterReadingValue> {

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
        return "Asset Meter Reading Value";
    }

    @Override
    public BaseModel getLogDomain(AssetMeterReadingValue domain) {
        return domain.getAssetMeterReading().getAsset();
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
