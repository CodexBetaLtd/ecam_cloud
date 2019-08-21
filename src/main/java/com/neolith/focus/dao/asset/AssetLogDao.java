package com.neolith.focus.dao.asset;
 
import org.springframework.stereotype.Repository;

import com.neolith.focus.model.asset.AssetLog;
import com.neolith.focus.repository.FocusDataTableRepository;

@Repository
public interface AssetLogDao extends FocusDataTableRepository<AssetLog, Integer> {

}
