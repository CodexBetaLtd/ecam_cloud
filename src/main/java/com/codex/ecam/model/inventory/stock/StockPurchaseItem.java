package com.codex.ecam.model.inventory.stock;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.Currency;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;

import java.util.Date;

@Entity
@Table(name = "tbl_stock_purchase_item")
public class StockPurchaseItem extends BaseModel {
	
	private static final long serialVersionUID = -4776175300981080656L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "stock_purchase_item_s")
	@SequenceGenerator(name = "stock_purchase_item_s", sequenceName = "stock_purchase_item_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	@JoinColumn(name = "asset_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset part;
	
	@JoinColumn(name = "business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;
	
	@JoinColumn(name = "currency_id")
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	private Currency currency;
	
	@JoinColumn(name = "stock_id")
	@ManyToOne(targetEntity = Stock.class, fetch = FetchType.LAZY)
	private Stock stock;
	
	@JoinColumn(name = "user_id")
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User user;
	
	@Column(name = "expiry_date")
	private Date expiryDate;
	
	@Column(name = "ordered_date")
	private Date orderedDate;
	
	@Column(name = "received_date")
	private Date receivedDate;
	
	@Column(name = "purchased_unit_price")
	private Double purchasedUnitPrice;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "quantity_left")
	private Integer quantityLeft;
	
	@Column(name = "quantity_left_when_ordered")
	private Integer quantityLeftWhenOrdered;
	
	@Column(name = "quantity_ordered")
	private Integer quantityOrdered;
	
	@Column(name = "quantity_purchased")
	private Integer quantityPurchased;
	
	@Column(name = "quantity_used")
	private Integer quantityUsed;

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

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
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

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(Date orderedDate) {
		this.orderedDate = orderedDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Double getPurchasedUnitPrice() {
		return purchasedUnitPrice;
	}

	public void setPurchasedUnitPrice(Double purchasedUnitPrice) {
		this.purchasedUnitPrice = purchasedUnitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantityLeft() {
		return quantityLeft;
	}

	public void setQuantityLeft(Integer quantityLeft) {
		this.quantityLeft = quantityLeft;
	}

	public Integer getQuantityLeftWhenOrdered() {
		return quantityLeftWhenOrdered;
	}

	public void setQuantityLeftWhenOrdered(Integer quantityLeftWhenOrdered) {
		this.quantityLeftWhenOrdered = quantityLeftWhenOrdered;
	}

	public Integer getQuantityOrdered() {
		return quantityOrdered;
	}

	public void setQuantityOrdered(Integer quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

	public Integer getQuantityPurchased() {
		return quantityPurchased;
	}

	public void setQuantityPurchased(Integer quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}

	public Integer getQuantityUsed() {
		return quantityUsed;
	}

	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}
	

}
