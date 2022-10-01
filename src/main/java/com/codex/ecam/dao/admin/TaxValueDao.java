package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.TaxValue;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface TaxValueDao extends FocusDataTableRepository<TaxValue, Integer> {


}
