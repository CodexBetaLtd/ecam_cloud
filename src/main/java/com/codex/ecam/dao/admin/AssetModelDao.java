package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.AssetModel;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface AssetModelDao extends FocusDataTableRepository<AssetModel, Integer>{
	
	AssetModel findById(Integer id);
	
}
