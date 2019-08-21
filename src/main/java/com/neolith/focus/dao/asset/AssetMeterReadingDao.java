package com.neolith.focus.dao.asset;

import com.neolith.focus.model.asset.AssetMeterReading;
import com.neolith.focus.model.asset.AssetMeterReadingValue;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AssetMeterReadingDao extends FocusDataTableRepository<AssetMeterReading, Integer> {

    @Query("select v from AssetMeterReading m INNER JOIN m.assetMeterReadingValues v where  m.asset.id = :assetId ORDER BY v.createdDate DESC")
    List<AssetMeterReadingValue> findLAllAssetMetereReadingsByAsset(@Param("assetId") Integer id);

    @Modifying
    @Transactional
    @Query("from AssetMeterReading where  asset.id = :assetId")
    List<AssetMeterReading> findAssetMeterReadingByAssetId(@Param("assetId") Integer id);


}
