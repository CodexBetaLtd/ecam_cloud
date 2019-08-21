package com.neolith.focus.model.biz.business;

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

@Entity
@Table(name = "tbl_business_virtual")
public class BusinessVirtual extends BaseModel {

	private static final long serialVersionUID = -7592629252462037307L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "business_virtual_s")
	@SequenceGenerator(name = "business_virtual_s", sequenceName = "business_virtual_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "business_owner_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@OneToMany(mappedBy = "businessVirtual", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<Business> virtualBusinesses;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Set<Business> getVirtualBusinesses() {
		return virtualBusinesses;
	}

	public void setVirtualBusinesses(Set<Business> virtualBusinesses) {
		this.virtualBusinesses = virtualBusinesses;
	}
}
