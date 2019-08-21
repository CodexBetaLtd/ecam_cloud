package com.neolith.focus.dao.biz;

import com.neolith.focus.model.biz.business.BusinessClassification;
import com.neolith.focus.repository.FocusDataTableRepository; 

import org.springframework.stereotype.Repository;

@Repository
public interface BusinessClassificationDao extends FocusDataTableRepository<BusinessClassification, Integer> {

	BusinessClassification findById(Integer id);

}
