package com.codex.ecam.dao.maintenance;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.miscellaneous.MiscellaneousExpenseType;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface MiscellaneousExpenseTypeDao extends FocusDataTableRepository<MiscellaneousExpenseType, Integer>{


}
