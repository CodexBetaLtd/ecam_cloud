package com.codex.ecam.dao.asset;
 
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.asset.AssetLog;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface AssetLogDao extends FocusDataTableRepository<AssetLog, Integer> {

}
