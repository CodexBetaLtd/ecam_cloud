package com.codex.ecam.service.log;

import com.codex.ecam.model.BaseModel;

public interface LogSupport {

	void createPersistLog(BaseModel model, String notes);
	
	void createUpdateLog(BaseModel model, String notes);
	
	void createRemoveLog(BaseModel model, String notes);
	
}
