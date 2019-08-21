package com.neolith.focus.listeners.inventory.part;

import com.neolith.focus.config.AutowireHelper;
import com.neolith.focus.event.RootAwareEvent;
import com.neolith.focus.service.inventory.api.PartNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

public class PartNotificationChildListEventListener implements ApplicationListener<RootAwareEvent>{

	@Autowired
    private PartNotificationService partNotificationService;

	@Override
	public void onApplicationEvent(RootAwareEvent event) {
        AutowireHelper.autowire(this, partNotificationService);
    }

}
