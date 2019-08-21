package com.codex.ecam.dao.biz;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.constants.TAXType;
import com.codex.ecam.model.biz.tax.Tax;
import com.codex.ecam.repository.FocusDataTableRepository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaxDao extends FocusDataTableRepository<Tax, Integer> {

    @Query("select tax from Tax tax where tax.fromDate = :date")
    List<Tax> findCurrentTaxes(@Param("date") Date date);

    @Query("select tax from Tax tax where tax.taxType =:type and createdDate=(select max(createdDate) from Tax tax where tax.taxType = :type)")
    Tax findLastTaxByTaxType(@Param("type") TAXType type);

    @Query("select tax from Tax tax " +
            "where tax.taxType.id in :taxTypeList and createdDate=(select max(createdDate) from Tax tax where tax.taxType.id = :taxTypeId)")
    List<Tax> findLastTaxListByTaxType(@Param("taxTypeId") List<Integer> taxTypeList);


}
