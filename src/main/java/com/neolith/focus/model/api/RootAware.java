package com.neolith.focus.model.api;

import com.neolith.focus.model.BaseModel;

/**
 * Defines the RootAware. 
 */
public interface RootAware<T extends BaseModel>{
	
	/**
	 * Resolve the root entity for any Child Entity.. 
	 */
	T root();
}
