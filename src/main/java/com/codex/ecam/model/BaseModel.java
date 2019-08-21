package com.codex.ecam.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.codex.ecam.model.admin.User;
import com.codex.ecam.util.AuthenticationUtil;

@MappedSuperclass
public abstract class BaseModel implements Serializable, PropertyChangeListener {

	private static final long serialVersionUID = -5356423279919704668L;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	protected User createdUser;

	@Column(name = "created_date")
	protected Date createdDate;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by", nullable = true)
	protected User updatedUser;

	@Column(name = "modified_date", nullable = true)
	protected Date updatedDate;

	@Column(name = "is_deleted", nullable = false)
	protected Boolean isDeleted;

	@Version
	@Column(name = "version")
	protected Integer version;

	@Transient
	protected PropertyChangeSupport pcs;

	@Transient
	private Boolean isPropertyChange = false;

	public BaseModel() {
		super();
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(this);
	}

	public abstract Integer getId();

	public abstract void setId(Integer id);

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		setIsPropertyChange(true);
	}

	@PrePersist
	public void prePersist() {
		Date date = new Date();
		createdDate = date;
		try {
			createdUser = AuthenticationUtil.getAuthenticatedUser();
		} catch (Exception e) {
			createdUser = AuthenticationUtil.TRIGGER_USER;
		}
	}

	@PreUpdate
	public void preUpdate() {
		Date date = new Date();
		updatedDate = date;
		try {
			updatedUser = AuthenticationUtil.getAuthenticatedUser();
		} catch (Exception e) {
			updatedUser = AuthenticationUtil.TRIGGER_USER;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof BaseModel ) {
			return getId().equals(((BaseModel)obj).getId());
		}

		return false;
	}

	public User getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(User updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getIsPropertyChange() {
		return isPropertyChange;
	}

	public void setIsPropertyChange(Boolean isPropertyChange) {
		this.isPropertyChange = isPropertyChange;
	}

	protected void firePropertyChange( String propertyName, Object oldValue, Object newValue) {
		if ((oldValue != null) || (newValue != null)) {
			pcs.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	@SuppressWarnings("unchecked")
	protected <T> void updateCollection(String varName, List<T> updatedList) {
		try {

			Field field = this.getClass().getDeclaredField(varName);
			field.setAccessible(true);
			List<T> currentList = (List<T>) field.get(this);

			if (currentList == null) {
				if (updatedList != null) {
					field.set(this, updatedList);
				} else {
					field.set(this, new ArrayList<T>());
				}
			} else {
				currentList.clear();
				if (updatedList != null) {
					currentList.addAll(updatedList);
				} else {
					currentList.addAll(new ArrayList<T>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	protected <T> void updateCollection(String varName, Set<T> updatedList) {
		try {

			Field field = this.getClass().getDeclaredField(varName);
			field.setAccessible(true);
			Set<T> currentList = (Set<T>) field.get(this);

			if (currentList == null) {
				if (updatedList != null) {
					field.set(this, updatedList);
				} else {
					field.set(this, new HashSet<T>());
				}
			} else {
				currentList.clear();
				if (updatedList != null) {
					currentList.addAll(updatedList);
				} else {
					currentList.addAll(new HashSet<T>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
