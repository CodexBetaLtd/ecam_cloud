package com.codex.ecam.model.maintenance.project;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="tbl_project")
public class Project extends BaseModel {

	private static final long serialVersionUID = -5946273453257239589L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "project_s")
	@SequenceGenerator(name = "project_s", sequenceName = "project_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "parent_project_id")
	@ManyToOne(targetEntity = Project.class, fetch = FetchType.LAZY)
	private Project parentProject;

	@JoinColumn(name = "business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn(name = "site_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset site;

	@Column(name = "actual_end_date")
	private Date actualEndDate;

	@Column(name = "actual_start_date")
	private Date actualStartDate;

	@Column(name = "projected_end_date")
	private Date projectedEndDate;

	@Column(name = "projected_start_date")
	private Date projectedStartDate;

	@Column(name = "description")
	private String description;

	@Column(name = "name")
	private String name;

	@Column(name = "notes")
	private String notes;

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<ProjectUser> projectUsers;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Project getParentProject() {
		return parentProject;
	}

	public void setParentProject(Project parentProject) {
		this.parentProject = parentProject;
	}

	public Asset getSite() {
		return site;
	}

	public void setSite(Asset site) {
		this.site = site;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public Date getProjectedStartDate() {
		return projectedStartDate;
	}

	public void setProjectedStartDate(Date projectedStartDate) {
		this.projectedStartDate = projectedStartDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Date getProjectedEndDate() {
		return projectedEndDate;
	}

	public void setProjectedEndDate(Date projectedEndDate) {
		this.projectedEndDate = projectedEndDate;
	}

	public Set<ProjectUser> getProjectUsers() {
		return projectUsers;
	}

    public void setProjectUsers(Set<ProjectUser> projectUsers) {
        updateCollection("projectUsers", projectUsers);
	}
}
