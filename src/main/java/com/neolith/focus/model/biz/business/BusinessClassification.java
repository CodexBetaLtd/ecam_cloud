package com.neolith.focus.model.biz.business;

import com.neolith.focus.model.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "tbl_business_classification")
public class BusinessClassification extends BaseModel {

	private static final long serialVersionUID = 8641823824070828671L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "business_classification_s")
    @SequenceGenerator(name = "business_classification_s", sequenceName = "business_classification_s", allocationSize = 1)
    @Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

    @Column(name = "description")
    private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
