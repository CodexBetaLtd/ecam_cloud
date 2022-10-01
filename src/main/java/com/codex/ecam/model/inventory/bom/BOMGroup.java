package com.codex.ecam.model.inventory.bom;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.biz.business.Business;

import java.util.List;

@Entity
@Table(name = "tbl_bom_group")
public class BOMGroup extends BaseModel {

    private static final long serialVersionUID = 3214131986620871250L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "bom_group_s")
    @SequenceGenerator(name = "bom_group_s", sequenceName = "bom_group_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "business_id")
    @ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
    private Business business;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "bomGroup", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<BOMGroupPart> bomGroupParts;

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

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public List<BOMGroupPart> getBomGroupParts() {
        return bomGroupParts;
    }

    public void setBomGroupParts(List<BOMGroupPart> bomGroupParts) {
        this.bomGroupParts = bomGroupParts;
    }

}
