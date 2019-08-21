package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.AssetBrand;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface AssetBrandDao extends FocusDataTableRepository<AssetBrand, Integer>{
	
	AssetBrand findById(Integer id);
	
}
