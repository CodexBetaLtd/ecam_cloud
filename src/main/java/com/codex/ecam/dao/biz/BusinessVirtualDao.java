package com.codex.ecam.dao.biz;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.biz.business.BusinessVirtual;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface BusinessVirtualDao extends FocusDataTableRepository<BusinessVirtual, Integer> {

    @Query("select bv from BusinessVirtual bv join bv.business as biz  where biz.id = :businessId")
    BusinessVirtual findByBusinessOwner(@Param("businessId") Integer businessId);

}
