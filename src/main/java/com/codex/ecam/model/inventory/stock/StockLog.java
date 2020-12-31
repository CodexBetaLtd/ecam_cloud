package com.codex.ecam.model.inventory.stock;

import java.math.BigDecimal;

import javax.persistence.*;

import com.codex.ecam.constants.LogType;
import com.codex.ecam.model.BaseModel;

@Entity
@Table( name = "tbl_stock_log")
public class StockLog extends BaseModel {

	private static final long serialVersionUID = -424042165118627024L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "stock_log_s")
	@SequenceGenerator(name = "stock_log_s", sequenceName = "stock_log_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	@JoinColumn(name = "stock_id")
	@ManyToOne( targetEntity = Stock.class, fetch = FetchType.LAZY)
	private Stock stock;

    @Column(name = "log_type_id")
    private LogType logType;
    
    @Column(name = "quantity_movement")
    private BigDecimal quantity;
    
    @Column(name = "after_quantity")
    private BigDecimal afterQuantity;

    @Column(name = "before_quantity")
	private BigDecimal beforeQuantity;
	
	@Column(name="notes")
	private String notes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAfterQuantity() {
		return afterQuantity;
	}

	public void setAfterQuantity(BigDecimal afterQuantity) {
		this.afterQuantity = afterQuantity;
	}

	public BigDecimal getBeforeQuantity() {
		return beforeQuantity;
	}

	public void setBeforeQuantity(BigDecimal beforeQuantity) {
		this.beforeQuantity = beforeQuantity;
	}

	
}
