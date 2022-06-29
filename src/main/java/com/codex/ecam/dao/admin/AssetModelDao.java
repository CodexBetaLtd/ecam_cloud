package com.codex.ecam.dao.admin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.AssetModel;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface AssetModelDao extends FocusDataTableRepository<AssetModel, Integer>{

	AssetModel findById(Integer id);

	@Query("FROM AssetModel WHERE LOWER(modelName) = :name")
	AssetModel findByNameIgnoreCase(@Param("name")String name);
}
