package com.neolith.focus.event;

import org.springframework.context.ApplicationEvent;

public class RootAwareEvent extends ApplicationEvent {

	private static final long serialVersionUID = -8766643595495182485L;

	public RootAwareEvent(Object source) {
		super(source); 
	}

}
