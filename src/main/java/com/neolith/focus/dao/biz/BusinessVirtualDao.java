package com.neolith.focus.dao.biz;

import com.neolith.focus.model.biz.business.BusinessVirtual;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessVirtualDao extends FocusDataTableRepository<BusinessVirtual, Integer> {

    @Query("select bv from BusinessVirtual bv join bv.business as biz  where biz.id = :businessId")
    BusinessVirtual findByBusinessOwner(@Param("businessId") Integer businessId);

}
