package com.neolith.focus.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.biz.business.Business;
@Entity
@Table(name="tbl_user_skill_level")
public class UserSkillLevel extends BaseModel {

	private static final long serialVersionUID = 2795181843338832371L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_skill_level_s")
	@SequenceGenerator(name = "user_skill_level_s", sequenceName = "user_skill_level_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@Column(name="skill")
	private String skill;

	@Column(name="description")
	private String  description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}


}
