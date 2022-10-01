package com.codex.ecam.model.biz.part;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Asset;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_avg_price")
public class AvgPrice extends BaseModel {

    private static final long serialVersionUID = -4742026916418181279L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "asset_avg_price_s")
    @SequenceGenerator(name = "asset_avg_price_s", sequenceName = "asset_avg_price_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "part_id")
    @ManyToOne(targetEntity = Asset.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Asset part;

    @Column(name = "avg_price")
    private BigDecimal avgPrice;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Asset getPart() {
        return part;
    }

    public void setPart(Asset part) {
        this.part = part;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }
}
