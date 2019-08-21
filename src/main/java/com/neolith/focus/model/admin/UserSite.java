package com.neolith.focus.model.admin;

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

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.asset.Asset;

@Entity
@Table(name="tbl_user_site")
public class UserSite extends BaseModel{


	private static final long serialVersionUID = -619284122942321514L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="user_site_s")
	@SequenceGenerator(name="user_site_s", sequenceName="user_site_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn(name="user_id")
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY,cascade = {CascadeType.MERGE})
	private User user;

	@JoinColumn(name="site_id")
	@ManyToOne(targetEntity=Asset.class,fetch=FetchType.LAZY)
	private Asset site;

	@OneToMany(mappedBy="userSite", fetch=FetchType.LAZY,cascade = {CascadeType.ALL})
	private Set<UserSiteGroup> userSiteGroups;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<UserSiteGroup> getUserSiteGroups() {
		return userSiteGroups;
	}

	public void setUserSiteGroups(Set<UserSiteGroup> userSiteGroups) {
		updateCollection("userSiteGroups", userSiteGroups);
	}

	public Asset getSite() {
		return site;
	}

	public void setSite(Asset site) {
		this.site = site;
	}





}
