package com.neolith.focus.model.maintenance.scheduledmaintenance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.neolith.focus.constants.LogType;
import com.neolith.focus.model.BaseModel;

@Entity
@Table( name = "tbl_scheduled_maintenance_log" )
public class ScheduledMaintenanceLog extends BaseModel {

	private static final long serialVersionUID = -4797324804028702229L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="scheduled_maintenance_log_s")
	@SequenceGenerator(name="scheduled_maintenance_log_s", sequenceName="scheduled_maintenance_log_s", allocationSize=1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "scheduled_maintenance_id")
	@ManyToOne( targetEntity = ScheduledMaintenance.class, fetch = FetchType.LAZY)
	private ScheduledMaintenance scheduledMaintenance;

	@Column(name = "log_type_id")
	private LogType logType;

	@Column(name="notes")
	private String notes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ScheduledMaintenance getScheduledMaintenance() {
		return scheduledMaintenance;
	}

	public void setScheduledMaintenance(ScheduledMaintenance scheduledMaintenance) {
		this.scheduledMaintenance = scheduledMaintenance;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
