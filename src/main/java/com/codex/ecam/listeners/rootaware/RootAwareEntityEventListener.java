package com.codex.ecam.listeners.rootaware;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.event.spi.AbstractEvent;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.codex.ecam.config.AutowireHelper;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.api.RootApplicationEventAware;
import com.codex.ecam.model.api.RootAware;

public abstract class RootAwareEntityEventListener implements PostUpdateEventListener, PostInsertEventListener, PostDeleteEventListener {

	private static final long serialVersionUID = 8375408341597223073L;

	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * 	To intercept the UPDATE SQL events
	 *  @param root Root Entity of child entity
	 *  @param event used to get PostUpdateEvent
	 *  @param entity Event Entity of child entity
	 */
	public abstract void addUpdateLog(Object root, PostUpdateEvent event, Object entity);

	/**
	 * 	To intercept the PERSIST SQL events
	 *  @param root Root Entity of child entity
	 *  @param event used to get PostInsertEvent
	 *  @param entity Event Object of child entity
	 */
	public abstract void addInsertLog(Object root, PostInsertEvent event, Object entity);

	/**
	 * 	To intercept the Delete SQL events
	 *  @param root Root Entity of child entity
	 *  @param event used to get PostDeleteEvent
	 *  @param entity Event Object of child entity
	 */
	public abstract void addDeleteLog(Object root, PostDeleteEvent event, Object entity);

	/**
	 * LockMode The lock mode to used for a specific query alias.
	 */
	public abstract LockMode getLockMode();

	/**
	 * publish the given create event
	 * @param root used to get Application Event
	 */
	public abstract void publishRootEvent(Object root);

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		onEventFire(event);
	}

	@Override
	public void onPostDelete(PostDeleteEvent event) {
		onEventFire(event);
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
		onEventFire(event);
	}

	@Override
	public boolean requiresPostCommitHanding(EntityPersister persister) {
		return true;
	}

	@SuppressWarnings("unchecked")
	protected void onEventFire(AbstractEvent event) {
		final Object entity = getEntityByEventType(event);

		if(entity instanceof RootAware) {
			RootAware<BaseModel> rootAware = (RootAware<BaseModel>) entity;
			Object root = rootAware.root();
			event.getSession().buildLockRequest(new LockOptions(getLockMode())).lock(root);
			publishRootEvent(root);
			addLog(root, event, entity);
		}
	}

	protected void addLog (Object root, AbstractEvent event, Object entity) {
		if ( event instanceof PostInsertEvent ) {
			addInsertLog(root, (PostInsertEvent) event, entity);
		} else if ( event instanceof PostUpdateEvent ) {
			addUpdateLog(root, (PostUpdateEvent) event, entity);
		} else if (event instanceof PostDeleteEvent ) {
			addDeleteLog(root, (PostDeleteEvent) event, entity);
		}
	}

	protected void onPublish(Object root) {
		AutowireHelper.autowire(this, publisher);
		if (root instanceof RootApplicationEventAware ) {
			publisher.publishEvent(((RootApplicationEventAware)root).getEvent());
		}
	}

	protected Object getEntityByEventType (AbstractEvent event) {
		if ( event instanceof PostInsertEvent ) {
			return ((PostInsertEvent) event).getEntity();
		} else if ( event instanceof PostUpdateEvent ) {
			return ((PostUpdateEvent) event).getEntity();
		} else if (event instanceof PostDeleteEvent ) {
			return ((PostDeleteEvent) event).getEntity();
		}

		return null;

	}

}
