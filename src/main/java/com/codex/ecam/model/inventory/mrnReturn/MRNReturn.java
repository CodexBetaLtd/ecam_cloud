package com.codex.ecam.model.inventory.mrnReturn;

import java.util.Date;
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

import com.codex.ecam.constants.inventory.MRNReturnStatus;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.inventory.mrn.MRN;

@Entity
@Table(name = "tbl_mrn_return")
public class MRNReturn extends BaseModel {

    private static final long serialVersionUID = -5566712823688332116L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "mrn_return_s")
    @SequenceGenerator(name = "mrn_return_s", sequenceName = "mrn_returns_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "business_id")
    @ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
    private Business business;

    @JoinColumn(name = "site_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset site;

    @JoinColumn(name = "requested_by", nullable = true)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User requestedBy;
    
    @JoinColumn(name = "mrn_id", nullable = true)
    @ManyToOne(targetEntity = MRN.class, fetch = FetchType.LAZY)
    private MRN mrn;
    
    @Column(name = "mrn_return_status_id")
    private MRNReturnStatus mrnReturnStatus;

    @Column(name = "mrn_return_no")
    private String mrnReturnNo;

    @Column(name = "date")
    private Date date;

    @OneToMany(mappedBy = "mrnReturn", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<MRNReturnItem> returnItems;

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

	public Asset getSite() {
		return site;
	}

	public void setSite(Asset site) {
		this.site = site;
	}

	public User getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(User requestedBy) {
		this.requestedBy = requestedBy;
	}

	public MRN getMrn() {
		return mrn;
	}

	public void setMrn(MRN mrn) {
		this.mrn = mrn;
	}



	public MRNReturnStatus getMrnReturnStatus() {
		return mrnReturnStatus;
	}

	public void setMrnReturnStatus(MRNReturnStatus mrnReturnStatus) {
		this.mrnReturnStatus = mrnReturnStatus;
	}

	public String getMrnReturnNo() {
		return mrnReturnNo;
	}

	public void setMrnReturnNo(String mrnReturnNo) {
		this.mrnReturnNo = mrnReturnNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<MRNReturnItem> getReturnItems() {
		return returnItems;
	}

	public void setReturnItems(Set<MRNReturnItem> returnItems) {
		this.returnItems = returnItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


    
}
