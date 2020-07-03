package com.codex.ecam.dao.inventory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.mrnReturn.MRNReturn;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface MRNReturnDao extends FocusDataTableRepository<MRNReturn, Integer> {

   /* @Query("select aod from AOD aod where aod.id=(select max(id) from AOD)")
    AOD findLastDomain();*/

    @Query("select mrnr from MRNReturn mrnr where mrnr.id=(select max(id) from MRNReturn where business.id=:businessId and year(createdDate)=year(sysdate()))")
    MRNReturn findLastDomainByBusiness(@Param("businessId") Integer businessId);
}
