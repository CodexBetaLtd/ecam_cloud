package com.codex.ecam.dao.biz;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.biz.business.BusinessClassification;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface BusinessClassificationDao extends FocusDataTableRepository<BusinessClassification, Integer> {

	BusinessClassification findById(Integer id);

}
