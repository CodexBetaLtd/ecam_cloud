package com.codex.ecam.model.admin;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderStatusTransitionPermission;

@Entity
@Table(name="tbl_user_po_status_transition_permission")
public class UserPOStatusTransitionPermission extends BaseModel {
	
	private static final long serialVersionUID = -7898749386221373989L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_po_status_transition_permission_s")
	@SequenceGenerator(name = "user_po_status_transition_permission_s", sequenceName = "user_po_status_transition_permission_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	@JoinColumn(name = "user_id")
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY,cascade = {CascadeType.MERGE})
	private User user;
	
	@JoinColumn(name = "permission_id")
	@ManyToOne(targetEntity=PurchaseOrderStatusTransitionPermission.class,fetch=FetchType.LAZY,cascade = {CascadeType.MERGE})
	private PurchaseOrderStatusTransitionPermission statusTransitionPermission;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PurchaseOrderStatusTransitionPermission getStatusTransitionPermission() {
		return statusTransitionPermission;
	}

	public void setStatusTransitionPermission(PurchaseOrderStatusTransitionPermission statusTransitionPermission) {
		this.statusTransitionPermission = statusTransitionPermission;
	}

}
