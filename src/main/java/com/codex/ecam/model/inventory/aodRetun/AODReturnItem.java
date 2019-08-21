package com.codex.ecam.model.inventory.aodRetun;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.inventory.aod.AODItem;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_aod_return_item")
public class AODReturnItem extends BaseModel {

    private static final long serialVersionUID = -556891252588332116L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "aod_return_item_s")
    @SequenceGenerator(name = "aod_return_item_s", sequenceName = "aod_return_item_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(targetEntity = AODReturn.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "aod_return_id")
    private AODReturn aodReturn;

    @Column(name = "description")
    private String description;

    @Column(name = "return_qty")
    private BigDecimal returnQty;

    @ManyToOne(targetEntity = AODItem.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "aod_item_id")
    private AODItem aodItem;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AODReturn getAodReturn() {
        return aodReturn;
    }

    public void setAodReturn(AODReturn aodReturn) {
        this.aodReturn = aodReturn;
    }

    public BigDecimal getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(BigDecimal returnQty) {
        this.returnQty = returnQty;
    }

    public AODItem getAodItem() {
        return aodItem;
    }

    public void setAodItem(AODItem aodItem) {
        this.aodItem = aodItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
