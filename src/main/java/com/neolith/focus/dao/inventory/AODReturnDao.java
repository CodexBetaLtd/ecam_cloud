package com.neolith.focus.dao.inventory;

import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.inventory.aodRetun.AODReturn;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AODReturnDao extends FocusDataTableRepository<AODReturn, Integer> {

    @Query("select aodReturn from AODReturn aodReturn " +
            "where aodReturnStatus.id = :aodReturnStatus")
    List<AODReturn> findAODReturnByStatus(@Param("aodReturnStatus") Integer aodReturnStatus);

    @Query("select cus from Asset cus  where  cus.id = (select customer.id from AOD aod where aod.id=:aodId )")
    Asset findCutomerByAOD(@Param("aodId") Integer id);

    @Query("select aodReturn from AODReturn aodReturn where aodReturn.id=(select max(id) from AODReturn)")
    AODReturn findLastDomain();

}
