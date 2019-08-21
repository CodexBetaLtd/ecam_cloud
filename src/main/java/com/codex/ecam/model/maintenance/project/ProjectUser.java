package com.codex.ecam.model.maintenance.project;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;

@Entity
@Table(name = "tbl_project_user")
public class ProjectUser extends BaseModel {

    private static final long serialVersionUID = -6444040440416920685L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "project_user_s")
    @SequenceGenerator(name = "project_user_s", sequenceName = "project_user_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "project_id")
    @ManyToOne(targetEntity = Project.class, fetch = FetchType.LAZY)
    private Project project;

    @JoinColumn(name = "user_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
