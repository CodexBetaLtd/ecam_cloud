package com.codex.ecam.listeners;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.AssetEvent;
import com.codex.ecam.model.asset.AssetMeterReadingValue;
import com.codex.ecam.service.maintenance.api.ScheduledService;

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
