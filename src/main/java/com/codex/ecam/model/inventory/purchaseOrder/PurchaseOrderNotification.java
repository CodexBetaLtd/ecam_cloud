package com.codex.ecam.model.inventory.purchaseOrder;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.User;

@Entity
@Table(name="tbl_purchase_order_notification")
public class PurchaseOrderNotification extends BaseModel {
	
	private static final long serialVersionUID = -3850310852386241291L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="purchase_order_notification_s")
	@SequenceGenerator(name="purchase_order_notification_s", sequenceName="purchase_order_notification_s", allocationSize=1)
	@Column(name="id")
    private Integer id;

	@JoinColumn(name="purchase_order_id")
    @ManyToOne(targetEntity = PurchaseOrder.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private PurchaseOrder purchaseOrder;

	@JoinColumn(name="user_id")
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY,cascade = {CascadeType.MERGE})
	private User user;

	@Column(name="notify_on_assignment")
	private Boolean notifyOnAssignment;

	@Column(name="notify_on_status_change")
	private Boolean notifyOnStatusChange;

	@Column(name="notify_on_completion")
	private Boolean notifyOnCompletion;

	@Column(name="notify_on_task_completed")
	private Boolean notifyOnTaskCompleted;

	@Column(name="notify_on_online_offline")
	private Boolean notifyOnOnlineOffline;


	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public Boolean getNotifyOnAssignment() {
		return notifyOnAssignment;
	}

	public void setNotifyOnAssignment(Boolean notifyOnAssignment) {
		this.notifyOnAssignment = notifyOnAssignment;
	}

	public Boolean getNotifyOnStatusChange() {
		return notifyOnStatusChange;
	}

	public void setNotifyOnStatusChange(Boolean notifyOnStatusChange) {
		this.notifyOnStatusChange = notifyOnStatusChange;
	}

	public Boolean getNotifyOnCompletion() {
		return notifyOnCompletion;
	}

	public void setNotifyOnCompletion(Boolean notifyOnCompletion) {
		this.notifyOnCompletion = notifyOnCompletion;
	}

	public Boolean getNotifyOnTaskCompleted() {
		return notifyOnTaskCompleted;
	}

	public void setNotifyOnTaskCompleted(Boolean notifyOnTaskCompleted) {
		this.notifyOnTaskCompleted = notifyOnTaskCompleted;
	}

	public Boolean getNotifyOnOnlineOffline() {
		return notifyOnOnlineOffline;
	}

	public void setNotifyOnOnlineOffline(Boolean notifyOnOnlineOffline) {
		this.notifyOnOnlineOffline = notifyOnOnlineOffline;
	}


}
