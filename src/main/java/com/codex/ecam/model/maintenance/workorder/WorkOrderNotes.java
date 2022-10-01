package com.codex.ecam.model.maintenance.workorder;

import java.util.Date;

import javax.persistence.*;

import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.listeners.workorder.WorkOrderNoteLogListener;
import com.codex.ecam.model.BaseModel;

@Entity
@Table(name="tbl_wo_notes")
@EntityListeners({WorkOrderNoteLogListener.class})
public class WorkOrderNotes extends BaseModel{
	
	private static final long serialVersionUID = 4869210211494401226L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="wo_notes_s")
	@SequenceGenerator(name="wo_notes_s", sequenceName="wo_notes_s", allocationSize=1)
	@Column(name="id")
    private Integer id;
	
	@Column(name = "notes")
	private String notes; 
	
	@JoinColumn(name = "wo_id")
	@ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
	private WorkOrder  workOrder;
	
	@Column(name = "note_date")
	@Temporal(TemporalType.DATE)
	private Date noteDate;
	
	@Column(name = "status")
	private WorkOrderStatus workOrderStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	} 

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public Date getNoteDate() {
		return noteDate;
	}

	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}

	public WorkOrderStatus getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(WorkOrderStatus workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	} 

}
