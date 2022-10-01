package com.codex.ecam.dao.inventory;
 
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.rfq.RFQChangeLog;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface RFQChangeLogDao extends FocusDataTableRepository<RFQChangeLog, Integer> {

}
