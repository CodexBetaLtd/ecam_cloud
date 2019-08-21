package com.neolith.focus.listeners;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;

import com.neolith.focus.config.AutowireHelper;
import com.neolith.focus.constants.SMTriggerType;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.asset.AssetEvent;
import com.neolith.focus.model.asset.AssetMeterReadingValue;
import com.neolith.focus.service.maintenance.api.ScheduledService;

public class ScheduleTriggerEntityListener {

	@Autowired
	private ScheduledService scheduledService;

	@PostPersist
	@PostUpdate
	public void fireAssetTriggers(BaseModel entity) {
		AutowireHelper.autowire(this, scheduledService);
		if (entity != null) {
			if (entity instanceof AssetEvent) {
				scheduledService.notifyAssetTrigger(((AssetEvent) entity).getAssetEventTypeAsset().getAsset(), SMTriggerType.EVENT_TRIGGER);
			} else if (entity instanceof AssetMeterReadingValue) {
				scheduledService.notifyAssetTrigger(((AssetMeterReadingValue) entity).getAssetMeterReading().getAsset(), SMTriggerType.METER_READING_TRIGGER);
			}
		}
	}

}
