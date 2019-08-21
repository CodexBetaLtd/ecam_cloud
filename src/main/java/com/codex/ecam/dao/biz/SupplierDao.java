package com.codex.ecam.dao.biz;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.repository.FocusDataTableRepository;

import java.util.List;

@Repository
public interface SupplierDao extends FocusDataTableRepository<Business, Integer> {

    @Query("from Business where code = :code and (:id is null or id != :id)")
    List<Business> findDuplicateByCodeAndId(@Param("id") Integer id, @Param("code") String code);

}
