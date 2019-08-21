package com.neolith.focus.listeners.rootaware;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootAwareUpdateEventListener extends RootAwareEntityEventListener {

	private static final long serialVersionUID = -1737300408675861293L;

	private static final Logger LOGGER = LoggerFactory.getLogger(RootAwareUpdateEventListener.class);

	public static final RootAwareUpdateEventListener INSTANCE =  new RootAwareUpdateEventListener();

	@Override
	public void onPostUpdate(PostUpdateEvent event) throws HibernateException {
		super.onPostUpdate(event);
	}

	@Override
	public void addUpdateLog(Object root, PostUpdateEvent event, Object entity) {
		LOGGER.info( "Incrementing {} entity version because a {} child entity has been updated", root, entity);
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
	public void addInsertLog(Object root, PostInsertEvent event, Object entity) {
	}

	@Override
	public void addDeleteLog(Object root, PostDeleteEvent event, Object entity) {
	}

}
