package com.codex.ecam.model.biz.supplier;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.asset.Contact;

@Entity
@Table(name="tbl_supplier_contact")
public class SupplierContact extends BaseModel {

	private static final long serialVersionUID = 3995542305560456290L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "supplier_contact_s")
	@SequenceGenerator(name = "supplier_contact_s", sequenceName = "supplier_contact_s", allocationSize = 1)
	@Column(name="id")
	private Integer id;	
	
	@JoinColumn( name="supplier_id" )
	@ManyToOne( targetEntity = Supplier.class, cascade =  {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	private Supplier supplier;
	
	@JoinColumn( name="contact_id" )
	@ManyToOne( targetEntity = Contact.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	private Contact contact;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	


}
