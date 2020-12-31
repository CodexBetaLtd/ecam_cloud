package com.codex.ecam.listeners.inventory.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.event.RootAwareEvent;
import com.codex.ecam.service.inventory.api.PartNotificationService;

public class PartNotificationChildListEventListener implements ApplicationListener<RootAwareEvent>{

	@Autowired
    private PartNotificationService partNotificationService;

	@Override
	public void onApplicationEvent(RootAwareEvent event) {
        AutowireHelper.autowire(this, partNotificationService);
    }

}
