package com.neolith.focus.dao.asset;

import com.neolith.focus.model.asset.AssetMeterReadingValue;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetMeterReadingValueDao extends FocusDataTableRepository<AssetMeterReadingValue, Integer> {
	
	@Query("from AssetMeterReadingValue where  id = :id")
    AssetMeterReadingValue findById(@Param("id") Integer id);
	
}
