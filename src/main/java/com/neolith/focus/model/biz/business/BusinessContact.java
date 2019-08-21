package com.neolith.focus.model.biz.business;

import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.asset.Contact;

import javax.persistence.*;

@Entity
@Table(name="tbl_business_contact")
public class BusinessContact extends BaseModel {

	private static final long serialVersionUID = 3995542305560456290L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "business_contact_s")
	@SequenceGenerator(name = "business_contact_s", sequenceName = "business_contact_s", allocationSize = 1)
	@Column(name="id")
	private Integer id;	
	
	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	private Business business;
	
	@JoinColumn( name="contact_id" )
	@ManyToOne( targetEntity = Contact.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	private Contact contact;
	
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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

}
