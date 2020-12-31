package com.codex.ecam.dao.asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.asset.AssetEventTypeAsset;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AssetEventTypeAssetDao extends JpaRepository<AssetEventTypeAsset, Integer> {

    @Query("from AssetEventTypeAsset where asset.id = :assetId")
    List<AssetEventTypeAsset> findByAssetId(@Param("assetId") Integer id);

    @Modifying
    @Transactional
    @Query("delete from AssetEventTypeAsset where  asset.id = :assetId")
    void deleteByAssetId(@Param("assetId") Integer assetId);

}
