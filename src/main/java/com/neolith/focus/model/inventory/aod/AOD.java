package com.neolith.focus.model.inventory.aod;

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

import com.neolith.focus.constants.inventory.AODStatus;
import com.neolith.focus.constants.inventory.AODType;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.admin.User;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.model.maintenance.workorder.WorkOrder;

@Entity
@Table(name = "tbl_aod")
public class AOD extends BaseModel {

    private static final long serialVersionUID = -5566712823688332116L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "aod_s")
    @SequenceGenerator(name = "aod_s", sequenceName = "aod_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "business_id")
    @ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
    private Business business;

    @JoinColumn(name = "site_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset site;

    @Column(name = "aod_type_id")
    private AODType aodType;

    @JoinColumn(name = "customer_id")
    @ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
    private Business customer;
    
    @JoinColumn(name = "requested_by", nullable = true)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User requestedBy;
    
    @JoinColumn(name = "workorder_id")
    @ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
    private WorkOrder workOrder;

    @Column(name = "aod_status_id")
    private AODStatus aodStatus;

    @Column(name = "contact_person")
    private String customerContactPerson;

    @Column(name = "aod_no")
    private String aodNo;

    @Column(name = "date")
    private Date date;

    @OneToMany(mappedBy = "aod", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<AODItem> aodItemList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AODType getAodType() {
        return aodType;
    }

    public void setAodType(AODType aodType) {
        this.aodType = aodType;
    }

    public String getAodNo() {
        return aodNo;
    }

    public void setAodNo(String aodNo) {
        this.aodNo = aodNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Set<AODItem> getAodItemList() {
        return aodItemList;
    }

    public void setAodItemList(Set<AODItem> aodItemList) {
        updateCollection("aodItemList", aodItemList);
    } 

    public AODStatus getAodStatus() {
        return aodStatus;
    }

    public void setAodStatus(AODStatus aodStatus) {
        this.aodStatus = aodStatus;
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

    public String getCustomerContactPerson() {
        return customerContactPerson;
    }

    public void setCustomerContactPerson(String customerContactPerson) {
        this.customerContactPerson = customerContactPerson;
    }

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public Business getCustomer() {
		return customer;
	}

	public void setCustomer(Business customer) {
		this.customer = customer;
	} 

}
