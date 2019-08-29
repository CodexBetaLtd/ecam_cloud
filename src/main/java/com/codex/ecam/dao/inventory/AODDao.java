package com.codex.ecam.dao.inventory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.aod.AOD;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface AODDao extends FocusDataTableRepository<AOD, Integer> {

    @Query("select aod from AOD aod where aod.id=(select max(id) from AOD)")
    AOD findLastDomain();

}