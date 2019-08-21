package com.codex.ecam.listeners;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.service.log.LogSupport;

public abstract class LogListener<DOMAIN extends BaseModel> {

	/**
     * get @Autowired log service 
     */
	public abstract LogSupport getService();
	
	
	/**
     * get log notes 
     */
	public abstract String getNotes();
	
	
    /**
     * @param domain Log Domain		
     * 
     */
	public abstract BaseModel getLogDomain(DOMAIN domain);
	
	
    /**
     * Transform model object into domain object
     * @param model    BaseModel passed by from Entity Listener
     * @return DOMAIN.
     */
    @SuppressWarnings("unchecked")
    private DOMAIN getDomain(BaseModel model) {
        return (DOMAIN) model;
	}

    public void postPersist(BaseModel model) {
        getService().createPersistLog(getLogDomain(getDomain(model)), getNotes() + " Added");
    }

    public void postUpdate(BaseModel model) {
        getService().createUpdateLog(getLogDomain(getDomain(model)), getNotes() + " Updated");
    }

    public void postRemove(BaseModel model) {
        getService().createRemoveLog(getLogDomain(getDomain(model)), getNotes() + " Removed");
    }

}
