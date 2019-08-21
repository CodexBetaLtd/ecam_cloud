package com.codex.ecam.model.api;

import com.codex.ecam.model.BaseModel;

/**
 * Defines the RootAware. 
 */
public interface RootAware<T extends BaseModel>{
	
	/**
	 * Resolve the root entity for any Child Entity.. 
	 */
	T root();
}
