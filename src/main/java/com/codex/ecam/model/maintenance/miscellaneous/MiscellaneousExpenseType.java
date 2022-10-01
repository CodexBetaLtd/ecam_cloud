package com.codex.ecam.model.maintenance.miscellaneous;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.biz.business.Business;

/**
 */
@Entity
@Table(name="tbl_miscellaneous_expense_type")
public class MiscellaneousExpenseType extends BaseModel {

    private static final long serialVersionUID = 99856993392812085L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="miscellaneous_expense_type_s")
    @SequenceGenerator(name="miscellaneous_expense_type_s", sequenceName="miscellaneous_expense_type_s", allocationSize=1)
    @Column(name="id")
    private Integer id;

    @Column(name="type")
    private String type;

    @Column(name="description")
    private String description;
    
	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}
    
    
}

