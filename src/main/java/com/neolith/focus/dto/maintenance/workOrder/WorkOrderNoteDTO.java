package com.neolith.focus.dto.maintenance.workOrder;
 
import com.neolith.focus.constants.WorkOrderStatus;
import com.neolith.focus.dto.BaseDTO; 

public class WorkOrderNoteDTO extends BaseDTO {

	private Integer id; 
	private Integer version;
	private Integer woUserId;

	private String woNoteDate;
	private String woNote;
	private String woUserName;
	
	private WorkOrderStatus workOrderStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	} 

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}   

	public Integer getWoUserId() {
		return woUserId;
	}

	public void setWoUserId(Integer woUserId) {
		this.woUserId = woUserId;
	}

	public String getWoNoteDate() {
		return woNoteDate;
	}

	public void setWoNoteDate(String woNoteDate) {
		this.woNoteDate = woNoteDate;
	}

	public String getWoNote() {
		return woNote;
	}

	public void setWoNote(String woNote) {
		this.woNote = woNote;
	}

	public String getWoUserName() {
		return woUserName;
	}

	public void setWoUserName(String woUserName) {
		this.woUserName = woUserName;
	}

	public WorkOrderStatus getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(WorkOrderStatus workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

}
