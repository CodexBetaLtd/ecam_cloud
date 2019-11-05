package com.codex.ecam.model.inventory.mrn;

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

import com.codex.ecam.constants.inventory.MRNStatus;
import com.codex.ecam.constants.inventory.MRNType;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.biz.customer.Customer;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;

@Entity
@Table(name = "tbl_mrn")
public class MRN extends BaseModel {

    private static final long serialVersionUID = -5566712823688332116L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "mrn_s")
    @SequenceGenerator(name = "mrn_s", sequenceName = "mrn_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "business_id")
    @ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
    private Business business;

    @JoinColumn(name = "site_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset site;

    @Column(name = "mrn_type_id")
    private MRNType mrnType;

    @JoinColumn(name = "customer_id")
    @ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
    private Customer customer;
    
    @JoinColumn(name = "requested_by", nullable = true)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User requestedBy;
    
    @JoinColumn(name = "workorder_id")
    @ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
    private WorkOrder workOrder;

    @Column(name = "mrn_status_id")
    private MRNStatus mrnStatus;

    @Column(name = "contact_person")
    private String customerContactPerson;

    @Column(name = "mrn_no")
    private String mrnNo;

    @Column(name = "date")
    private Date date;

    @OneToMany(mappedBy = "mrn", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<MRNItem> mrnItems;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public MRNType getMrnType() {
		return mrnType;
	}

	public void setMrnType(MRNType mrnType) {
		this.mrnType = mrnType;
	}

	public MRNStatus getMrnStatus() {
		return mrnStatus;
	}

	public void setMrnStatus(MRNStatus mrnStatus) {
		this.mrnStatus = mrnStatus;
	}

	public String getMrnNo() {
		return mrnNo;
	}

	public void setMrnNo(String mrnNo) {
		this.mrnNo = mrnNo;
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


    public Set<MRNItem> getMrnItems() {
		return mrnItems;
	}

	public void setMrnItems(Set<MRNItem> mrnItems) {
		this.mrnItems = mrnItems;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



}
