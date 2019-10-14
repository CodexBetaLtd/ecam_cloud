package com.codex.ecam.model.admin;

import java.io.Serializable;

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

import com.codex.ecam.constants.Widgets;
import com.codex.ecam.model.BaseModel;

@Entity
@Table(name="tbl_user_group_wiget")
public class UserGroupWiget extends BaseModel implements Serializable {

	private static final long serialVersionUID = 3328873791953044797L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="user_group_wiget_s")
	@SequenceGenerator(name="user_group_wiget_s", sequenceName="user_group_wiget_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@JoinColumn( name="user_group_id" )
	@ManyToOne( targetEntity = UserGroup.class, fetch = FetchType.LAZY)
	private UserGroup userGroup;

	@Column(name="wiget_id")
	private Widgets widgets;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public Widgets getWidgets() {
		return widgets;
	}

	public void setWidgets(Widgets widgets) {
		this.widgets = widgets;
	}





}
