package com.codex.ecam.model.api;

import org.springframework.context.ApplicationEvent; 

/**
 * Defines the Application Event for the RootAware. 
 */
public interface RootApplicationEventAware {
	
	/**
	 * Return the Application Event of RootAware
	 */
	ApplicationEvent getEvent();
	
}
