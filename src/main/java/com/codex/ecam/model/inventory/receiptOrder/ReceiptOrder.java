package com.codex.ecam.model.inventory.receiptOrder;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.constants.inventory.ReceiptOrderStatus;
import com.codex.ecam.constants.inventory.ReceiptOrderType;
import com.codex.ecam.listeners.inventory.receiptorder.ReceiptOrderLogListener;
import com.codex.ecam.listeners.inventory.receiptorder.ReceiptorderPrePersistListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.Currency;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.biz.supplier.Supplier;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrder;

@Entity
@Table(name = "tbl_receipt_order")
@EntityListeners( { ReceiptorderPrePersistListener.class,ReceiptOrderLogListener.class } )
public class ReceiptOrder extends BaseModel {

	private static final long serialVersionUID = 833960047762724029L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "receipt_order_s")
	@SequenceGenerator(name = "receipt_order_s", sequenceName = "receipt_order_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "business_id")
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn(name = "site_id")
	@ManyToOne(targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset site;

	@JoinColumn(name = "supplier_id")
	@ManyToOne(targetEntity = Supplier.class, fetch = FetchType.LAZY)
	private Supplier supplier;

	//Todo: Is this Used ??
	@JoinColumn(name = "purchase_order_id")
	@ManyToOne(targetEntity = PurchaseOrder.class, fetch = FetchType.LAZY)
	private PurchaseOrder purchaseOrder;

	@JoinColumn(name = "currency_id")
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	private Currency currency;

	@JoinColumn(name = "received_user_id")
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User receivedUser;

	@Column(name = "receipt_order_status_id")
	private ReceiptOrderStatus receiptOrderStatus;

	@Column(name = "code")
	private String code;

	@Column(name = "date_ordered")
	private Date dateOrdered;

	@Column(name = "date_received")
	private Date dateReceived;
	
	@Column(name = "receiptorder_type_id")
	private ReceiptOrderType receiptOrderType;

	@OneToMany(mappedBy = "receiptOrder", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Set<ReceiptOrderItem> receiptOrderItems;

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



	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public User getReceivedUser() {
		return receivedUser;
	}

	public void setReceivedUser(User receivedUser) {
		this.receivedUser = receivedUser;
	}

	public ReceiptOrderStatus getReceiptOrderStatus() {
		return receiptOrderStatus;
	}

	public void setReceiptOrderStatus(ReceiptOrderStatus receiptOrderStatus) {
		this.receiptOrderStatus = receiptOrderStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDateOrdered() {
		return dateOrdered;
	}

	public void setDateOrdered(Date dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public Set<ReceiptOrderItem> getReceiptOrderItems() {
		return receiptOrderItems;
	}

	public void setReceiptOrderItems(Set<ReceiptOrderItem> receiptOrderItems) {
		updateCollection("receiptOrderItems", receiptOrderItems);
	}

	public ReceiptOrderType getReceiptOrderType() {
		return receiptOrderType;
	}

	public void setReceiptOrderType(ReceiptOrderType receiptOrderType) {
		this.receiptOrderType = receiptOrderType;
	}
	
	
}
