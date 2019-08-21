package com.codex.ecam.model.maintenance;

import java.util.Date;

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

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTrigger;

@Entity
@Table(name = "tbl_calendar_event")
public class CalendarEvent extends BaseModel {

	private static final long serialVersionUID = 6142528531718217248L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "calendar_event_s")
	@SequenceGenerator(name = "calendar_event_s", sequenceName = "calendar_event_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "scheduled_trigger_id")
	@ManyToOne(targetEntity = ScheduledMaintenanceTrigger.class, fetch = FetchType.LAZY)
	private ScheduledMaintenanceTrigger scheduledMaintenanceTrigger;

	@Column(name = "scheduled_date")
	private Date scheduledDate;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public ScheduledMaintenanceTrigger getScheduledMaintenanceTrigger() {
		return scheduledMaintenanceTrigger;
	}

	public void setScheduledMaintenanceTrigger(ScheduledMaintenanceTrigger scheduledMaintenanceTrigger) {
		this.scheduledMaintenanceTrigger = scheduledMaintenanceTrigger;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

}
