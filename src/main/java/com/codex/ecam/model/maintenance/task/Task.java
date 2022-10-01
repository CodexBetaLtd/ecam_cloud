package com.codex.ecam.model.maintenance.task;

import javax.persistence.*;

import com.codex.ecam.constants.TaskType;
import com.codex.ecam.model.BaseModel;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_task")
public class Task extends BaseModel {

    private static final long serialVersionUID = -4670404090778841597L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "task_s")
    @SequenceGenerator(name = "task_s", sequenceName = "task_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "estimated_hours")
    private BigDecimal estimatedHours;

    @Column(name = "task_type")
    private TaskType taskType;

    @JoinColumn(name = "task_group_id")
    @ManyToOne(targetEntity = TaskGroup.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private TaskGroup taskGroup;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(BigDecimal estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public TaskGroup getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(TaskGroup taskGroup) {
        this.taskGroup = taskGroup;
    }
}
