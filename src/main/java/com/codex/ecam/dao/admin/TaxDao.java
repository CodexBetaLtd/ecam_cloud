package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.Tax;
import com.codex.ecam.model.maintenance.Account;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface TaxDao extends FocusDataTableRepository<Tax, Integer> {


}
