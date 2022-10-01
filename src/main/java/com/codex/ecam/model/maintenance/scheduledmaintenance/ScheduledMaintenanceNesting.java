package com.codex.ecam.model.maintenance.scheduledmaintenance;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_scheduled_maintenance_nesting")
public class ScheduledMaintenanceNesting extends BaseModel {

    private static final long serialVersionUID = 8507063725951684506L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "scheduled_maintenance_nesting_s")
    @SequenceGenerator(name = "scheduled_maintenance_nesting_s", sequenceName = "scheduled_maintenance_nesting_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "parent_id")
    @ManyToOne(targetEntity = ScheduledMaintenanceNesting.class, fetch = FetchType.LAZY)
    private ScheduledMaintenanceNesting parent;

    @JoinColumn(name = "scheduled_maintenance_id")
    @ManyToOne(targetEntity = ScheduledMaintenance.class, fetch = FetchType.LAZY)
    private ScheduledMaintenance scheduledMaintenance;

    @Column(name = "current_iteration_cycle")
    private Integer currentIterationCycle;

    @Column(name = "multiplier")
    private Integer multiplier;

    @Column(name = "name_identifier")
    private Integer nameIdentifier;

    @Column(name = "description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ScheduledMaintenanceNesting getParent() {
        return parent;
    }

    public void setParent(ScheduledMaintenanceNesting parent) {
        this.parent = parent;
    }

    public ScheduledMaintenance getScheduledMaintenance() {
        return scheduledMaintenance;
    }

    public void setScheduledMaintenance(ScheduledMaintenance scheduledMaintenance) {
        this.scheduledMaintenance = scheduledMaintenance;
    }

    public Integer getCurrentIterationCycle() {
        return currentIterationCycle;
    }

    public void setCurrentIterationCycle(Integer currentIterationCycle) {
        this.currentIterationCycle = currentIterationCycle;
    }

    public Integer getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Integer multiplier) {
        this.multiplier = multiplier;
    }

    public Integer getNameIdentifier() {
        return nameIdentifier;
    }

    public void setNameIdentifier(Integer nameIdentifier) {
        this.nameIdentifier = nameIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
