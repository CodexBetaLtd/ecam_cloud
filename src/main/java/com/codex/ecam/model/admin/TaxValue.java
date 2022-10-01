package com.codex.ecam.model.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.biz.business.Business;

@Entity
@Table(name = "tbl_tax_value")
public class TaxValue extends BaseModel{

	private static final long serialVersionUID = 5525006230906983236L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tax_value_s")
	@SequenceGenerator(name = "tax_value_s" , sequenceName = "tax_value_s", allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "from_date")
	private Date fromDate;
	
	@Column(name = "to_date")
	private Date toDate;
	
	@Column(name = "is_current_rate")
	private Boolean isCurrentRate;
	
	@Column(name = "value")
	private BigDecimal value;
	
	@JoinColumn( name="tax_id" )
	@ManyToOne( targetEntity = Tax.class, fetch = FetchType.LAZY)
	private Tax tax;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Boolean getIsCurrentRate() {
		return isCurrentRate;
	}

	public void setIsCurrentRate(Boolean isCurrentRate) {
		this.isCurrentRate = isCurrentRate;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Tax getTax() {
		return tax;
	}

	public void setTax(Tax tax) {
		this.tax = tax;
	}

	
	
}