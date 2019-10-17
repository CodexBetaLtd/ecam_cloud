package com.codex.ecam.dao.inventory;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.mrn.MRN;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface MRNDao extends FocusDataTableRepository<MRN, Integer> {

   /* @Query("select aod from AOD aod where aod.id=(select max(id) from AOD)")
    AOD findLastDomain();*/

}
