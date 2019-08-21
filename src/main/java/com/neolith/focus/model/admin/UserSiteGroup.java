package com.neolith.focus.model.admin;

import com.neolith.focus.model.BaseModel;

import javax.persistence.*;

@Entity
@Table(name="tbl_user_site_group")
public class UserSiteGroup extends BaseModel{

    private static final long serialVersionUID = -619248322936018714L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="user_site_group_s")
	@SequenceGenerator(name="user_site_group_s", sequenceName="user_site_group_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn(name="user_site_id")
    @ManyToOne(targetEntity = UserSite.class, fetch = FetchType.LAZY)
    private UserSite userSite;

    @JoinColumn(name="group_id")
	@ManyToOne(targetEntity=UserGroup.class,fetch=FetchType.LAZY)
	private UserGroup userGroup;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserSite getUserSite() {
		return userSite;
	}

	public void setUserSite(UserSite userSite) {
		this.userSite = userSite;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

}
