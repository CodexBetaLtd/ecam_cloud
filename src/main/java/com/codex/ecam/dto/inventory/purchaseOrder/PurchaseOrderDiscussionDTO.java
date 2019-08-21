package com.codex.ecam.dto.inventory.purchaseOrder;

import java.util.Date;

import com.codex.ecam.dto.BaseDTO;

public class PurchaseOrderDiscussionDTO extends BaseDTO {

	private Integer id;
	private String userName;
	private Integer userId;
	private Date discussionDate;
	private String comment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getDiscusionDate() {
		return discussionDate;
	}

	public void setDiscusionDate(Date discusionDate) {
		this.discussionDate = discusionDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
