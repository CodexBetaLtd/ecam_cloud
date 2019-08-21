package com.neolith.focus.model.biz.tax;

import com.neolith.focus.constants.TAXType;
import com.neolith.focus.model.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_tax")
public class Tax extends BaseModel {

    private static final long serialVersionUID = 6982253054843115569L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tax_s")
    @SequenceGenerator(name = "tax_s", sequenceName = "tax_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tax_type_id")
    private TAXType taxType;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "rate")
    private BigDecimal rate;

    /*====================================================================*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TAXType getTaxType() {
        return taxType;
    }

    public void setTaxType(TAXType taxType) {
        this.taxType = taxType;
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}