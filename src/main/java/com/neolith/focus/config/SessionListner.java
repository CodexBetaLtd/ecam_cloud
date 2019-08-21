package com.neolith.focus.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListner implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setMaxInactiveInterval(60 * 15);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
	}

}