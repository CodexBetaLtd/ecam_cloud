package com.codex.ecam.model.app;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_app")
public class App extends BaseModel {

	private static final long serialVersionUID = 1402018620137396351L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "app_s")
	@SequenceGenerator(name = "app_s", sequenceName = "app_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "rate")
	private BigDecimal rate;

	@OneToMany(mappedBy = "app", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AppMenu> appMenus;

	@OneToMany(mappedBy = "app", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<RelatedApp> relatedApps;
	
	@OneToMany(mappedBy = "app", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AppWiget> appWigets;

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

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Set<AppMenu> getAppMenus() {
		return appMenus;
	}

	public void setAppMenus(Set<AppMenu> appMenus) {
		updateCollection("appMenus", appMenus);
	}

	public Set<RelatedApp> getRelatedApps() {
		return relatedApps;
	}

	public void setRelatedApps(Set<RelatedApp> relatedApps) {
		updateCollection("relatedApps", relatedApps);
	}

	public Set<AppWiget> getAppWigets() {
		return appWigets;
	}

	public void setAppWigets(Set<AppWiget> appWigets) {
		updateCollection("appWigets", appWigets);
	}
	
	

}
