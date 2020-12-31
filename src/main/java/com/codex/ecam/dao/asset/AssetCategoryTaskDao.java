package com.codex.ecam.dao.asset;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.asset.AssetCategoryTask;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface AssetCategoryTaskDao extends FocusDataTableRepository<AssetCategoryTask, Integer> {

}
