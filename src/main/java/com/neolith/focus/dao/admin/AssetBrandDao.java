package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.AssetBrand;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface AssetBrandDao extends FocusDataTableRepository<AssetBrand, Integer>{
	
	AssetBrand findById(Integer id);
	
}
