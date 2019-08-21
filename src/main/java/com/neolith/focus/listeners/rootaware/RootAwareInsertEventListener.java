package com.neolith.focus.listeners.rootaware;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootAwareInsertEventListener extends RootAwareEntityEventListener {

	private static final long serialVersionUID = 382378200784149666L;

	private static final Logger LOGGER = LoggerFactory.getLogger(RootAwareInsertEventListener.class);

	public static final RootAwareInsertEventListener INSTANCE = new RootAwareInsertEventListener();

	@Override
	public void onPostInsert(PostInsertEvent event) throws HibernateException {
		super.onPostInsert(event);
	}

	@Override
	public void addInsertLog(Object root, PostInsertEvent event, Object entity) {
		LOGGER.info("Incrementing {} entity version because a {} child entity has been inserted", root, entity);
	}

	@Override
	public LockMode getLockMode() {
		return LockMode.OPTIMISTIC_FORCE_INCREMENT;
	}

	@Override
	public void publishRootEvent(Object root) {
		super.onPublish(root);
	}

	@Override
	public void addUpdateLog(Object root, PostUpdateEvent event, Object entity) {
	}

	@Override
	public void addDeleteLog(Object root, PostDeleteEvent event, Object entity) {
	}

}
