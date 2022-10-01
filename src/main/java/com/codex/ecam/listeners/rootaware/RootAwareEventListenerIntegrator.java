package com.codex.ecam.listeners.rootaware;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class RootAwareEventListenerIntegrator implements Integrator {

	public static final RootAwareEventListenerIntegrator INSTANCE = new RootAwareEventListenerIntegrator();

	@Override
	public void disintegrate( SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
	}

	@Override
	public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
		final EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
		eventListenerRegistry.appendListeners( EventType.POST_INSERT, RootAwareInsertEventListener.INSTANCE );
		eventListenerRegistry.appendListeners( EventType.POST_UPDATE, RootAwareUpdateEventListener.INSTANCE );
		eventListenerRegistry.appendListeners( EventType.POST_DELETE, RootAwareDeleteEventListener.INSTANCE );
	}

	@Override
	public void integrate(MetadataImplementor metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

	}

}
