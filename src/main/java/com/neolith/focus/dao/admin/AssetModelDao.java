package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.AssetModel;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface AssetModelDao extends FocusDataTableRepository<AssetModel, Integer>{
	
	AssetModel findById(Integer id);
	
}
