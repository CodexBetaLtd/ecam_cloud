package com.neolith.focus.model.biz.business;

import java.util.Date;
import java.util.Set;

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
import com.neolith.focus.model.app.App;
import com.neolith.focus.model.app.RelatedApp;

@Entity
@Table(name = "tbl_business_app")
public class BusinessApp extends BaseModel {

	private static final long serialVersionUID = 6561188765583313196L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "business_app_s")
	@SequenceGenerator(name = "business_app_s", sequenceName = "business_app_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "app_id")
	@ManyToOne(targetEntity = App.class, fetch = FetchType.LAZY)
	private App app;

	@JoinColumn(name = "business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@Column(name = "expiry_date")
	private Date expiryDate;

	@Column(name = "no_expiry")
	private Boolean noExpiry;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="app_id", referencedColumnName="app_id", updatable=false, insertable=false)
	private Set<RelatedApp> relatedApps;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="related_app_id", referencedColumnName="app_id", updatable=false, insertable=false)
	private Set<RelatedApp> affectedApps;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Boolean getNoExpiry() {
		return noExpiry;
	}

	public void setNoExpiry(Boolean noExpiry) {
		this.noExpiry = noExpiry;
	}

	public Set<RelatedApp> getRelatedApps() {
		return relatedApps;
	}

	public void setRelatedApps(Set<RelatedApp> relatedApps) {
		updateCollection("relatedApps", relatedApps);
	}

	public Set<RelatedApp> getAffectedApps() {
		return affectedApps;
	}

	public void setAffectedApps(Set<RelatedApp> affectedApps) {
		updateCollection("affectedApps", affectedApps);
	}

}
