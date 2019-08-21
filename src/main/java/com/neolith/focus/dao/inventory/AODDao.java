package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.inventory.aod.AOD;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AODDao extends FocusDataTableRepository<AOD, Integer> {

    @Query("select aod from AOD aod where aod.id=(select max(id) from AOD)")
    AOD findLastDomain();

}
