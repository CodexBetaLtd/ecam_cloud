package com.neolith.focus.dao.biz;

import com.neolith.focus.model.biz.customer.Customer;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.stereotype.Repository;
@Repository
public interface CustomerDao extends FocusDataTableRepository<Customer, Integer>{

	Customer findById(Integer id);

}
