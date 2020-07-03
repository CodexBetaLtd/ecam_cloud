package com.codex.ecam.dto.admin.cmmssetting.tax;

import java.math.BigDecimal;
import java.util.Date;

import com.codex.ecam.dto.BaseDTO;

public class TaxValueDTO extends BaseDTO {

    private Integer id;
    private Date startDate;
    private Date endDate;
    private Boolean isCurrentRate ;
    private BigDecimal value;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
    
    


  

}
