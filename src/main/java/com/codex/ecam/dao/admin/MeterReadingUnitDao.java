package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.MeterReadingUnit;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface MeterReadingUnitDao extends FocusDataTableRepository<MeterReadingUnit, Integer> {

    MeterReadingUnit findById(Integer id);

}
