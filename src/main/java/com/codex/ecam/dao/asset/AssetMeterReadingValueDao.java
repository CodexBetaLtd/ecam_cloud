package com.codex.ecam.dao.asset;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.asset.AssetMeterReadingValue;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface AssetMeterReadingValueDao extends FocusDataTableRepository<AssetMeterReadingValue, Integer> {
	
	@Query("from AssetMeterReadingValue where  id = :id")
    AssetMeterReadingValue findById(@Param("id") Integer id);
	
}
