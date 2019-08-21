package com.neolith.focus.dao.biz;

import com.neolith.focus.model.biz.business.Business;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierDao extends FocusDataTableRepository<Business, Integer> {

    @Query("from Business where code = :code and (:id is null or id != :id)")
    List<Business> findDuplicateByCodeAndId(@Param("id") Integer id, @Param("code") String code);

}
