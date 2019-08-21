package com.codex.ecam.model.biz.customer;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Contact;

@Entity
@Table(name="tbl_customer_contact")
public class CustomerContact extends BaseModel {

	private static final long serialVersionUID = 3995542305560456290L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "customer_contact_s")
	@SequenceGenerator(name = "customer_contact_s", sequenceName = "customer_contact_s", allocationSize = 1)
	@Column(name="id")
	private Integer id;	
	
	@JoinColumn( name="customer_id" )
	@ManyToOne( targetEntity = Customer.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	private Customer customer;
	
	@JoinColumn( name="contact_id" )
	@ManyToOne( targetEntity = Contact.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	private Contact contact;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

}
