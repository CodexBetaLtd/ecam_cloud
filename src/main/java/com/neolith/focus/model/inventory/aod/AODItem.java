package com.neolith.focus.model.inventory.aod;

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.inventory.stock.Stock;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "tbl_aod_item")
public class AODItem extends BaseModel {

    private static final long serialVersionUID = -5566965874688332116L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "aod_item_s")
    @SequenceGenerator(name = "aod_item_s", sequenceName = "aod_item_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "aod_id")
    @ManyToOne(targetEntity = AOD.class, fetch = FetchType.LAZY)
    private AOD aod;

    @JoinColumn(name = "part_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset part;

    @JoinColumn(name = "warehouse_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset warehouse;

    @JoinColumn(name = "site_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset site;
 
    @JoinColumn(name = "stock_id")
    @ManyToOne(targetEntity = Stock.class, fetch = FetchType.LAZY)
    private Stock stock;

    @Column(name = "batch_no")
    private String batchNo;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "return_quantity")
    private BigDecimal returnQuantity;

    @Column(name = "description")
    private String description;

    @Column(name = "item_cost")
    private BigDecimal itemCost;

    @OneToMany(mappedBy = "aodItem", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<AODItemStock> aodItemStocks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AOD getAod() {
        return aod;
    }

    public void setAod(AOD aod) {
        this.aod = aod;
    }

    public Asset getPart() {
        return part;
    }

    public void setPart(Asset part) {
        this.part = part;
    }

    public Asset getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Asset warehouse) {
        this.warehouse = warehouse;
    }

    public Asset getSite() {
        return site;
    }

    public void setSite(Asset site) {
        this.site = site;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public Set<AODItemStock> getAodItemStocks() {
        return aodItemStocks;
    }

    public void setAodItemStocks(Set<AODItemStock> aodItemStocks) {
        updateCollection("aodItemStocks", aodItemStocks);
    }
}
