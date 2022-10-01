package com.codex.ecam.dto.maintenance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codex.ecam.dto.BaseDTO;
import com.codex.ecam.dto.admin.UserDTO;

public class ProjectDTO extends BaseDTO {

    private Integer id;
    private Integer siteId;         // Todo: Not in used
    private Integer businessId;

    private Integer parentProjectId;
    private String parentProjectName;

    private String description;
    private String name;
    private String notes;
    private String siteName;
    private String businessName;

    private Date actualEndDate;
    private Date actualStartDate;
    private Date projectedEndDate;
    private Date projectedStartDate;

    private List<UserDTO> users = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentProjectId() {
        return parentProjectId;
    }

    public void setParentProjectId(Integer parentProjectId) {
        this.parentProjectId = parentProjectId;
    }

    public String getParentProjectName() {
        return parentProjectName;
    }

    public void setParentProjectName(String parentProjectName) {
        this.parentProjectName = parentProjectName;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
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

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getProjectedEndDate() {
        return projectedEndDate;
    }

    public void setProjectedEndDate(Date projectedEndDate) {
        this.projectedEndDate = projectedEndDate;
    }

    public Date getProjectedStartDate() {
        return projectedStartDate;
    }

    public void setProjectedStartDate(Date projectedStartDate) {
        this.projectedStartDate = projectedStartDate;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

}