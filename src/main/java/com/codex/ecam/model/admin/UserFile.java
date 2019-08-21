package com.codex.ecam.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;

@Entity
@Table(name="tbl_user_file")
public class UserFile extends BaseModel {

	private static final long serialVersionUID = 3383055772847671007L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="user_file_s")
	@SequenceGenerator(name="user_file_s", sequenceName="user_file_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@Column(name="file_path")
	private String filePath;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

}
