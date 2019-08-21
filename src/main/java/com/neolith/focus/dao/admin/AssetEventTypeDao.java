package com.neolith.focus.dao.admin;

import com.neolith.focus.model.asset.AssetEventType;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface AssetEventTypeDao extends FocusDataTableRepository<AssetEventType, Integer> {

	AssetEventType findById(Integer id);

}
