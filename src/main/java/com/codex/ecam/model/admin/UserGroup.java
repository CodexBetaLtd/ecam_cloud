package com.codex.ecam.model.admin;

import java.io.Serializable;
import java.util.HashSet;
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
import com.codex.ecam.model.biz.business.Business;

@Entity
@Table(name="tbl_user_group")
public class UserGroup extends BaseModel implements Serializable {

	private static final long serialVersionUID = 6274638401650831218L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="user_group_s")
	@SequenceGenerator(name="user_group_s", sequenceName="user_group_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@Column(name="name")
	private String name;

	@Column(name="description")
	private String description;

	@OneToMany( mappedBy = "userGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserGroupMenu> menuList = new HashSet<UserGroupMenu>();

	@OneToMany( mappedBy = "userGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserGroupPage> pageList = new HashSet<UserGroupPage>();

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

	public Set<UserGroupMenu> getMenuList() {
		return menuList;
	}

	public void setMenuList(Set<UserGroupMenu> menuList) {
		updateCollection("menuList", menuList);
	}

	public Set<UserGroupPage> getPageList() {
		return pageList;
	}

	public void setPageList(Set<UserGroupPage> pageList) {
		updateCollection("pageList", pageList);
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}
}
