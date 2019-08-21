package com.neolith.focus.model.admin;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.neolith.focus.constants.UserCertifiCationLevel;
import com.neolith.focus.model.BaseModel;
@Entity
@Table(name="tbl_user_certification")
public class UserCertification extends  BaseModel{

	private static final long serialVersionUID = 7106953745826669111L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="user_certification_s")
	@SequenceGenerator(name="user_certification_s", sequenceName="user_certification_s", allocationSize=1)
	@Column(name="id")
	private Integer id;

	@Column(name="user_certificate_level")
	private UserCertifiCationLevel userCertificateLevel;

	@Column(name = "valid_to_date")
	@Temporal(TemporalType.DATE)
	private Date validToDate;

	@Column(name = "valid_from_date")
	@Temporal(TemporalType.DATE)
	private Date validFromDate;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@JoinColumn(name = "user_id")
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User user;

	@JoinColumn(name = "certificate_type_id")
	@ManyToOne(targetEntity = Certification.class, fetch = FetchType.LAZY)
	private Certification certification;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserCertifiCationLevel getUserCertificateLevel() {
		return userCertificateLevel;
	}

	public void setUserCertificateLevel(UserCertifiCationLevel userCertificateLevel) {
		this.userCertificateLevel = userCertificateLevel;
	}

	public Date getValidToDate() {
		return validToDate;
	}

	public void setValidToDate(Date validToDate) {
		this.validToDate = validToDate;
	}

	public Date getValidFromDate() {
		return validFromDate;
	}

	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Certification getCertification() {
		return certification;
	}

	public void setCertification(Certification certification) {
		this.certification = certification;
	}








}
