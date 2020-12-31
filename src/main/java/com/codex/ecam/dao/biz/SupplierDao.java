package com.codex.ecam.dao.biz;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.biz.supplier.Supplier;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface SupplierDao extends FocusDataTableRepository<Supplier, Integer> {

    @Query("from Supplier where code = :code and (:id is null or id != :id)")
    List<Supplier> findDuplicateByCodeAndId(@Param("id") Integer id, @Param("code") String code);

}
