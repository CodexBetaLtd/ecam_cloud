package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.asset.AssetEventType;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface AssetEventTypeDao extends FocusDataTableRepository<AssetEventType, Integer> {

	AssetEventType findById(Integer id);

}
