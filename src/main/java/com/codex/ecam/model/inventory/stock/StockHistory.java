package com.codex.ecam.model.inventory.stock;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;

@Entity
@Table(name = "tbl_stock_history")
public class StockHistory extends BaseModel {

	private static final long serialVersionUID = 2229070125717214939L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "stock_history_s")
	@SequenceGenerator(name = "stock_history_s", sequenceName = "stock_history_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "stock_id")
	@ManyToOne(targetEntity = Stock.class, fetch = FetchType.LAZY)
	private Stock stock;

	@JoinColumn(name = "user_id")
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User user;

	@Column(name = "description")
	private String description;

	@Column(name = "quantity")
	private BigDecimal quantity;

	@Column(name = "after_quantity")
	private BigDecimal afterQuantity;

	@Column(name = "before_quantity")
	private BigDecimal beforeQuantity;

	@Column(name = "last_price")
	private BigDecimal lastPrice;

	@Column(name = "date")
	private Date date;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
