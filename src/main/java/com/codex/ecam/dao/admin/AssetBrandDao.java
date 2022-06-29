package com.codex.ecam.dao.admin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.AssetBrand;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface AssetBrandDao extends FocusDataTableRepository<AssetBrand, Integer>{

	AssetBrand findById(Integer id);

	@Query("FROM AssetBrand WHERE brandName = :name")
	AssetBrand findByName(@Param("name")String name);

}
