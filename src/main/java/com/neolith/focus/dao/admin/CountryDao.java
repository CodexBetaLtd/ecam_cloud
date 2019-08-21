package com.neolith.focus.dao.admin; 
import org.springframework.stereotype.Repository;

import com.neolith.focus.model.admin.Country;
import com.neolith.focus.repository.FocusDataTableRepository;

@Repository
public interface CountryDao extends FocusDataTableRepository<Country, Integer> {

	Country findById(Integer id);

}
