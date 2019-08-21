package com.neolith.focus.service.log;

import com.neolith.focus.model.BaseModel;

public interface LogSupport {

	void createPersistLog(BaseModel model, String notes);
	
	void createUpdateLog(BaseModel model, String notes);
	
	void createRemoveLog(BaseModel model, String notes);
	
}
