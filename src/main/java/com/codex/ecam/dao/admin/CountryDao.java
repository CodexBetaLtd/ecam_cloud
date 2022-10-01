package com.codex.ecam.dao.admin; 
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.Country;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface CountryDao extends FocusDataTableRepository<Country, Integer> {

	Country findById(Integer id);

}
