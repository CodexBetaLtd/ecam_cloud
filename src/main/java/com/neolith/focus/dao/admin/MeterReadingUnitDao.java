package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.MeterReadingUnit;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface MeterReadingUnitDao extends FocusDataTableRepository<MeterReadingUnit, Integer> {

    MeterReadingUnit findById(Integer id);

}
