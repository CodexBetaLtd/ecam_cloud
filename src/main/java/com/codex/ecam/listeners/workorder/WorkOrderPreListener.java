package com.codex.ecam.listeners.workorder;

import java.util.Date;

import javax.persistence.PrePersist;

import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;
import com.codex.ecam.model.maintenance.workorder.WorkOrderNotes; 

public class WorkOrderPreListener { 

	@PrePersist
	public void fireAgreementTriggers(WorkOrder domain) throws Exception { 
		WorkOrderNotes workOrderNote = new WorkOrderNotes();
		workOrderNote.setNotes("Work Order Start Status Set as " + WorkOrderStatus.OPEN);
		workOrderNote.setNoteDate(new Date());
		workOrderNote.setWorkOrder(domain);
		workOrderNote.setWorkOrderStatus(WorkOrderStatus.OPEN);
		workOrderNote.setIsDeleted(false);			
		domain.getWorkOrderNotes().add(workOrderNote);
		domain.setCurrentStatus(workOrderNote); 
	}
	
}
