package com.codex.ecam.model.maintenance.scheduledmaintenance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.listeners.scheduledmaintenance.ScheduledMaintenancePartLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.inventory.stock.Stock;

@Entity
@Table(name = "tbl_scheduled_maintenance_part")
@EntityListeners( ScheduledMaintenancePartLogListener.class )
public class ScheduledMaintenancePart extends BaseModel {

	private static final long serialVersionUID = -8001048613089490024L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "scheduled_maintenance_part_s")
	@SequenceGenerator(name = "scheduled_maintenance_part_s", sequenceName = "scheduled_maintenance_part_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "scheduled_maintenance_task_id")
	@ManyToOne(targetEntity = ScheduledMaintenanceTask.class, fetch = FetchType.LAZY )
	private ScheduledMaintenanceTask scheduledMaintenanceTask;

	@JoinColumn(name = "part_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset part;

	@Column(name = "suggested_quantity")
	private Double suggestedQuantity;

	@JoinColumn(name = "stock_id")
	@ManyToOne(targetEntity = Stock.class, fetch = FetchType.LAZY)
	private Stock stock;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Asset getPart() {
		return part;
	}

	public void setPart(Asset part) {
		this.part = part;
	}

	public Double getSuggestedQuantity() {
		return suggestedQuantity;
	}

	public void setSuggestedQuantity(Double suggestedQuantity) {
		this.suggestedQuantity = suggestedQuantity;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public ScheduledMaintenanceTask getScheduledMaintenanceTask() {
		return scheduledMaintenanceTask;
	}

	public void setScheduledMaintenanceTask(ScheduledMaintenanceTask scheduledMaintenanceTask) {
		this.scheduledMaintenanceTask = scheduledMaintenanceTask;
	}

}
