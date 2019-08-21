package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.Currency;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;
@Repository
public interface CurrencyDao extends FocusDataTableRepository<Currency, Integer> {

	Currency findById(Integer id);

}
