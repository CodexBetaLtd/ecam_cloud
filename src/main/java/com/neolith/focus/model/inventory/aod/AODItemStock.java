package com.neolith.focus.model.inventory.aod;

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.inventory.stock.Stock;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_aod_item_stock")
public class AODItemStock extends BaseModel {

    private static final long serialVersionUID = -5566965874688332116L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "aod_item_stock_s")
    @SequenceGenerator(name = "aod_item_stock_s", sequenceName = "aod_item_stock_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "aod_item_id")
    @ManyToOne(targetEntity = AODItem.class, fetch = FetchType.LAZY)
    private AODItem aodItem;

    @JoinColumn(name = "stock_id")
    @ManyToOne(targetEntity = Stock.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Stock stock;

    @Column(name = "item_cost")
    private BigDecimal itemCost;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "return_quantity")
    private BigDecimal returnQuantity;

    /**
     * ===============================================================
     */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AODItem getAodItem() {
        return aodItem;
    }

    public void setAodItem(AODItem aodItem) {
        this.aodItem = aodItem;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(BigDecimal returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

}
