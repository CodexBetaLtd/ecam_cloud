package com.codex.ecam.dao.biz;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.biz.customer.Customer;
import com.codex.ecam.repository.FocusDataTableRepository;
@Repository
public interface CustomerDao extends FocusDataTableRepository<Customer, Integer>{

	Customer findById(Integer id);

}
