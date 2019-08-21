package com.codex.ecam.model.inventory.aodRetun;

import javax.persistence.*;

import com.codex.ecam.constants.inventory.AODReturnStatus;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.inventory.aod.AOD;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_aod_return")
public class AODReturn extends BaseModel {

    private static final long serialVersionUID = -5566712823688332116L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "aod_return_s")
    @SequenceGenerator(name = "aod_return_s", sequenceName = "aod_return_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "business_id")
    @ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
    private Business business;

    @JoinColumn(name = "site_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset site;

    @JoinColumn(name = "aod_id")
    @ManyToOne(targetEntity = AOD.class, fetch = FetchType.LAZY)
    private AOD aod;

    @Column(name = "return_no")
    private String returnNo;

    @Column(name = "return_ref_no")
    private String returnRefNo;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "aod_return_status_id")
    private AODReturnStatus aodReturnStatus;

    @OneToMany(mappedBy = "aodReturn", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<AODReturnItem> aodReturnItems;


    /*===============================================================================================================*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public String getReturnRefNo() {
        return returnRefNo;
    }

    public void setReturnRefNo(String returnRefNo) {
        this.returnRefNo = returnRefNo;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Set<AODReturnItem> getAodReturnItems() {
        return aodReturnItems;
    }

    public void setAodReturnItems(Set<AODReturnItem> aodReturnItems) {
        updateCollection("aodReturnItems", aodReturnItems);
    }

    public AODReturnStatus getAodReturnStatus() {
        return aodReturnStatus;
    }

    public void setAodReturnStatus(AODReturnStatus aodReturnStatus) {
        this.aodReturnStatus = aodReturnStatus;
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

    public AOD getAod() {
        return aod;
    }

    public void setAod(AOD aod) {
        this.aod = aod;
    }
}
