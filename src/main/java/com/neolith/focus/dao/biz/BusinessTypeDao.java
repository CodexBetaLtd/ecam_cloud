package com.neolith.focus.dao.biz;

import com.neolith.focus.model.biz.business.BusinessTypeDefinition;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessTypeDao extends FocusDataTableRepository<BusinessTypeDefinition, Integer>{

	BusinessTypeDefinition findById(Integer id);

}
