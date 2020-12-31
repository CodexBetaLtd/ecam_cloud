package com.codex.ecam.dao.biz;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.biz.business.BusinessTypeDefinition;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface BusinessTypeDao extends FocusDataTableRepository<BusinessTypeDefinition, Integer>{

	BusinessTypeDefinition findById(Integer id);

}
