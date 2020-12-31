package com.codex.ecam.model.inventory.stockAdjustment;

import javax.persistence.*;

import com.codex.ecam.constants.inventory.StockAdjustmentStatus;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.inventory.stock.Stock;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_stock_adjustment")
public class StockAdjustment extends BaseModel {

    private static final long serialVersionUID = -9999999918192116L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "stock_adjustment_s")
    @SequenceGenerator(name = "stock_adjustment_s", sequenceName = "stock_adjustment_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "business_id")
    @ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
    private Business business;

    @JoinColumn(name = "site_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset site;

    @JoinColumn(name = "part_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset part;

    @JoinColumn(name = "warehouse_id")
    @ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
    private Asset warehouse;

    @JoinColumn(name = "stock_id")
    @ManyToOne(targetEntity = Stock.class, fetch = FetchType.LAZY)
    private Stock stock;

    @Column(name = "status_id")
    private StockAdjustmentStatus stockAdjustmentStatus;

    @Column(name = "adjustment_code")
    private String adjustmentCode;

    @Column(name = "description")
    private String description;

    @Column(name = "last_quantity")
    private BigDecimal lastQuantity;

    @Column(name = "new_quantity")
    private BigDecimal newQuantity;

    @Column(name = "date")
    private Date stockAdjustmentDate;




	/*=======================================================*/

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

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public BigDecimal getLastQuantity() {
        return lastQuantity;
    }

    public void setLastQuantity(BigDecimal lastQuantity) {
        this.lastQuantity = lastQuantity;
    }

    public BigDecimal getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(BigDecimal newQuantity) {
        this.newQuantity = newQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StockAdjustmentStatus getStockAdjustmentStatus() {
        return stockAdjustmentStatus;
    }

    public void setStockAdjustmentStatus(StockAdjustmentStatus stockAdjustmentStatus) {
        this.stockAdjustmentStatus = stockAdjustmentStatus;
    }

    public String getAdjustmentCode() {
        return adjustmentCode;
    }

    public void setAdjustmentCode(String adjustmentCode) {
        this.adjustmentCode = adjustmentCode;
    }

    public Date getStockAdjustmentDate() {
        return stockAdjustmentDate;
    }

    public void setStockAdjustmentDate(Date stockAdjustmentDate) {
        this.stockAdjustmentDate = stockAdjustmentDate;
    }


}
