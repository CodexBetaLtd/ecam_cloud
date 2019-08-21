package com.codex.ecam.model.maintenance.task;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;

@Entity
@Table(name = "tbl_task_group")
public class TaskGroup extends BaseModel {

	private static final long serialVersionUID = -2119517187620687881L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "task_group_s")
	@SequenceGenerator(name = "task_group_s", sequenceName = "task_group_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn(name = "site_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset site;

	@Column(name = "name")
	private String name;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "taskGroup", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<Task> tasks;

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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		updateCollection("tasks", tasks);
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Asset getSite() {
		return site;
	}

	public void setSite(Asset site) {
		this.site = site;
	}
}
